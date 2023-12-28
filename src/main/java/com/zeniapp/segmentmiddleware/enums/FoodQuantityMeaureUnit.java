package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FoodQuantityMeaureUnit {
    PORTIONS("PORTIONS"),
    GRAMS("GRAMS"),
    KILOGRAM("KILOGRAM"),
    LITER("LITER"),
    MILLILITER("MILLILITER"),
    HECTOGRAM("HECTOGRAM");
    
    @Getter
    private String foodQuantityMeaureUnit;
}
