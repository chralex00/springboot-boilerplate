package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BiologicalGender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    @Getter
    private String biologicalGender;
}