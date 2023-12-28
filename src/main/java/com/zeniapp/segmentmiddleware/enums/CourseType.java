package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CourseType {
    STARTER("STARTER"),
    MAIN("MAIN"),
    SECOND("SECOND"),
    DESSERT("DESSERT"),
    BITTER("BITTER"),
    OTHER("OTHER");
    
    @Getter
    private String courseType;
}
