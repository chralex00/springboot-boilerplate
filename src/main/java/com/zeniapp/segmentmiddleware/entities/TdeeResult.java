package com.zeniapp.segmentmiddleware.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TdeeResult {
    @Getter
    @Setter
    private Float idealBodyWeight; // expressed in kg, it can be calculated, or inserted manually

    @Getter
    @Setter
    private Float totalEnergy; // expressed in kcal per day

    @Getter
    @Setter
    private Float basalMetabolicRateEnergy; // BMR, expressed in kcal per day, it can be calculated, or inserted manually
    
    @Getter
    @Setter
    private Float thermogenicEffectOfFoodEnergy; // TEF, expressed in kcal per day
    
    @Getter
    @Setter
    private Float nonExerciseActivityThermogenesisEnergy; // NEAT, expressed in kcal per day
    
    @Getter
    @Setter
    private Float thermicEffectOfActivityEnergy; // TEA, expressed in kcal per day

    @Getter
    @Setter
    private Float carbs; // expressed in grams per kg of body weight

    @Getter
    @Setter
    private Float proteins; // expressed in grams per kg of body weight

    @Getter
    @Setter
    private Float fats; // expressed in grams per kg of body weight
}