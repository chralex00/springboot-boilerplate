package com.zeniapp.segmentmiddleware.controllers;

import jakarta.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.SessionDto;
import com.zeniapp.segmentmiddleware.dtos.SessionQueryParamsDto;
import com.zeniapp.segmentmiddleware.configs.Configs;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateSessionDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.entities.Account;
import com.zeniapp.segmentmiddleware.entities.Session;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.services.SessionService;
import com.zeniapp.segmentmiddleware.utils.JwtUtils;
import com.zeniapp.segmentmiddleware.utils.SessionUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/sessions")
public class AdminSessionController {
    @Autowired
    private Configs configs;

    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) Integer apiCounterMin,
        @RequestParam(required = false) Integer apiCounterMax,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) Long createdOnMin,
        @RequestParam(required = false) Long createdOnMax,
        @RequestParam(required = false) Long lastActivityOnMin,
        @RequestParam(required = false) Long lastActivityOnMax
    ) {
        try {
            SessionQueryParamsDto sessionQueryParamsDto = new SessionQueryParamsDto(
                null, null, null, null,
                apiCounterMin, apiCounterMax,
                email, username,
                createdOnMin, createdOnMax,
                lastActivityOnMin, lastActivityOnMax
            );
            
            SessionUtils.validateSessionQueryParamsDto(sessionQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.sessionService.count(sessionQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminSessionController.log.error("error occurred counting the sessions");
            AdminSessionController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(SessionUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateSessionDto createSessionDto, BindingResult bindingResult) {
        try {
            SessionUtils.validateCreateSessionDto(createSessionDto, bindingResult);

            Account accountByIdentifier = this.accountService.findByIdentifier(createSessionDto.getIdentifier()).orElseThrow(ResourceNotFoundException::new);

            Optional<Session> sessionByAccountId = this.sessionService.findByAccountId(accountByIdentifier.getId());

            SessionUtils.manageDuplicateFields(sessionByAccountId, createSessionDto.getIdentifier());

            Session sessionToCreate = new Session();
            sessionToCreate.setAccount(accountByIdentifier);
            sessionToCreate.setApiCounter(0);
            sessionToCreate.setCreatedOn(new Timestamp(new Date().getTime()));
            sessionToCreate.setLastActivityOn(new Timestamp(new Date().getTime()));
            
            Session createdSession = this.sessionService.save(sessionToCreate);

            Map<String, String> claims = new HashMap<String, String>();
            claims.put("sessionId", createdSession.getId());
            
            JwtUtils.generateJwt("user", null, claims, this.configs.getSecurityJwtGenerationSecret());
            
            SessionDto sessionDto = this.modelMapper.map(createdSession, SessionDto.class);
            sessionDto.setUsername(accountByIdentifier.getUsername());

            return new ResponseEntity<SessionDto>(sessionDto, HttpStatus.CREATED);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminSessionController.log.error("error occurred creating the session");
            AdminSessionController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(SessionUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Session sessionFound = this.sessionService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            SessionDto sessionDto = this.modelMapper.map(sessionFound, SessionDto.class);
            sessionDto.setUsername(sessionFound.getAccount().getUsername());

            return new ResponseEntity<SessionDto>(sessionDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminSessionController.log.error("error occurred retrieving the session");
            AdminSessionController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(SessionUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "apiCounter") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) Integer apiCounterMin,
        @RequestParam(required = false) Integer apiCounterMax,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String username,
        @RequestParam(required = false) Long createdOnMin,
        @RequestParam(required = false) Long createdOnMax,
        @RequestParam(required = false) Long lastActivityOnMin,
        @RequestParam(required = false) Long lastActivityOnMax
    ) {
        try {
            SessionQueryParamsDto sessionQueryParamsDto = new SessionQueryParamsDto(
                offset, limit, sortField, sortDirection,
                apiCounterMin, apiCounterMax,
                email, username,
                createdOnMin, createdOnMax,
                lastActivityOnMin, lastActivityOnMax
            );
            
            SessionUtils.validateSessionQueryParamsDto(sessionQueryParamsDto);

            List<Session> sessionsFound = this.sessionService.findMany(sessionQueryParamsDto);
            List<SessionDto> sessionDtos = sessionsFound.stream().map(e -> {
                SessionDto dto = this.modelMapper.map(e, SessionDto.class);
                dto.setUsername(e.getAccount().getUsername());
                return dto;
            }).toList();
            Long total = this.sessionService.count(sessionQueryParamsDto);

            FindManyResponseDto<SessionDto> findAllResponseDto = new FindManyResponseDto<SessionDto>(total, sessionDtos);

            return new ResponseEntity<FindManyResponseDto<SessionDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminSessionController.log.error("error occurred retrieving the sessions");
            AdminSessionController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(SessionUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Session sessionToDelete = this.sessionService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            this.sessionService.deleteOne(id);

            SessionDto sessionDto = this.modelMapper.map(sessionToDelete, SessionDto.class);

            return new ResponseEntity<SessionDto>(sessionDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminSessionController.log.error("error occurred during the session deletion");
            AdminSessionController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(SessionUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
