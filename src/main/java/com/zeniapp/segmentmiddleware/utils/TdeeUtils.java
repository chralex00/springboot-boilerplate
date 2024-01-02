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
import com.zeniapp.segmentmiddleware.dtos.TdeeQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Tdee;
import com.zeniapp.segmentmiddleware.entities.TdeeResult;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class TdeeUtils {
    public static Query getQueryByTdeeQyeryParamsDto(TdeeQueryParamsDto tdeeQueryParamsDto) {
        Query query = new Query();

        if (tdeeQueryParamsDto.getAccountId() != null) {
            query.addCriteria(Criteria.where("accountId").is(tdeeQueryParamsDto.getAccountId()));
        }

        if (tdeeQueryParamsDto.getTags() != null) {
            query.addCriteria(Criteria.where("tags").all(tdeeQueryParamsDto.getTags()));
        }

        if (tdeeQueryParamsDto.getTitle() != null) {
            query.addCriteria(Criteria.where("title").regex(tdeeQueryParamsDto.getTitle(), "i"));
        }

        if (tdeeQueryParamsDto.getBasalMetabolismRateFormula() != null) {
            query.addCriteria(Criteria.where("basalMetabolismRateFormula").regex(tdeeQueryParamsDto.getBasalMetabolismRateFormula(), "i"));
        }

        if (tdeeQueryParamsDto.getIdealWeightFormula() != null) {
            query.addCriteria(Criteria.where("idealWeightFormula").regex(tdeeQueryParamsDto.getIdealWeightFormula(), "i"));
        }

        if (tdeeQueryParamsDto.getPerformedOnMin() != null && tdeeQueryParamsDto.getPerformedOnMax() != null) {
            query.addCriteria(Criteria.where("performedOn")
                .gte(tdeeQueryParamsDto.getPerformedOnMin())
                .lte(tdeeQueryParamsDto.getPerformedOnMax()));
        }
        else if (tdeeQueryParamsDto.getPerformedOnMin() != null) {
            query.addCriteria(Criteria.where("performedOn").gte(tdeeQueryParamsDto.getPerformedOnMin()));
        }
        else if (tdeeQueryParamsDto.getPerformedOnMax() != null) {
            query.addCriteria(Criteria.where("performedOn").lte(tdeeQueryParamsDto.getPerformedOnMax()));
        }
        
        if (tdeeQueryParamsDto.getIsArchived() != null) {
            query.addCriteria(Criteria.where("isArchived").is(tdeeQueryParamsDto.getIsArchived()));
        }

        if (tdeeQueryParamsDto.getCreatedOnMin() != null && tdeeQueryParamsDto.getCreatedOnMax() != null) {
            query.addCriteria(Criteria.where("createdOn")
                .gte(tdeeQueryParamsDto.getCreatedOnMin())
                .lte(tdeeQueryParamsDto.getCreatedOnMax()));
        }
        else if (tdeeQueryParamsDto.getCreatedOnMin() != null) {
            query.addCriteria(Criteria.where("createdOn").gte(tdeeQueryParamsDto.getCreatedOnMin()));
        }
        else if (tdeeQueryParamsDto.getCreatedOnMax() != null) {
            query.addCriteria(Criteria.where("createdOn").lte(tdeeQueryParamsDto.getCreatedOnMax()));
        }

        if (tdeeQueryParamsDto.getUpdatedOnMin() != null && tdeeQueryParamsDto.getUpdatedOnMax() != null) {
            query.addCriteria(Criteria.where("updatedOn")
                .gte(tdeeQueryParamsDto.getUpdatedOnMin())
                .lte(tdeeQueryParamsDto.getUpdatedOnMax()));
        }
        else if (tdeeQueryParamsDto.getUpdatedOnMin() != null) {
            query.addCriteria(Criteria.where("updatedOn").gte(tdeeQueryParamsDto.getUpdatedOnMin()));
        }
        else if (tdeeQueryParamsDto.getUpdatedOnMax() != null) {
            query.addCriteria(Criteria.where("updatedOn").lte(tdeeQueryParamsDto.getUpdatedOnMax()));
        }

        return query;
    }

    public static void validateCreateOrUpdateTdeeDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateTdeeQueryParamsDto(TdeeQueryParamsDto tdeeQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<TdeeQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(tdeeQueryParamsDto);

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

    public static TdeeResult createTdeeResultByTdee(Tdee tdee) {
        TdeeResult result = new TdeeResult();

        // to do

        return result;
    }
}