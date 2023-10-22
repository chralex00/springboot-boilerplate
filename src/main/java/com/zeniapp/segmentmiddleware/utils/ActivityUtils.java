package com.zeniapp.segmentmiddleware.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.zeniapp.segmentmiddleware.dtos.ActivityQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.entities.Activity;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class ActivityUtils {
    public static Query getQueryByActivityQyeryParamsDto(ActivityQueryParamsDto activityQueryParamsDto) {
        Query query = new Query();
        
        query.addCriteria(Criteria.where("isDeleted").is(false));

        if (activityQueryParamsDto.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(activityQueryParamsDto.getName(), "i"));
        }

        if (activityQueryParamsDto.getType() != null) {
            query.addCriteria(Criteria.where("type").is(activityQueryParamsDto.getType()));
        }

        if (activityQueryParamsDto.getMetsMin() != null && activityQueryParamsDto.getMetsMax() != null) {
            query.addCriteria(Criteria.where("mets")
                .gte(activityQueryParamsDto.getMetsMin())
                .lte(activityQueryParamsDto.getMetsMax()));
        }
        else if (activityQueryParamsDto.getMetsMin() != null) {
            query.addCriteria(Criteria.where("mets").gte(activityQueryParamsDto.getMetsMin()));
        }
        else if (activityQueryParamsDto.getMetsMax() != null) {
            query.addCriteria(Criteria.where("mets").lte(activityQueryParamsDto.getMetsMax()));
        }

        if (activityQueryParamsDto.getTags() != null) {
            query.addCriteria(Criteria.where("tags").all(activityQueryParamsDto.getTags()));
        }

        if (activityQueryParamsDto.getMainExercises() != null) {
            query.addCriteria(Criteria.where("mainExercises").all(activityQueryParamsDto.getMainExercises()));
        }

        return query;
    }

    public static void manageDuplicateFields(Optional<Activity> activityByName) throws DuplicateFieldsException {
        if (activityByName.isPresent()) {
            throw new DuplicateFieldsException(Arrays.asList(new String[] { "name already exists" }));
        }
    }

    public static void validateCreateOrUpdateActivityDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateActivityQueryParamsDto(ActivityQueryParamsDto activityQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<ActivityQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(activityQueryParamsDto);

        if (!validationErrors.isEmpty()) {
            List<String> errorMessages = validationErrors.stream().map(ConstraintViolation::getMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static ErrorResponseDto getInternalServerError() {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setError(true);
        errorResponseDto.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponseDto.setMessages(Arrays.asList(new String[] { "generic error occurred" }));

        return errorResponseDto;
    }
}
