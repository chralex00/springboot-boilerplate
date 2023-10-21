package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FoodCategory {
    SWEETS("SWEETS"),
    DAIRY_OR_EGGS("DAIRY_OR_EGGS"),
    NUTS_AND_SEEDS("NUTS_AND_SEEDS"),
    FRUITS_AND_VEGETABLES("FRUITS_AND_VEGETABLES"),
    WHOLE_GRAINS_AND_LEGUMES("WHOLE_GRAINS_AND_LEGUMES"),
    OILS("OILS"),
    OTHER("OTHER");
    
    @Getter
    private String foodCategory;
}