package com.zeniapp.segmentmiddleware.dtos;

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
public class TrainingSessionDto {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String recommendedStartingTime;

    @Getter
    @Setter
    private String estimatedDuration;

    @Getter
    @Setter
    private EffortLevel estimatedEffortLevel;

    @Getter
    @Setter
    private List<TrainingSessionExecutionDto> executions;
}
