package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExecutionMethod {
    SLIGHT("SLIGHT"),
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH"),
    AT_THE_LIMIT("AT_THE_LIMIT"),
    EXTREME("EXTREME");
    
    @Getter
    private String executionMethod;
}