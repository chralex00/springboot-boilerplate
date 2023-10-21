package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActivityType {
    QUOTIDIAN("QUOTIDIAN"),
    SPORT("SPORT"),
    DISCIPLINE("DISCIPLINE"),
    WORK("WORK"),
    OTHER("OTHER");
    
    @Getter
    private String activityType;
}
