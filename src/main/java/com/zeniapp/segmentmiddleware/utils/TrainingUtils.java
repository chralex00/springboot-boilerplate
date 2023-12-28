package com.zeniapp.segmentmiddleware.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.TrainingQueryParamsDto;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class TrainingUtils {
    public static Query getQueryByTrainingQyeryParamsDto(TrainingQueryParamsDto trainingQueryParamsDto) {
        Query query = new Query();

        if (trainingQueryParamsDto.getAccountId() != null) {
            query.addCriteria(Criteria.where("accountId").is(trainingQueryParamsDto.getAccountId()));
        }

        if (trainingQueryParamsDto.getTags() != null) {
            query.addCriteria(Criteria.where("tags").all(trainingQueryParamsDto.getTags()));
        }

        if (trainingQueryParamsDto.getTitle() != null) {
            query.addCriteria(Criteria.where("title").regex(trainingQueryParamsDto.getTitle(), "i"));
        }

        if (trainingQueryParamsDto.getGoal() != null) {
            query.addCriteria(Criteria.where("goal").regex(trainingQueryParamsDto.getGoal(), "i"));
        }

        if (trainingQueryParamsDto.getType() != null) {
            query.addCriteria(Criteria.where("type").is(trainingQueryParamsDto.getType()));
        }

        if (trainingQueryParamsDto.getStartsOnMin() != null && trainingQueryParamsDto.getStartsOnMax() != null) {
            query.addCriteria(Criteria.where("startsOn")
                .gte(trainingQueryParamsDto.getStartsOnMin())
                .lte(trainingQueryParamsDto.getStartsOnMax()));
        }
        else if (trainingQueryParamsDto.getStartsOnMin() != null) {
            query.addCriteria(Criteria.where("startsOn").gte(trainingQueryParamsDto.getStartsOnMin()));
        }
        else if (trainingQueryParamsDto.getStartsOnMax() != null) {
            query.addCriteria(Criteria.where("startsOn").lte(trainingQueryParamsDto.getStartsOnMax()));
        }

        if (trainingQueryParamsDto.getDurationMin() != null && trainingQueryParamsDto.getDurationMax() != null) {
            query.addCriteria(Criteria.where("duration")
                .gte(trainingQueryParamsDto.getDurationMin())
                .lte(trainingQueryParamsDto.getDurationMax()));
        }
        else if (trainingQueryParamsDto.getDurationMin() != null) {
            query.addCriteria(Criteria.where("duration").gte(trainingQueryParamsDto.getDurationMin()));
        }
        else if (trainingQueryParamsDto.getDurationMax() != null) {
            query.addCriteria(Criteria.where("duration").lte(trainingQueryParamsDto.getDurationMax()));
        }
        
        if (trainingQueryParamsDto.getIsArchived() != null) {
            query.addCriteria(Criteria.where("isArchived").is(trainingQueryParamsDto.getIsArchived()));
        }

        if (trainingQueryParamsDto.getCreatedOnMin() != null && trainingQueryParamsDto.getCreatedOnMax() != null) {
            query.addCriteria(Criteria.where("createdOn")
                .gte(trainingQueryParamsDto.getCreatedOnMin())
                .lte(trainingQueryParamsDto.getCreatedOnMax()));
        }
        else if (trainingQueryParamsDto.getCreatedOnMin() != null) {
            query.addCriteria(Criteria.where("createdOn").gte(trainingQueryParamsDto.getCreatedOnMin()));
        }
        else if (trainingQueryParamsDto.getCreatedOnMax() != null) {
            query.addCriteria(Criteria.where("createdOn").lte(trainingQueryParamsDto.getCreatedOnMax()));
        }

        if (trainingQueryParamsDto.getUpdatedOnMin() != null && trainingQueryParamsDto.getUpdatedOnMax() != null) {
            query.addCriteria(Criteria.where("updatedOn")
                .gte(trainingQueryParamsDto.getUpdatedOnMin())
                .lte(trainingQueryParamsDto.getUpdatedOnMax()));
        }
        else if (trainingQueryParamsDto.getUpdatedOnMin() != null) {
            query.addCriteria(Criteria.where("updatedOn").gte(trainingQueryParamsDto.getUpdatedOnMin()));
        }
        else if (trainingQueryParamsDto.getUpdatedOnMax() != null) {
            query.addCriteria(Criteria.where("updatedOn").lte(trainingQueryParamsDto.getUpdatedOnMax()));
        }

        return query;
    }

    public static void validateCreateOrUpdateTrainingDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateTrainingQueryParamsDto(TrainingQueryParamsDto trainingQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<TrainingQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(trainingQueryParamsDto);

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