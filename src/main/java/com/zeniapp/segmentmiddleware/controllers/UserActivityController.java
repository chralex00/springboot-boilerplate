package com.zeniapp.segmentmiddleware.controllers;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.ActivityDto;
import com.zeniapp.segmentmiddleware.dtos.ActivityQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.CountResponseDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.FindManyResponseDto;
import com.zeniapp.segmentmiddleware.entities.Activity;
import com.zeniapp.segmentmiddleware.exceptions.ResourceNotFoundException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.ActivityService;
import com.zeniapp.segmentmiddleware.utils.ActivityUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/activities")
public class UserActivityController {
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
            UserActivityController.log.error("error occurred counting the activities");
            UserActivityController.log.error("error message is " + exception.getMessage());
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
            UserActivityController.log.error("error occurred retrieving the activity");
            UserActivityController.log.error("error message is " + exception.getMessage());
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
            UserActivityController.log.error("error occurred retrieving the activities");
            UserActivityController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(ActivityUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}