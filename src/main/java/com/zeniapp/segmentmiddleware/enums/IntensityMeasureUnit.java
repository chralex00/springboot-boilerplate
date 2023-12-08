package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum IntensityMeasureUnit {
    RPE("RPE"),
    RM("RM"),
    PERCENTAGE("PERCENTAGE"),
    BUFFER("BUFFER");
    
    @Getter
    private String intensityMeasureUnit;
}