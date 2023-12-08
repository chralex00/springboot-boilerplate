package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TrainingType {
    SPLIT_ROUTINE("SPLIT_ROUTINE"),
    TOTAL_BODY("TOTAL_BODY"),
    OTHER("OTHER");
    
    @Getter
    private String trainingType;
}
