package com.zeniapp.segmentmiddleware.utils;

import java.util.Map;
import java.util.HashMap;
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
import com.zeniapp.segmentmiddleware.entities.TdeeActivity;
import com.zeniapp.segmentmiddleware.entities.TdeeResult;
import com.zeniapp.segmentmiddleware.enums.ActivityLevel;
import com.zeniapp.segmentmiddleware.enums.BasalMetabolicRateFormula;
import com.zeniapp.segmentmiddleware.enums.BiologicalGender;
import com.zeniapp.segmentmiddleware.enums.BodyWeightGoal;
import com.zeniapp.segmentmiddleware.enums.IdealWeightFormula;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class TdeeUtils {
    public static Query getQueryByTdeeQyeryParamsDto(TdeeQueryParamsDto tdeeQueryParamsDto) {
        Query query = new Query();

        if (tdeeQueryParamsDto.getAccountId() != null) {
            query.addCriteria(Criteria.where("accountId").is(tdeeQueryParamsDto.getAccountId()));
        }

        if (tdeeQueryParamsDto.getWeightGoal() != null) {
            query.addCriteria(Criteria.where("weightGoal").is(tdeeQueryParamsDto.getWeightGoal()));
        }

        if (tdeeQueryParamsDto.getTags() != null) {
            query.addCriteria(Criteria.where("tags").all(tdeeQueryParamsDto.getTags()));
        }

        if (tdeeQueryParamsDto.getTitle() != null) {
            query.addCriteria(Criteria.where("title").regex(tdeeQueryParamsDto.getTitle(), "i"));
        }

        if (tdeeQueryParamsDto.getBasalMetabolismRateFormula() != null) {
            query.addCriteria(Criteria.where("basalMetabolismRateFormula").is(tdeeQueryParamsDto.getBasalMetabolismRateFormula()));
        }

        if (tdeeQueryParamsDto.getIdealWeightFormula() != null) {
            query.addCriteria(Criteria.where("idealWeightFormula").is(tdeeQueryParamsDto.getIdealWeightFormula()));
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

        if (tdeeQueryParamsDto.getIsExerciseActivityThermogenesisCalculatedByActivityLevel() != null) {
            query.addCriteria(Criteria.where("isExerciseActivityThermogenesisCalculatedByActivityLevel").is(tdeeQueryParamsDto.getIsExerciseActivityThermogenesisCalculatedByActivityLevel()));
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

        TdeeUtils.calculateIdealBodyWeight(tdee, result);
        TdeeUtils.calculateBasalMetabolicRate(tdee, result);
        TdeeUtils.calculateTotalDailyEnergyExpenditure(tdee, result);
        TdeeUtils.calculateMacronutrient(tdee, result);

        return result;
    }

    private static void calculateIdealBodyWeight(Tdee tdee, TdeeResult result) {
        String gender = tdee.getPersonalInfo().getBiologicalGender();
        Float height = tdee.getPersonalInfo().getHeight();
        Integer age = tdee.getPersonalInfo().getAge();

        Float idealBodyWeightByBroca = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            idealBodyWeightByBroca = height - 104;
        }
        else {
            idealBodyWeightByBroca = height - 100;
        }

        Float idealBodyWeightByDevine = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            idealBodyWeightByDevine = 45.5f + (2.3f * (height - 152.4f));
        }
        else {
            idealBodyWeightByDevine = 50 + (2.3f * (height - 152.4f));
        }

        Float idealBodyWeightByHamwi = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            Float inchOverFiveFeet = ((height - 152.4f) / 2.54f);
            idealBodyWeightByHamwi = 45.5f + (2.2f * inchOverFiveFeet);
        }
        else {
            Float inchOverFiveFeet = ((height - 152.4f) / 2.54f);
            idealBodyWeightByHamwi = 48 + (2.7f * inchOverFiveFeet);
        }

        Float idealBodyWeightByMiller = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            Float inchOverFiveFeet = ((height - 152.4f) / 2.54f);
            idealBodyWeightByMiller = 53.1f + (1.36f * inchOverFiveFeet);
        }
        else {
            Float inchOverFiveFeet = ((height - 152.4f) / 2.54f);
            idealBodyWeightByMiller = 56.2f + (1.41f * inchOverFiveFeet);
        }

        Float idealBodyWeightByRobinson = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            Float inchOverFiveFeet = ((height - 152.4f) / 2.54f);
            idealBodyWeightByRobinson = 49 + (1.7f * inchOverFiveFeet);
        }
        else {
            Float inchOverFiveFeet = ((height - 152.4f) / 2.54f);
            idealBodyWeightByRobinson = 52 + (1.9f * inchOverFiveFeet);
        }

        Float idealBodyWeightByBerthean = 0.8f * (height - 100) + age / 2;

        Float idealBodyWeightByLorenz = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            idealBodyWeightByLorenz = height - 100 - (height - 150) / 2;
        }
        else {
            idealBodyWeightByLorenz = height - 100 - (height - 150) / 4;
        }

        Float idealBodyWeightByKeys = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            Float heighExpressedtInMeters = height / 100;
            idealBodyWeightByKeys = heighExpressedtInMeters * heighExpressedtInMeters * 20.6f;
        }
        else {
            Float heightExpressedInMeters = height / 100;
            idealBodyWeightByKeys = heightExpressedInMeters * heightExpressedInMeters * 22.1f;
        }

        Map<String, Float> idealBodyWeightByFormula = new HashMap<String, Float>();
        idealBodyWeightByFormula.put(IdealWeightFormula.BROCA_EQUATION.toString(), idealBodyWeightByBroca);
        idealBodyWeightByFormula.put(IdealWeightFormula.DEVINE_EQUATION.toString(), idealBodyWeightByDevine);
        idealBodyWeightByFormula.put(IdealWeightFormula.HAMWI_EQUATION.toString(), idealBodyWeightByHamwi);
        idealBodyWeightByFormula.put(IdealWeightFormula.MILLER_EQUATION.toString(), idealBodyWeightByMiller);
        idealBodyWeightByFormula.put(IdealWeightFormula.ROBINSON_EQUATION.toString(), idealBodyWeightByRobinson);
        idealBodyWeightByFormula.put(IdealWeightFormula.BERTHEAN_EQUATION.toString(), idealBodyWeightByBerthean);
        idealBodyWeightByFormula.put(IdealWeightFormula.LORENZ_EQUATION.toString(), idealBodyWeightByLorenz);
        idealBodyWeightByFormula.put(IdealWeightFormula.KEYS_EQUATION.toString(), idealBodyWeightByKeys);

        Float idealBodyWeight = idealBodyWeightByFormula.get(tdee.getIdealWeightFormula());
        result.setIdealBodyWeight(idealBodyWeight);
    }

    private static void calculateBasalMetabolicRate(Tdee tdee, TdeeResult result) {
        String gender = tdee.getPersonalInfo().getBiologicalGender();
        Integer age = tdee.getPersonalInfo().getAge();
        Float actualBodyWeight = tdee.getPersonalInfo().getActualWeight();
        Float height = tdee.getPersonalInfo().getHeight();

        Float basalMetabolicRateByEuropeanCommisionLarnBmrTable = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            if (age >= 60) {
                basalMetabolicRateByEuropeanCommisionLarnBmrTable = actualBodyWeight * 9.082f;
            }
            else if (age >= 30) {
                basalMetabolicRateByEuropeanCommisionLarnBmrTable = actualBodyWeight * 8.126f;
            }
            else {
                basalMetabolicRateByEuropeanCommisionLarnBmrTable = actualBodyWeight * 14.818f;
            }
        }
        else {
            if (age >= 60) {
                basalMetabolicRateByEuropeanCommisionLarnBmrTable = actualBodyWeight * 11.171f;
            }
            else if (age >= 30) {
                basalMetabolicRateByEuropeanCommisionLarnBmrTable = actualBodyWeight * 11.472f;
            }
            else {
                basalMetabolicRateByEuropeanCommisionLarnBmrTable = actualBodyWeight * 15.057f;
            }
        }

        Float basalMetabolicRateByHarrisBenedictEquation = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            basalMetabolicRateByHarrisBenedictEquation = 655.1f + (9.563f * actualBodyWeight) + (1.850f * height) - (4.676f * age);
        }
        else {
            basalMetabolicRateByHarrisBenedictEquation =  66.5f + (13.75f * actualBodyWeight) + (5.003f * height) - (6.75f * age);
        }

        Float basalMetabolicRateByMifflinStJeorEquation = 0.0f;
        if (gender.equals(BiologicalGender.FEMALE.toString())) {
            basalMetabolicRateByHarrisBenedictEquation = (10 * actualBodyWeight) + (6.25f * height) - (5 * age) - 161;
        }
        else {
            basalMetabolicRateByHarrisBenedictEquation = (10 * actualBodyWeight) + (6.25f * height) - (5 * age) + 5;
        }

        Map<String, Float> basalMetabolicRateByFormula = new HashMap<String, Float>();
        basalMetabolicRateByFormula.put(BasalMetabolicRateFormula.EUROPEAN_COMMISSION_LARN_BMR_TABLE.toString(), basalMetabolicRateByEuropeanCommisionLarnBmrTable);
        basalMetabolicRateByFormula.put(BasalMetabolicRateFormula.HARRIS_BENEDICT_EQUATION.toString(), basalMetabolicRateByHarrisBenedictEquation);
        basalMetabolicRateByFormula.put(BasalMetabolicRateFormula.MIFFLIN_ST_JEOR_EQUATION.toString(), basalMetabolicRateByMifflinStJeorEquation);

        Float basalMetabolicRate = basalMetabolicRateByFormula.get(tdee.getBasalMetabolismRateFormula());
        result.setBasalMetabolicRateEnergy(basalMetabolicRate);
    }

    private static void calculateTotalDailyEnergyExpenditure(Tdee tdee, TdeeResult result) {
        Float basalMetabolicRate = result.getBasalMetabolicRateEnergy();
        Float actualBodyWeight = tdee.getPersonalInfo().getActualWeight();

        if (tdee.getIsExerciseActivityThermogenesisCalculatedByActivityLevel()) {
            Map<String, Float> coefficientByActivityLevel = new HashMap<String, Float>();
            coefficientByActivityLevel.put(ActivityLevel.SEDENTARY.toString(), 0.2f);
            coefficientByActivityLevel.put(ActivityLevel.LIGHT.toString(), 0.3f);
            coefficientByActivityLevel.put(ActivityLevel.MODERATE.toString(), 0.4f);
            coefficientByActivityLevel.put(ActivityLevel.HEAVY.toString(), 0.6f);
            coefficientByActivityLevel.put(ActivityLevel.VERY_HEAVY.toString(), 0.8f);

            result.setThermicEffectOfActivityEnergy(result.getBasalMetabolicRateEnergy() * coefficientByActivityLevel.get(tdee.getPersonalInfo().getActivityLevel()));
        }
        else {
            Float exerciseActivityThermogenesis = 0.0f;
            for (TdeeActivity tdeeActivity : tdee.getPersonalInfo().getActivities()) {
                exerciseActivityThermogenesis += (tdeeActivity.getMets() * 3.5f * (actualBodyWeight / 200)) * (tdeeActivity.getHours() * 60);
            }
            result.setThermicEffectOfActivityEnergy(exerciseActivityThermogenesis);
        }

        result.setThermogenicEffectOfFoodEnergy(basalMetabolicRate * 0.1f);
        result.setNonExerciseActivityThermogenesisEnergy(basalMetabolicRate * 0.15f);

        Float totalEnergy = result.getBasalMetabolicRateEnergy() +
            result.getThermogenicEffectOfFoodEnergy() +
            result.getNonExerciseActivityThermogenesisEnergy() +
            result.getThermicEffectOfActivityEnergy();
        
        if (tdee.getWeightGoal().equals(BodyWeightGoal.WEIGHT_INCREASE.toString())) {
            totalEnergy *= 1.15f; // +15%
        }
        else if (tdee.getWeightGoal().equals(BodyWeightGoal.WEIGHT_LOSS.toString())) {
            totalEnergy *= 0.85f; // -15%
        }
        
        result.setTotalEnergy(totalEnergy);
    }

    private static void calculateMacronutrient(Tdee tdee, TdeeResult result) {
        Float actualBodyWeight = tdee.getPersonalInfo().getActualWeight();
        Float totalEnergy = result.getTotalEnergy();

        if (tdee.getWeightGoal().equals(BodyWeightGoal.WEIGHT_INCREASE.toString())) {
            result.setFats(1.5f * actualBodyWeight);
            totalEnergy -= result.getFats() * 9;

            result.setProteins(2 * actualBodyWeight);
            totalEnergy -= result.getProteins() * 4;

            result.setCarbs(totalEnergy / 4);
            totalEnergy = 0f;
        }
        else if (tdee.getWeightGoal().equals(BodyWeightGoal.WEIGHT_LOSS.toString())) {
            result.setFats(1 * actualBodyWeight);
            totalEnergy -= result.getFats() * 9;

            result.setCarbs(2.8f * actualBodyWeight);
            totalEnergy -= result.getCarbs() * 4;

            result.setProteins(totalEnergy / 4);
            totalEnergy = 0f;
        }
        else {
            result.setFats(1 * actualBodyWeight);
            totalEnergy -= result.getFats() * 9;

            result.setCarbs(3.6f * actualBodyWeight);
            totalEnergy -= result.getCarbs() * 4;

            result.setProteins(totalEnergy / 4);
            totalEnergy = 0f;
        }
    }
}