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
import com.zeniapp.segmentmiddleware.dtos.MuscleQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.entities.Muscle;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class MuscleUtils {
    public static Query getQueryByMuscleQyeryParamsDto(MuscleQueryParamsDto muscleQueryParamsDto) {
        Query query = new Query();

        query.addCriteria(Criteria.where("isDeleted").is(false));
        
        if (muscleQueryParamsDto.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(muscleQueryParamsDto.getName(), "i"));
        }

        if (muscleQueryParamsDto.getOrigin() != null) {
            query.addCriteria(Criteria.where("origin").regex(muscleQueryParamsDto.getOrigin(), "i"));
        }

        if (muscleQueryParamsDto.getInsertion() != null) {
            query.addCriteria(Criteria.where("insertion").regex(muscleQueryParamsDto.getInsertion(), "i"));
        }

        if (muscleQueryParamsDto.getFunctions() != null) {
            query.addCriteria(Criteria.where("functions").regex(muscleQueryParamsDto.getFunctions(), "i"));
        }

        if (muscleQueryParamsDto.getDimension() != null) {
            query.addCriteria(Criteria.where("dimension").is(muscleQueryParamsDto.getDimension()));
        }

        if (muscleQueryParamsDto.getForm() != null) {
            query.addCriteria(Criteria.where("form").is(muscleQueryParamsDto.getForm()));
        }
        
        if (muscleQueryParamsDto.getMainExercises() != null) {
            query.addCriteria(Criteria.where("mainExercises").all(muscleQueryParamsDto.getMainExercises()));
        }
        
        if (muscleQueryParamsDto.getIsPublished() != null) {
            query.addCriteria(Criteria.where("isPublished").is(muscleQueryParamsDto.getIsPublished()));
        }

        return query;
    }

    public static void manageDuplicateFields(Optional<Muscle> muscleByName) throws DuplicateFieldsException {
        if (muscleByName.isPresent()) {
            throw new DuplicateFieldsException(Arrays.asList(new String[] { "name already exists" }));
        }
    }

    public static void validateCreateOrUpdateMuscleDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateMuscleQueryParamsDto(MuscleQueryParamsDto muscleQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<MuscleQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(muscleQueryParamsDto);

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
