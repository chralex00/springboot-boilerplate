package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MuscleDimension {
    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    BIG("BIG");
    
    @Getter
    private String muscleDimension;
}