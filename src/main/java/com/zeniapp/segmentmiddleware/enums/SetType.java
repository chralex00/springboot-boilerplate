package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SetType {
    WARM_UP("WARM_UP"),
    APPROACH_SET("APPROACH_SET"),
    LIGHT_SET("LIGHT_SET"),
    HEAVY_SET("HEAVY_SET");
    
    @Getter
    private String setType;
}
