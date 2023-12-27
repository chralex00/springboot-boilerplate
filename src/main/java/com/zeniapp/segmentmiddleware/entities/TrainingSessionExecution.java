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
public class TrainingSessionExecution {
    @Getter
    @Setter
    private List<TrainingSessionExecutionSet> sets; // max 50 sets

    @Getter
    @Setter
    private String notes;

    @Getter
    @Setter
    private TrainingSessionExecutionExercise exercise;
}
