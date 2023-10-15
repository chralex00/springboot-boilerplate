package com.zeniapp.segmentmiddleware.controllers;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.configs.Configs;
import com.zeniapp.segmentmiddleware.dtos.CreateAccountDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.JwtDto;
import com.zeniapp.segmentmiddleware.dtos.LoginDto;
import com.zeniapp.segmentmiddleware.dtos.RegistrationDto;
import com.zeniapp.segmentmiddleware.entities.Account;
import com.zeniapp.segmentmiddleware.entities.Session;
import com.zeniapp.segmentmiddleware.enums.AccountRole;
import com.zeniapp.segmentmiddleware.exceptions.AccountBlockedException;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongCredentialsException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.services.SessionService;
import com.zeniapp.segmentmiddleware.utils.AccountUtils;
import com.zeniapp.segmentmiddleware.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/access")
public class PublicAccessController {
    @Autowired
    private Configs configs;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
                throw new WrongPayloadException(errorMessages);
            }

            Account accountByIdentifier = this.accountService.findByIdentifier(loginDto.getIdentifier()).orElseThrow(WrongCredentialsException::new);

            String passwordWithSecret = loginDto.getPassword() + this.configs.getSecurityPasswordHashingSecret();
            
            if (!this.argon2PasswordEncoder.matches(passwordWithSecret, accountByIdentifier.getPassword())) {
                throw new WrongCredentialsException();
            }

            if (accountByIdentifier.getIsBlocked()) {
                throw new AccountBlockedException();
            }

            Optional<Session> sessionByAccountId = this.sessionService.findByAccountId(accountByIdentifier.getId());
            Integer apiCounter = 0;
            
            if (sessionByAccountId.isPresent()) {
                apiCounter = sessionByAccountId.get().getApiCounter();
                this.sessionService.deleteOne(sessionByAccountId.get().getId());
            }

            Session sessionToCreate = new Session();
            sessionToCreate.setAccount(accountByIdentifier);
            sessionToCreate.setApiCounter(apiCounter);
            sessionToCreate.setCreatedOn(new Timestamp(new Date().getTime()));
            sessionToCreate.setLastActivityOn(new Timestamp(new Date().getTime()));

            Session sessionCreated = this.sessionService.save(sessionToCreate);

            Map<String, String> claims = new HashMap<String, String>();
            claims.put("sessionId", sessionCreated.getId());

            String jwt = JwtUtils.generateJwt("user", null, claims, this.configs.getSecurityJwtGenerationSecret());

            return new ResponseEntity<JwtDto>(new JwtDto(jwt), HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (AccountBlockedException accountBlockedException) {
            return new ResponseEntity<ErrorResponseDto>(accountBlockedException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (WrongCredentialsException wrongCredentialsException) {
            return new ResponseEntity<ErrorResponseDto>(wrongCredentialsException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            PublicAccessController.log.error("error occurred during the login");
            PublicAccessController.log.error("error message is " + exception.getMessage());

            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setError(true);
            errorResponseDto.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            errorResponseDto.setMessages(Arrays.asList(new String[] { "generic error occurred" }));

            return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization") String jwt) {
        try {
            Map<String, Object> claims = JwtUtils.decodeJwt(jwt, this.configs.getSecurityJwtGenerationSecret(), new String[] { "sessionId" });

            String sessionId = (String) claims.get("sessionId");

            this.sessionService.deleteOne(sessionId);

            return new ResponseEntity<Object>(null, HttpStatus.OK);
        }
        catch (Exception exception) {
            PublicAccessController.log.error("error occurred during the logout");
            PublicAccessController.log.error("error message is " + exception.getMessage());

            ErrorResponseDto errorResponseDto = new ErrorResponseDto();
            errorResponseDto.setError(true);
            errorResponseDto.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            errorResponseDto.setMessages(Arrays.asList(new String[] { "generic error occurred" }));

            return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDto registrationDto, BindingResult bindingResult) {
        try {
            CreateAccountDto createAccountDto = this.modelMapper.map(registrationDto, CreateAccountDto.class);
            createAccountDto.setRole(AccountRole.USER.toString());
            createAccountDto.setIsBlocked(Boolean.FALSE);
            createAccountDto.setIsConfirmed(Boolean.FALSE);

            AccountUtils.validateCreateAccountDto(createAccountDto, bindingResult);

            List<Account> accountsByEmailOrUsername = this.accountService.findByEmailOrUsername(createAccountDto.getEmail(), createAccountDto.getUsername());

            AccountUtils.manageDuplicateFields(accountsByEmailOrUsername, createAccountDto.getEmail(), createAccountDto.getUsername());

            Account accountToCreate = this.modelMapper.map(createAccountDto, Account.class);
            accountToCreate.setEmail(accountToCreate.getEmail().toLowerCase());
            accountToCreate.setIsBlocked(Boolean.FALSE);
            accountToCreate.setIsConfirmed(Boolean.FALSE);
            accountToCreate.setIsDeleted(Boolean.FALSE);

            String passwordWithSecret = createAccountDto.getPassword() + this.configs.getSecurityPasswordHashingSecret();
            String encodedPassword = this.argon2PasswordEncoder.encode(passwordWithSecret);
            accountToCreate.setPassword(encodedPassword);
            
            this.accountService.save(this.modelMapper.map(accountToCreate, Account.class));

            return new ResponseEntity<Object>(null, HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            PublicAccessController.log.error("error occurred during the account registration");
            PublicAccessController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
