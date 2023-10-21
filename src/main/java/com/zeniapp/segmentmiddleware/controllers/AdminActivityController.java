package com.zeniapp.segmentmiddleware.controllers;

import jakarta.validation.Valid;
import java.util.List;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.ActivityDto;
import com.zeniapp.segmentmiddleware.dtos.ActivityQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.CreateActivityDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateActivityDto;
import com.zeniapp.segmentmiddleware.entities.Activity;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.ActivityService;
import com.zeniapp.segmentmiddleware.utils.ActivityUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/activities")
public class AdminActivityController {
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/count")
    public ResponseEntity<?> count(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) List<String> mainExercises,
        @RequestParam(required = false) Integer metsMin,
        @RequestParam(required = false) Integer metsMax
    ) {
        try {
            ActivityQueryParamsDto activityQueryParamsDto = new ActivityQueryParamsDto(
                null, null, null, null,
                name, type, tags, mainExercises,
                metsMin, metsMax
            );
            
            ActivityUtils.validateActivityQueryParamsDto(activityQueryParamsDto);

            CountResponseDto countDto = new CountResponseDto();
            countDto.setTotal(this.activityService.count(activityQueryParamsDto));

            return new ResponseEntity<CountResponseDto>(countDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminActivityController.log.error("error occurred counting the activities");
            AdminActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateActivityDto createActivityDto, BindingResult bindingResult) {
        try {
            ActivityUtils.validateCreateOrUpdateActivityDto(bindingResult);

            Optional<Activity> activityByName = this.activityService.findByName(createActivityDto.getName());

            ActivityUtils.manageDuplicateFields(activityByName);
            
            Activity createdActivity = this.activityService.save(this.modelMapper.map(createActivityDto, Activity.class));

            return new ResponseEntity<ActivityDto>(this.modelMapper.map(createdActivity, ActivityDto.class), HttpStatus.CREATED);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (DuplicateFieldsException duplicateFieldsException) {
            return new ResponseEntity<ErrorResponseDto>(duplicateFieldsException.getErrorResponseDto(), HttpStatus.CONFLICT);
        }
        catch (Exception exception) {
            AdminActivityController.log.error("error occurred creating the activity");
            AdminActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable String id) {
        try {
            Activity activityFound = this.activityService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            ActivityDto activityDto = this.modelMapper.map(activityFound, ActivityDto.class);

            return new ResponseEntity<ActivityDto>(activityDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminActivityController.log.error("error occurred retrieving the activity");
            AdminActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<?> findMany(
        @RequestParam(required = false, defaultValue = "0") Integer offset,
        @RequestParam(required = false, defaultValue = "10") Integer limit,
        @RequestParam(required = false, defaultValue = "name") String sortField,
        @RequestParam(required = false, defaultValue = "asc") String sortDirection,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(required = false) List<String> mainExercises,
        @RequestParam(required = false) Integer metsMin,
        @RequestParam(required = false) Integer metsMax
    ) {
        try {
            ActivityQueryParamsDto activityQueryParamsDto = new ActivityQueryParamsDto(
                offset, limit, sortField, sortDirection,
                name, type, tags, mainExercises,
                metsMin, metsMax
            );
            
            ActivityUtils.validateActivityQueryParamsDto(activityQueryParamsDto);

            List<Activity> activitiesFound = this.activityService.findMany(activityQueryParamsDto);
            List<ActivityDto> activityDtos = activitiesFound.stream().map(e -> this.modelMapper.map(e, ActivityDto.class)).toList();
            Long total = this.activityService.count(activityQueryParamsDto);

            FindManyResponseDto<ActivityDto> findAllResponseDto = new FindManyResponseDto<ActivityDto>(total, activityDtos);

            return new ResponseEntity<FindManyResponseDto<ActivityDto>>(findAllResponseDto, HttpStatus.OK);
        }
        catch (WrongPayloadException wrongPayloadException) {
            return new ResponseEntity<ErrorResponseDto>(wrongPayloadException.getErrorResponseDto(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            AdminActivityController.log.error("error occurred retrieving the activities");
            AdminActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable String id, @Valid @RequestBody UpdateActivityDto updateActivityDto, BindingResult bindingResult) {
        try {
            ActivityUtils.validateCreateOrUpdateActivityDto(bindingResult);

            Activity activityFound = this.activityService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            Optional<Activity> activityByName = this.activityService.findByIdNotAndName(id, updateActivityDto.getName());
            
            ActivityUtils.manageDuplicateFields(activityByName);

            activityFound.setName(updateActivityDto.getName());
            activityFound.setDescription(updateActivityDto.getDescription());
            activityFound.setTags(updateActivityDto.getTags());
            activityFound.setMets(updateActivityDto.getMets());
            activityFound.setMainExercises(null); // to do
            activityFound.setIsPublished(updateActivityDto.getIsPublished());
            activityFound.setIsDeleted(updateActivityDto.getIsDeleted());

            Activity activityUpdated = this.activityService.save(activityFound);

            return new ResponseEntity<ActivityDto>(this.modelMapper.map(activityUpdated, ActivityDto.class), HttpStatus.OK);
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
            AdminActivityController.log.error("error occurred updating the activity");
            AdminActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable String id) {
        try {
            Activity activityToDelete = this.activityService.findOne(id).orElseThrow(ResourceNotFoundException::new);

            activityToDelete.setIsDeleted(true);

            this.activityService.save(activityToDelete);

            ActivityDto activityDto = this.modelMapper.map(activityToDelete, ActivityDto.class);

            return new ResponseEntity<ActivityDto>(activityDto, HttpStatus.OK);
        }
        catch (ResourceNotFoundException resourceNotFoundException) {
            return new ResponseEntity<ErrorResponseDto>(resourceNotFoundException.getErrorResponseDto(), HttpStatus.NOT_FOUND);
        }
        catch (Exception exception) {
            AdminActivityController.log.error("error occurred during the activity deletion");
            AdminActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
