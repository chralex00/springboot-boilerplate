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
public class TrainingSessionExecutionDto {
    @Getter
    @Setter
    private List<TrainingSessionExecutionSetDto> sets;

    @Getter
    @Setter
    private String notes;

    @Getter
    @Setter
    private TrainingSessionExecutionExerciseDto exercise;
}
