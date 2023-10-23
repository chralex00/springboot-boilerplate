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
import com.zeniapp.segmentmiddleware.dtos.FoodQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.entities.Food;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class FoodUtils {
    public static Query getQueryByFoodQyeryParamsDto(FoodQueryParamsDto foodQueryParamsDto) {
        Query query = new Query();

        query.addCriteria(Criteria.where("isDeleted").is(false));
        
        if (foodQueryParamsDto.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(foodQueryParamsDto.getName(), "i"));
        }

        if (foodQueryParamsDto.getCategory() != null) {
            query.addCriteria(Criteria.where("category").is(foodQueryParamsDto.getCategory()));
        }

        if (foodQueryParamsDto.getEnergyMin() != null && foodQueryParamsDto.getEnergyMax() != null) {
            query.addCriteria(Criteria.where("energy")
                .gte(foodQueryParamsDto.getEnergyMin())
                .lte(foodQueryParamsDto.getEnergyMax()));
        }
        else if (foodQueryParamsDto.getEnergyMin() != null) {
            query.addCriteria(Criteria.where("energy").gte(foodQueryParamsDto.getEnergyMin()));
        }
        else if (foodQueryParamsDto.getEnergyMax() != null) {
            query.addCriteria(Criteria.where("energy").lte(foodQueryParamsDto.getEnergyMax()));
        }

        if (foodQueryParamsDto.getReferencePortionQuantityMin() != null && foodQueryParamsDto.getReferencePortionQuantityMax() != null) {
            query.addCriteria(Criteria.where("referencePortionQuantity")
                .gte(foodQueryParamsDto.getReferencePortionQuantityMin())
                .lte(foodQueryParamsDto.getReferencePortionQuantityMax()));
        }
        else if (foodQueryParamsDto.getReferencePortionQuantityMin() != null) {
            query.addCriteria(Criteria.where("referencePortionQuantity").gte(foodQueryParamsDto.getReferencePortionQuantityMin()));
        }
        else if (foodQueryParamsDto.getReferencePortionQuantityMax() != null) {
            query.addCriteria(Criteria.where("referencePortionQuantity").lte(foodQueryParamsDto.getReferencePortionQuantityMax()));
        }

        if (foodQueryParamsDto.getProteinsMin() != null && foodQueryParamsDto.getProteinsMax() != null) {
            query.addCriteria(Criteria.where("proteins")
                .gte(foodQueryParamsDto.getProteinsMin())
                .lte(foodQueryParamsDto.getProteinsMax()));
        }
        else if (foodQueryParamsDto.getProteinsMin() != null) {
            query.addCriteria(Criteria.where("proteins").gte(foodQueryParamsDto.getProteinsMin()));
        }
        else if (foodQueryParamsDto.getProteinsMax() != null) {
            query.addCriteria(Criteria.where("proteins").lte(foodQueryParamsDto.getProteinsMax()));
        }

        if (foodQueryParamsDto.getComplexCarbsMin() != null && foodQueryParamsDto.getComplexCarbsMax() != null) {
            query.addCriteria(Criteria.where("complexCarbs")
                .gte(foodQueryParamsDto.getComplexCarbsMin())
                .lte(foodQueryParamsDto.getComplexCarbsMax()));
        }
        else if (foodQueryParamsDto.getComplexCarbsMin() != null) {
            query.addCriteria(Criteria.where("complexCarbs").gte(foodQueryParamsDto.getComplexCarbsMin()));
        }
        else if (foodQueryParamsDto.getComplexCarbsMax() != null) {
            query.addCriteria(Criteria.where("complexCarbs").lte(foodQueryParamsDto.getComplexCarbsMax()));
        }

        if (foodQueryParamsDto.getSimplexCarbsMin() != null && foodQueryParamsDto.getSimplexCarbsMax() != null) {
            query.addCriteria(Criteria.where("simplexCarbs")
                .gte(foodQueryParamsDto.getSimplexCarbsMin())
                .lte(foodQueryParamsDto.getSimplexCarbsMax()));
        }
        else if (foodQueryParamsDto.getSimplexCarbsMin() != null) {
            query.addCriteria(Criteria.where("simplexCarbs").gte(foodQueryParamsDto.getSimplexCarbsMin()));
        }
        else if (foodQueryParamsDto.getSimplexCarbsMax() != null) {
            query.addCriteria(Criteria.where("simplexCarbs").lte(foodQueryParamsDto.getSimplexCarbsMax()));
        }

        if (foodQueryParamsDto.getSaturatedFatsMin() != null && foodQueryParamsDto.getSaturatedFatsMax() != null) {
            query.addCriteria(Criteria.where("saturatedFats")
                .gte(foodQueryParamsDto.getSaturatedFatsMin())
                .lte(foodQueryParamsDto.getSaturatedFatsMax()));
        }
        else if (foodQueryParamsDto.getSaturatedFatsMin() != null) {
            query.addCriteria(Criteria.where("saturatedFats").gte(foodQueryParamsDto.getSaturatedFatsMin()));
        }
        else if (foodQueryParamsDto.getSaturatedFatsMax() != null) {
            query.addCriteria(Criteria.where("saturatedFats").lte(foodQueryParamsDto.getSaturatedFatsMax()));
        }

        if (foodQueryParamsDto.getUnsaturatedFatsMin() != null && foodQueryParamsDto.getUnsaturatedFatsMax() != null) {
            query.addCriteria(Criteria.where("unsaturatedFats")
                .gte(foodQueryParamsDto.getUnsaturatedFatsMin())
                .lte(foodQueryParamsDto.getUnsaturatedFatsMax()));
        }
        else if (foodQueryParamsDto.getUnsaturatedFatsMin() != null) {
            query.addCriteria(Criteria.where("unsaturatedFats").gte(foodQueryParamsDto.getUnsaturatedFatsMin()));
        }
        else if (foodQueryParamsDto.getUnsaturatedFatsMax() != null) {
            query.addCriteria(Criteria.where("unsaturatedFats").lte(foodQueryParamsDto.getUnsaturatedFatsMax()));
        }

        if (foodQueryParamsDto.getFibersMin() != null && foodQueryParamsDto.getFibersMax() != null) {
            query.addCriteria(Criteria.where("fibers")
                .gte(foodQueryParamsDto.getFibersMin())
                .lte(foodQueryParamsDto.getFibersMax()));
        }
        else if (foodQueryParamsDto.getFibersMin() != null) {
            query.addCriteria(Criteria.where("fibers").gte(foodQueryParamsDto.getFibersMin()));
        }
        else if (foodQueryParamsDto.getFibersMax() != null) {
            query.addCriteria(Criteria.where("fibers").lte(foodQueryParamsDto.getFibersMax()));
        }

        if (foodQueryParamsDto.getCholesterolMin() != null && foodQueryParamsDto.getCholesterolMax() != null) {
            query.addCriteria(Criteria.where("cholesterol")
                .gte(foodQueryParamsDto.getCholesterolMin())
                .lte(foodQueryParamsDto.getFibersMax()));
        }
        else if (foodQueryParamsDto.getCholesterolMin() != null) {
            query.addCriteria(Criteria.where("cholesterol").gte(foodQueryParamsDto.getCholesterolMin()));
        }
        else if (foodQueryParamsDto.getCholesterolMax() != null) {
            query.addCriteria(Criteria.where("cholesterol").lte(foodQueryParamsDto.getCholesterolMax()));
        }

        if (foodQueryParamsDto.getVitamins() != null) {
            query.addCriteria(Criteria.where("vitamins").all(foodQueryParamsDto.getVitamins()));
        }

        if (foodQueryParamsDto.getMinerals() != null) {
            query.addCriteria(Criteria.where("minerals").all(foodQueryParamsDto.getMinerals()));
        }

        return query;
    }

    public static void manageDuplicateFields(Optional<Food> foodByName) throws DuplicateFieldsException {
        if (foodByName.isPresent()) {
            throw new DuplicateFieldsException(Arrays.asList(new String[] { "name already exists" }));
        }
    }

    public static void validateCreateOrUpdateFoodDto(BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateFoodQueryParamsDto(FoodQueryParamsDto foodQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<FoodQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(foodQueryParamsDto);

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
