package com.zeniapp.segmentmiddleware.controllers;

import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.configs.Configs;
import com.zeniapp.segmentmiddleware.dtos.AccountDto;
import com.zeniapp.segmentmiddleware.dtos.AccountQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateAccountDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateAccountDto;
import com.zeniapp.segmentmiddleware.entities.Account;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.utils.AccountUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/accounts")
public class AdminAccountController {
    @Autowired
    private Configs configs;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) Boolean isConfirmed,
        @RequestParam(required = false) Boolean isBlocked,
        @RequestParam(required = false) Long createdOnMin,
        @RequestParam(required = false) Long createdOnMax,
        @RequestParam(required = false) Long updatedOnMin,
        @RequestParam(required = false) Long updatedOnMax,
        @RequestParam(required = false) String role
    ) {
        try {
            AccountQueryParamsDto accountQueryParamsDto = new AccountQueryParamsDto(
                null, null, null, null,
                email, username, firstName, lastName,
                isConfirmed, isBlocked, createdOnMin, createdOnMax,
                updatedOnMin, updatedOnMax, role
            );
            
            AccountUtils.validateAccountQueryParamsDto(accountQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.accountService.count(accountQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminAccountController.log.error("error occurred counting the accounts");
            AdminAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateAccountDto createAccountDto, BindingResult bindingResult) {
        try {
            AccountUtils.validateCreateAccountDto(createAccountDto, bindingResult);

            List<Account> accountsByEmailOrUsername = this.accountService.findByEmailOrUsername(createAccountDto.getEmail(), createAccountDto.getUsername());

            AccountUtils.manageDuplicateFields(accountsByEmailOrUsername, createAccountDto.getEmail(), createAccountDto.getUsername());

            Account accountToCreate = this.modelMapper.map(createAccountDto, Account.class);
            accountToCreate.setEmail(accountToCreate.getEmail().toLowerCase());

            String passwordWithSecret = createAccountDto.getPassword() + this.configs.getSecurityPasswordHashingSecret();
            String encodedPassword = this.argon2PasswordEncoder.encode(passwordWithSecret);
            accountToCreate.setPassword(encodedPassword);
            
            Account createdAccount = this.accountService.save(this.modelMapper.map(accountToCreate, Account.class));
            
            AccountDto accountDto = this.modelMapper.map(createdAccount, AccountDto.class);
            return new ResponseEntity<AccountDto>(accountDto, HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminAccountController.log.error("error occurred creating the account");
            AdminAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Account accountFound = this.accountService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            AccountDto accountDto = this.modelMapper.map(accountFound, AccountDto.class);

            return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminAccountController.log.error("error occurred retrieving the account");
            AdminAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "email") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) Boolean isConfirmed,
        @RequestParam(required = false) Boolean isBlocked,
        @RequestParam(required = false) Long createdOnMin,
        @RequestParam(required = false) Long createdOnMax,
        @RequestParam(required = false) Long updatedOnMin,
        @RequestParam(required = false) Long updatedOnMax,
        @RequestParam(required = false) String role
    ) {
        try {
            AccountQueryParamsDto accountQueryParamsDto = new AccountQueryParamsDto(
                offset, limit, sortField, sortDirection,
                email, username, firstName, lastName,
                isConfirmed, isBlocked, createdOnMin,
                createdOnMax, updatedOnMin, updatedOnMax,
                role
            );
            
            AccountUtils.validateAccountQueryParamsDto(accountQueryParamsDto);

            List<Account> accountsFound = this.accountService.findMany(accountQueryParamsDto);
            List<AccountDto> accountDtos = accountsFound.stream().map(e -> this.modelMapper.map(e, AccountDto.class)).toList();
            Long total = this.accountService.count(accountQueryParamsDto);

            FindManyResponseDto<AccountDto> findAllResponseDto = new FindManyResponseDto<AccountDto>(total, accountDtos);

            return new ResponseEntity<FindManyResponseDto<AccountDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminAccountController.log.error("error occurred retrieving the accounts");
            AdminAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id, @Valid @RequestBody UpdateAccountDto updateAccountDto, BindingResult bindingResult) {
        try {
            AccountUtils.validateUpdateAccountDto(updateAccountDto, bindingResult);

            Account accountFound = this.accountService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            List<Account> accountsByEmailOrUsername = this.accountService.findByIdNotAndEmailOrUsername(id, updateAccountDto.getEmail(), updateAccountDto.getUsername());
            
            AccountUtils.manageDuplicateFields(accountsByEmailOrUsername, updateAccountDto.getEmail(), updateAccountDto.getUsername());

            accountFound.setEmail(updateAccountDto.getEmail().toLowerCase());
            accountFound.setUsername(updateAccountDto.getUsername());
            accountFound.setFirstName(updateAccountDto.getFirstName());
            accountFound.setLastName(updateAccountDto.getLastName());
            accountFound.setIsConfirmed(Objects.requireNonNullElse(updateAccountDto.getIsConfirmed(), accountFound.getIsConfirmed()));
            accountFound.setIsBlocked(Objects.requireNonNullElse(updateAccountDto.getIsBlocked(), accountFound.getIsBlocked()));
            accountFound.setUpdatedOn(new Timestamp(new Date().getTime()));
            accountFound.setRole(Objects.requireNonNullElse(updateAccountDto.getRole(), accountFound.getRole()));

            if (updateAccountDto.getPassword() != null && updateAccountDto.getPassword().length() > 0) {
                String passwordWithSecret = updateAccountDto.getPassword() + this.configs.getSecurityPasswordHashingSecret();
                String encodedPassword = this.argon2PasswordEncoder.encode(passwordWithSecret);
                accountFound.setPassword(encodedPassword);
            }

            Account accountUpdated = this.accountService.save(accountFound);

            AccountDto accountDto = this.modelMapper.map(accountUpdated, AccountDto.class);

            return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminAccountController.log.error("error occurred updating the account");
            AdminAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Account accountToDelete = this.accountService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            accountToDelete.setIsDeleted(true);

            this.accountService.save(accountToDelete);

            AccountDto accountDto = this.modelMapper.map(accountToDelete, AccountDto.class);

            return new ResponseEntity<AccountDto>(accountDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminAccountController.log.error("error occurred during the account retrieving");
            AdminAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
