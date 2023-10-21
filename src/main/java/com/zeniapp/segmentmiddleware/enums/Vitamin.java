package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Vitamin {
    A("A"),
    D("D"),
    E("E"),
    K("K"),
    B1("B1"),
    B2("B2"),
    B3("B3"),
    B5("B5"),
    B6("B6"),
    B8("B8"),
    B9("B9"),
    B12("B12"),
    C("C");
    
    @Getter
    private String vitamin;
}