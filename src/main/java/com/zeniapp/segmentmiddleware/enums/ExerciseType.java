package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExerciseType {
    MULTIARTICULAR("MULTIARTICULAR"),
    BIARTICULAR("BIARTICULAR"),
    SINGLEARTICULAR("SINGLEARTICULAR");
    
    @Getter
    private String exerciseType;
}
