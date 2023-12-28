package com.zeniapp.segmentmiddleware.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DietMealCourseDto {
    @Getter
    @Setter
    private String notes;
    
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Float quantity;
    
    @Getter
    @Setter
    private String quantityExpressedIn;

    
    @Getter
    @Setter
    private DietMealCourseFoodDto food;
}