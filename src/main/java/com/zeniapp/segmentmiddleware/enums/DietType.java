package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DietType {
    STANDARD_DIET("STANDARD_DIET"),
    LOW_CALORIE("LOW_CALORIE"),
    NORMAL_CALORIE("NORMAL_CALORIE"),
    HIGH_CALORIE("HIGH_CALORIE"),
    LOW_PROTEIN("LOW_PROTEIN"),
    LOW_CARB("LOW_CARB"),
    LOW_FAT("LOW_FAT"),
    HIGH_PROTEIN("HIGH_PROTEIN"),
    HIGH_CARB("HIGH_CARB"),
    HIGH_FAT("HIGH_FAT"),
    MEDITERRANEAN("MEDITERRANEAN"),
    KETOGENIC("KETOGENIC"),
    INTERMITTENT_FASTING("INTERMITTENT_FASTING"),
    DETOX("DETOX"),
    PALEO("PALEO"),
    ZONE("ZONE"),
    VEGAN("VEGAN"),
    FRUCTARIAN("FRUCTARIAN"),
    VEGETARIAN("VEGETARIAN"),
    RAW("RAW"),
    WARRIOR("WARRIOR"),
    MEAL_REPLACEMENTS("MEAL_REPLACEMENTS");
    
    @Getter
    private String dietType;
}
