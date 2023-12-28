package com.zeniapp.segmentmiddleware.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DietMealCourse {
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
    private DietMealCourseFood food;
}