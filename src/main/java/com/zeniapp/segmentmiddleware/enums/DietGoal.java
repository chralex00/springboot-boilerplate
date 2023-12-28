package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DietGoal {
    WEIGHT_LOSS("WEIGHT_LOSS"),
    WEIGHT_INCREASE("WEIGHT_INCREASE"),
    MAINTENANCE("MAINTENANCE");
    
    @Getter
    private String dietGoal;
}
