package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BasalMetabolicRateFormula {
    MIFFLIN_ST_JEOR_EQUATION("MIFFLIN_ST_JEOR_EQUATION"),
    HARRIS_BENEDICT_EQUATION("HARRIS_BENEDICT_EQUATION"),
    EUROPEAN_COMMISSION_LARN_BMR_TABLE("EUROPEAN_COMMISSION_LARN_BMR_TABLE");

    @Getter
    private String basalMetabolicRateFormula;
}