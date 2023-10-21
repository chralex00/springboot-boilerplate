package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MuscleForm {
    FUSIFORM("FUSIFORM"),
    PARALLEL("PARALLEL"),
    CONVERGENT("CONVERGENT"),
    UNIPENNATE("UNIPENNATE"),
    BIPINNATE("BIPINNATE"),
    MULTIPINNATE("MULTIPINNATE"),
    CIRCULAR("CIRCULAR");
    
    @Getter
    private String muscleForm;
}