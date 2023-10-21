package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExerciseCategory {
    FUNDAMENTAL("FUNDAMENTAL"),
    COMPLEMENTARY("COMPLEMENTARY"),
    INSULATING("INSULATING");
    
    @Getter
    private String exerciseCategory;
}
