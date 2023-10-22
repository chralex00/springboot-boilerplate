package com.zeniapp.segmentmiddleware.dtos;

import java.util.List;
import com.zeniapp.segmentmiddleware.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivityDto {
    @Setter
    @Getter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;
    
    @Getter
    @Setter
    private ActivityType type;

    @Getter
    @Setter
    private Integer mets;

    @Getter
    @Setter
    private List<String> tags;

    @Getter
    @Setter
    private List<ExerciseDto> mainExercises;

    @Getter
    @Setter
    private Boolean isPublished;
}
