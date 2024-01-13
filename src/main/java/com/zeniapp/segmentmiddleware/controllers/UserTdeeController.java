package com.zeniapp.segmentmiddleware.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateTdeeDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.dtos.TdeeDto;
import com.zeniapp.segmentmiddleware.dtos.TdeeQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateTdeeDto;
import com.zeniapp.segmentmiddleware.entities.Tdee;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.services.TdeeService;
import com.zeniapp.segmentmiddleware.utils.ActivityUtils;
import com.zeniapp.segmentmiddleware.utils.TdeeUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/tdees")
public class UserTdeeController {
    @Autowired
    private TdeeService tdeeService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String goal,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String performedOnMin,
        @RequestParam(required = false) String performedOnMax,
        @RequestParam(required = false) String createdOnMin,
        @RequestParam(required = false) String createdOnMax,
        @RequestParam(required = false) String updatedOnMin,
        @RequestParam(required = false) String updatedOnMax,
        @RequestParam(required = false) Boolean isArchived,
        @RequestParam(required = false) Boolean isExerciseActivityThermogenesisCalculatedByActivityLevel,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) String basalMetabolismRateFormula,
        @RequestParam(required = false) String idealWeightFormula,
        @RequestParam(required = false) String weightGoal,
        @RequestAttribute("accountId") String accountId
    ) {
        try {
            TdeeQueryParamsDto tdeeQueryParamsDto = new TdeeQueryParamsDto(
                null, null, null, null,
                title, isArchived, isExerciseActivityThermogenesisCalculatedByActivityLevel,
                performedOnMin, performedOnMin, createdOnMin, createdOnMax, updatedOnMin, updatedOnMax,
                accountId, tags, idealWeightFormula, basalMetabolismRateFormula,
                weightGoal
            );
            
            TdeeUtils.validateTdeeQueryParamsDto(tdeeQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.tdeeService.count(tdeeQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            UserTdeeController.log.error("error occurred counting the tdees");
            UserTdeeController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TdeeUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(
        @Valid @RequestBody CreateTdeeDto createTdeeDto,
        BindingResult bindingResult,
        @RequestAttribute("accountId") String accountId
    ) {
        try {
            TdeeUtils.validateCreateOrUpdateTdeeDto(bindingResult);

            this.accountService
                .findOne(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

            Tdee tdeeToCreate = modelMapper.map(createTdeeDto, Tdee.class);
            tdeeToCreate.setId(null);
            tdeeToCreate.setAccountId(accountId);
            SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            tdeeToCreate.setCreatedOn(datetimeFormatter.format(Calendar.getInstance().getTime()));
            tdeeToCreate.setUpdatedOn(datetimeFormatter.format(Calendar.getInstance().getTime()));
            tdeeToCreate.setResult(TdeeUtils.createTdeeResultByTdee(tdeeToCreate));

            Tdee createdTdee = this.tdeeService.save(tdeeToCreate);

            return new ResponseEntity<TdeeDto>(this.modelMapper.map(createdTdee, TdeeDto.class), HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            UserTdeeController.log.error("error occurred creating the tdee");
            UserTdeeController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TdeeUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id, @RequestAttribute("accountId") String accountId) {
        try {
            Tdee tdeeFound = this.tdeeService.findOneByIdAndAccountId(id, accountId).orElseThrow(ResourceNotFoundException::new);

            TdeeDto tdeeDto = this.modelMapper.map(tdeeFound, TdeeDto.class);

            return new ResponseEntity<TdeeDto>(tdeeDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            UserTdeeController.log.error("error occurred retrieving the tdee");
            UserTdeeController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TdeeUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "title") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String goal,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String performedOnMin,
        @RequestParam(required = false) String performedOnMax,
        @RequestParam(required = false) String createdOnMin,
        @RequestParam(required = false) String createdOnMax,
        @RequestParam(required = false) String updatedOnMin,
        @RequestParam(required = false) String updatedOnMax,
        @RequestParam(required = false) Boolean isArchived,
        @RequestParam(required = false) Boolean isExerciseActivityThermogenesisCalculatedByActivityLevel,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) String basalMetabolismRateFormula,
        @RequestParam(required = false) String idealWeightFormula,
        @RequestParam(required = false) String weightGoal,
        @RequestAttribute("accountId") String accountId
    ) {
        try {
            TdeeQueryParamsDto tdeeQueryParamsDto = new TdeeQueryParamsDto(
                offset, limit, sortField, sortDirection,
                title, isArchived, isExerciseActivityThermogenesisCalculatedByActivityLevel,
                performedOnMin, performedOnMin, createdOnMin, createdOnMax, updatedOnMin, updatedOnMax,
                accountId, tags, idealWeightFormula, basalMetabolismRateFormula,
                weightGoal
            );
            
            TdeeUtils.validateTdeeQueryParamsDto(tdeeQueryParamsDto);

            List<Tdee> tdeesFound = this.tdeeService.findMany(tdeeQueryParamsDto);
            List<TdeeDto> tdeeDtos = tdeesFound.stream().map(e -> this.modelMapper.map(e, TdeeDto.class)).toList();
            Long total = this.tdeeService.count(tdeeQueryParamsDto);

            FindManyResponseDto<TdeeDto> findAllResponseDto = new FindManyResponseDto<TdeeDto>(total, tdeeDtos);

            return new ResponseEntity<FindManyResponseDto<TdeeDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            UserTdeeController.log.error("error occurred retrieving the tdees");
            UserTdeeController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TdeeUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(
        @PathVariable String id,
        @Valid @RequestBody UpdateTdeeDto updateTdeeDto,
        BindingResult bindingResult,
        @RequestAttribute("accountId") String accountId
    ) {
        try {
            TdeeUtils.validateCreateOrUpdateTdeeDto(bindingResult);

            this.accountService
                .findOne(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

            Tdee tdeeFound = this.tdeeService.findOneByIdAndAccountId(id, accountId).orElseThrow(ResourceNotFoundException::new);

            Tdee tdeeToUpdate = modelMapper.map(updateTdeeDto, Tdee.class);
            tdeeToUpdate.setId(tdeeFound.getId());
            tdeeToUpdate.setAccountId(accountId);
            tdeeToUpdate.setCreatedOn(tdeeFound.getCreatedOn());
            tdeeToUpdate.setUpdatedOn(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Calendar.getInstance().getTime()));
            tdeeToUpdate.setResult(TdeeUtils.createTdeeResultByTdee(tdeeToUpdate));

            Tdee tdeeUpdated = this.tdeeService.save(tdeeToUpdate);

            return new ResponseEntity<TdeeDto>(this.modelMapper.map(tdeeUpdated, TdeeDto.class), HttpStatus.OK);
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
            UserTdeeController.log.error("error occurred updating the tdee");
            UserTdeeController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id, @RequestAttribute("accountId") String accountId) {
        try {
            Tdee tdeeToDelete = this.tdeeService.findOneByIdAndAccountId(id, accountId).orElseThrow(ResourceNotFoundException::new);

            this.tdeeService.deleteOne(tdeeToDelete.getId());

            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            UserTdeeController.log.error("error occurred during the tdee deletion");
            UserTdeeController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TdeeUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}