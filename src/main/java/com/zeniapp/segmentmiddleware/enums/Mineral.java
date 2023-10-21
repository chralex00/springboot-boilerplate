package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Mineral {
    CALCIUM("CALCIUM"),
    POTASSIUM("POTASSIUM"),
    MAGNESIUM("MAGNESIUM"),
    IRON("IRON"),
    PHOSPHORUS("PHOSPHORUS"),
    SODIUM("SODIUM"),
    ZINC("ZINC"),
    COPPER("COPPER"),
    MANGANESE("MANGANESE"),
    SELENIUM("SELENIUM");
    
    @Getter
    private String mineral;
}