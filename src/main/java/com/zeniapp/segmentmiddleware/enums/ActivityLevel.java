package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActivityLevel {
    SEDENTARY("SEDENTARY"), // office job
    LIGHT("LIGHT"), // 1-2 times per week
    MODERATE("MODERATE"), // 3-5 times per week
    HEAVY("HEAVY"), // 6-7 times per week
    VERY_HEAVY("VERY_HEAVY"); // 2 times per day
    
    @Getter
    private String activityLevel;
}