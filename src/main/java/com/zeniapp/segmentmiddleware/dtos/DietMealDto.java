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
public class DietMealDto {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private List<DietMealCourseDto> courses;
}