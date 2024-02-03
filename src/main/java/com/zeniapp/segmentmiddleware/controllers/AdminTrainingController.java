package com.zeniapp.segmentmiddleware.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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
import com.zeniapp.segmentmiddleware.dtos.TrainingDto;
import com.zeniapp.segmentmiddleware.dtos.TrainingQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateTrainingDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateTrainingDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.entities.Training;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.AccountService;
import com.zeniapp.segmentmiddleware.services.TrainingService;
import com.zeniapp.segmentmiddleware.utils.ActivityUtils;
import com.zeniapp.segmentmiddleware.utils.TrainingUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/trainings")
public class AdminTrainingController {
    @Autowired
    private TrainingService trainingService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String goal,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String startsOnMin,
        @RequestParam(required = false) String startsOnMax,
        @RequestParam(required = false) Integer durationMin,
        @RequestParam(required = false) Integer durationMax,
        @RequestParam(required = false) String createdOnMin,
        @RequestParam(required = false) String createdOnMax,
        @RequestParam(required = false) String updatedOnMin,
        @RequestParam(required = false) String updatedOnMax,
        @RequestParam(required = false) Boolean isArchived,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) String accountId
    ) {
        try {
            TrainingQueryParamsDto trainingQueryParamsDto = new TrainingQueryParamsDto(
                null, null, null, null,
                title, goal, type, startsOnMin, startsOnMax,
                durationMin, durationMax, isArchived,
                createdOnMin, createdOnMax,
                updatedOnMin, updatedOnMax,
                accountId, tags
            );
            
            TrainingUtils.validateTrainingQueryParamsDto(trainingQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.trainingService.count(trainingQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminTrainingController.log.error("error occurred counting the trainings");
            AdminTrainingController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TrainingUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateTrainingDto createTrainingDto, BindingResult bindingResult) {
        try {
            TrainingUtils.validateCreateOrUpdateTrainingDto(bindingResult);

            this.accountService
                .findOne(createTrainingDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

            Training trainingToCreate = modelMapper.map(createTrainingDto, Training.class);
            trainingToCreate.setId(null);
            SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            trainingToCreate.setCreatedOn(datetimeFormatter.format(Calendar.getInstance().getTime()));
            trainingToCreate.setUpdatedOn(datetimeFormatter.format(Calendar.getInstance().getTime()));

            Training createdTraining = this.trainingService.save(trainingToCreate);

            return new ResponseEntity<TrainingDto>(this.modelMapper.map(createdTraining, TrainingDto.class), HttpStatus.CREATED);
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
            AdminTrainingController.log.error("error occurred creating the training");
            AdminTrainingController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TrainingUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable @NonNull String id) {
        try {
            Training trainingFound = this.trainingService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            TrainingDto trainingDto = this.modelMapper.map(trainingFound, TrainingDto.class);

            return new ResponseEntity<TrainingDto>(trainingDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminTrainingController.log.error("error occurred retrieving the training");
            AdminTrainingController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TrainingUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
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
        @RequestParam(required = false) String startsOnMin,
        @RequestParam(required = false) String startsOnMax,
        @RequestParam(required = false) Integer durationMin,
        @RequestParam(required = false) Integer durationMax,
        @RequestParam(required = false) String createdOnMin,
        @RequestParam(required = false) String createdOnMax,
        @RequestParam(required = false) String updatedOnMin,
        @RequestParam(required = false) String updatedOnMax,
        @RequestParam(required = false) Boolean isArchived,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) String accountId
    ) {
        try {
            TrainingQueryParamsDto trainingQueryParamsDto = new TrainingQueryParamsDto(
                offset, limit, sortField, sortDirection,
                title, goal, type, startsOnMin, startsOnMax,
                durationMin, durationMax, isArchived,
                createdOnMin, createdOnMax,
                updatedOnMin, updatedOnMax,
                accountId, tags
            );
            
            TrainingUtils.validateTrainingQueryParamsDto(trainingQueryParamsDto);

            List<Training> trainingsFound = this.trainingService.findMany(trainingQueryParamsDto);
            List<TrainingDto> trainingDtos = trainingsFound.stream().map(e -> this.modelMapper.map(e, TrainingDto.class)).toList();
            Long total = this.trainingService.count(trainingQueryParamsDto);

            FindManyResponseDto<TrainingDto> findAllResponseDto = new FindManyResponseDto<TrainingDto>(total, trainingDtos);

            return new ResponseEntity<FindManyResponseDto<TrainingDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminTrainingController.log.error("error occurred retrieving the trainings");
            AdminTrainingController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TrainingUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable @NonNull String id, @Valid @RequestBody UpdateTrainingDto updateTrainingDto, BindingResult bindingResult) {
        try {
            TrainingUtils.validateCreateOrUpdateTrainingDto(bindingResult);
            
            this.accountService
                .findOne(updateTrainingDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

            Training trainingFound = this.trainingService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            Training trainingToUpdate = modelMapper.map(updateTrainingDto, Training.class);
            trainingToUpdate.setId(trainingFound.getId());
            trainingToUpdate.setCreatedOn(trainingFound.getCreatedOn());
            trainingToUpdate.setUpdatedOn(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(Calendar.getInstance().getTime()));

            Training trainingUpdated = this.trainingService.save(trainingToUpdate);

            return new ResponseEntity<TrainingDto>(this.modelMapper.map(trainingUpdated, TrainingDto.class), HttpStatus.OK);
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
            AdminTrainingController.log.error("error occurred updating the training");
            AdminTrainingController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable @NonNull String id) {
        try {
            Training trainingToDelete = this.trainingService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            this.trainingService.deleteOne(trainingToDelete.getId());

            return new ResponseEntity<>(new HashMap<String, String>(), HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminTrainingController.log.error("error occurred during the training deletion");
            AdminTrainingController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(TrainingUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}