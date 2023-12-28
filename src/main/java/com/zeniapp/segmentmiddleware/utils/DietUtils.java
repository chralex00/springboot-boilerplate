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
import com.zeniapp.segmentmiddleware.dtos.DietQueryParamsDto;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class DietUtils {
    public static Query getQueryByDietQyeryParamsDto(DietQueryParamsDto dietQueryParamsDto) {
        Query query = new Query();

        if (dietQueryParamsDto.getAccountId() != null) {
            query.addCriteria(Criteria.where("accountId").is(dietQueryParamsDto.getAccountId()));
        }

        if (dietQueryParamsDto.getTags() != null) {
            query.addCriteria(Criteria.where("tags").all(dietQueryParamsDto.getTags()));
        }

        if (dietQueryParamsDto.getTitle() != null) {
            query.addCriteria(Criteria.where("title").regex(dietQueryParamsDto.getTitle(), "i"));
        }

        if (dietQueryParamsDto.getGoal() != null) {
            query.addCriteria(Criteria.where("goal").regex(dietQueryParamsDto.getGoal(), "i"));
        }

        if (dietQueryParamsDto.getType() != null) {
            query.addCriteria(Criteria.where("type").is(dietQueryParamsDto.getType()));
        }

        if (dietQueryParamsDto.getStartsOnMin() != null && dietQueryParamsDto.getStartsOnMax() != null) {
            query.addCriteria(Criteria.where("startsOn")
                .gte(dietQueryParamsDto.getStartsOnMin())
                .lte(dietQueryParamsDto.getStartsOnMax()));
        }
        else if (dietQueryParamsDto.getStartsOnMin() != null) {
            query.addCriteria(Criteria.where("startsOn").gte(dietQueryParamsDto.getStartsOnMin()));
        }
        else if (dietQueryParamsDto.getStartsOnMax() != null) {
            query.addCriteria(Criteria.where("startsOn").lte(dietQueryParamsDto.getStartsOnMax()));
        }

        if (dietQueryParamsDto.getDurationMin() != null && dietQueryParamsDto.getDurationMax() != null) {
            query.addCriteria(Criteria.where("duration")
                .gte(dietQueryParamsDto.getDurationMin())
                .lte(dietQueryParamsDto.getDurationMax()));
        }
        else if (dietQueryParamsDto.getDurationMin() != null) {
            query.addCriteria(Criteria.where("duration").gte(dietQueryParamsDto.getDurationMin()));
        }
        else if (dietQueryParamsDto.getDurationMax() != null) {
            query.addCriteria(Criteria.where("duration").lte(dietQueryParamsDto.getDurationMax()));
        }
        
        if (dietQueryParamsDto.getIsArchived() != null) {
            query.addCriteria(Criteria.where("isArchived").is(dietQueryParamsDto.getIsArchived()));
        }

        if (dietQueryParamsDto.getCreatedOnMin() != null && dietQueryParamsDto.getCreatedOnMax() != null) {
            query.addCriteria(Criteria.where("createdOn")
                .gte(dietQueryParamsDto.getCreatedOnMin())
                .lte(dietQueryParamsDto.getCreatedOnMax()));
        }
        else if (dietQueryParamsDto.getCreatedOnMin() != null) {
            query.addCriteria(Criteria.where("createdOn").gte(dietQueryParamsDto.getCreatedOnMin()));
        }
        else if (dietQueryParamsDto.getCreatedOnMax() != null) {
            query.addCriteria(Criteria.where("createdOn").lte(dietQueryParamsDto.getCreatedOnMax()));
        }

        if (dietQueryParamsDto.getUpdatedOnMin() != null && dietQueryParamsDto.getUpdatedOnMax() != null) {
            query.addCriteria(Criteria.where("updatedOn")
                .gte(dietQueryParamsDto.getUpdatedOnMin())
                .lte(dietQueryParamsDto.getUpdatedOnMax()));
        }
        else if (dietQueryParamsDto.getUpdatedOnMin() != null) {
            query.addCriteria(Criteria.where("updatedOn").gte(dietQueryParamsDto.getUpdatedOnMin()));
        }
        else if (dietQueryParamsDto.getUpdatedOnMax() != null) {
            query.addCriteria(Criteria.where("updatedOn").lte(dietQueryParamsDto.getUpdatedOnMax()));
        }

        return query;
    }

    public static void validateCreateOrUpdateDietDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateDietQueryParamsDto(DietQueryParamsDto dietQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<DietQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(dietQueryParamsDto);

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