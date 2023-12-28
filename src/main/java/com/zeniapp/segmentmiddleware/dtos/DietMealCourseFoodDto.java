package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DietMealCourseFoodDto {
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private Integer energy;

    @Getter
    @Setter
    private Integer referencePortion;

    @Getter
    @Setter
    private Integer proteins;

    @Getter
    @Setter
    private Integer simplexCarbs;

    @Getter
    @Setter
    private Integer complexCarbs;

    @Getter
    @Setter
    private Integer saturatedFats;

    @Getter
    @Setter
    private Integer unsaturatedFats;

    @Getter
    @Setter
    private Integer fibers;

    @Getter
    @Setter
    private Integer cholesterol;

    @Getter
    @Setter
    private List<String> vitamins;

    @Getter
    @Setter
    private List<String> minerals;
}