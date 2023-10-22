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
import com.zeniapp.segmentmiddleware.dtos.ExerciseQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.entities.Exercise;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class ExerciseUtils {
    public static Query getQueryByExerciseQyeryParamsDto(ExerciseQueryParamsDto exerciseQueryParamsDto) {
        Query query = new Query();

        query.addCriteria(Criteria.where("isDeleted").is(false));
        
        if (exerciseQueryParamsDto.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(exerciseQueryParamsDto.getName(), "i"));
        }

        if (exerciseQueryParamsDto.getDifficulty() != null) {
            query.addCriteria(Criteria.where("difficulty").is(exerciseQueryParamsDto.getDifficulty()));
        }

        if (exerciseQueryParamsDto.getCategory() != null) {
            query.addCriteria(Criteria.where("category").is(exerciseQueryParamsDto.getCategory()));
        }

        if (exerciseQueryParamsDto.getType() != null) {
            query.addCriteria(Criteria.where("type").is(exerciseQueryParamsDto.getType()));
        }

        if (exerciseQueryParamsDto.getAgonistMuscles() != null) {
            query.addCriteria(Criteria.where("agonistMuscles").all(exerciseQueryParamsDto.getAgonistMuscles()));
        }

        if (exerciseQueryParamsDto.getAntagonistMuscles() != null) {
            query.addCriteria(Criteria.where("antagonistMuscles").all(exerciseQueryParamsDto.getAntagonistMuscles()));
        }

        if (exerciseQueryParamsDto.getFixatorMuscles() != null) {
            query.addCriteria(Criteria.where("fixatorMuscles").all(exerciseQueryParamsDto.getFixatorMuscles()));
        }

        if (exerciseQueryParamsDto.getSynergisticMuscles() != null) {
            query.addCriteria(Criteria.where("synergisticMuscles").all(exerciseQueryParamsDto.getSynergisticMuscles()));
        }

        return query;
    }

    public static void manageDuplicateFields(Optional<Exercise> exerciseByName) throws DuplicateFieldsException {
        if (exerciseByName.isPresent()) {
            throw new DuplicateFieldsException(Arrays.asList(new String[] { "name already exists" }));
        }
    }

    public static void validateCreateOrUpdateExerciseDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateExerciseQueryParamsDto(ExerciseQueryParamsDto exerciseQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<ExerciseQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(exerciseQueryParamsDto);

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
