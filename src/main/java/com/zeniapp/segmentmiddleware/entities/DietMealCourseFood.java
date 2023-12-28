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
public class DietMealCourseFood { // partial type of the Food entity
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private Integer energy; // expressed in kcal/referencePortion

    @Getter
    @Setter
    private Integer referencePortion; // expressed in grams

    @Getter
    @Setter
    private Integer proteins; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer simplexCarbs; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer complexCarbs; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer saturatedFats; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer unsaturatedFats; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer fibers; // expressed in grams/referencePortion

    @Getter
    @Setter
    private Integer cholesterol; // expressed in milligrams/referencePortion

    @Getter
    @Setter
    private List<String> vitamins; // top 5 vitamins most present

    @Getter
    @Setter
    private List<String> minerals; // top 5 minerals most present
}