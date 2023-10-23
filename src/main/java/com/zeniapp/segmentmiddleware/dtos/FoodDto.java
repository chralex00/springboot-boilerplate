package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FoodDto {
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private FoodCategory category;

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
    
    @Getter
    @Setter
    private Boolean isPublished;
}