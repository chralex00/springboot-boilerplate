package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MealType {
    BREAKFAST("BREAKFAST"),
    SNACK("SNACK"),
    LUNCH("LUNCH"),
    DINNER("DINNER"),
    OTHER("OTHER");
    
    @Getter
    private String mealType;
}
