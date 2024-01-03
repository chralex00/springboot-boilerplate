package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum IdealWeightFormula {
    LORENZ_EQUATION("LORENZ_EQUATION"),
    KEYS_EQUATION("KEYS_EQUATION"),
    BERTHEAN_EQUATION("BERTHEAN_EQUATION"),
    HAMWI_EQUATION("HAMWI_EQUATION"),
    DEVINE_EQUATION("DEVINE_EQUATION"),
    ROBINSON_EQUATION("ROBINSON_EQUATION"),
    MILLER_EQUATION("MILLER_EQUATION"),
    BROCA_EQUATION("BROCA_EQUATION");

    @Getter
    private String basalMetabolicRateFormula;
}