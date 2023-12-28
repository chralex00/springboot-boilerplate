package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DietMeal {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private List<DietMealCourse> courses;
}