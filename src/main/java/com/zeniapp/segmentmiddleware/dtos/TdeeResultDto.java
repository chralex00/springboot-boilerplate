package com.zeniapp.segmentmiddleware.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TdeeResultDto {
    @Getter
    @Setter
    private Float idealBodyWeight;

    @Getter
    @Setter
    private Float totalEnergy;

    @Getter
    @Setter
    private Float basalMetabolicRateEnergy;
    
    @Getter
    @Setter
    private Float thermogenicEffectOfFoodEnergy;
    
    @Getter
    @Setter
    private Float nonExerciseActivityThermogenesisEnergy;
    
    @Getter
    @Setter
    private Float thermicEffectOfActivityEnergy;

    @Getter
    @Setter
    private Float carbs;

    @Getter
    @Setter
    private Float proteins;

    @Getter
    @Setter
    private Float fats;
}