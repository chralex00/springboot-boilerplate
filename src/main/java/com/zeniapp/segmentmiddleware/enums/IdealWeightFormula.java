package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum IdealWeightFormula {
    HAMWI_EQUATION("HAMWI_EQUATION"),
    DEVINE_EQUATION("DEVINE_EQUATION"),
    ROBINSON_EQUATION("ROBINSON_EQUATION"),
    MILLER_EQUATION("MILLER_EQUATION"),
    CREFF_EQUATION("CREFF_EQUATION"),
    BROCA_EQUATION("BROCA_EQUATION");

    @Getter
    private String basalMetabolicRateFormula;
}