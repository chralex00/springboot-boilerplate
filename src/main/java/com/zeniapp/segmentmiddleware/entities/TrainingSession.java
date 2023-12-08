package com.zeniapp.segmentmiddleware.entities;

import java.util.List;
import com.zeniapp.segmentmiddleware.enums.EffortLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingSession {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String recommendedStartingTime; // expressed in HH:mm format

    @Getter
    @Setter
    private String estimatedDuration; // expressed in HH:mm format

    @Getter
    @Setter
    private EffortLevel estimatedEffortLevel; // expressed in HH:mm format

    @Getter
    @Setter
    private List<TrainingSessionExecution> executions;
}
