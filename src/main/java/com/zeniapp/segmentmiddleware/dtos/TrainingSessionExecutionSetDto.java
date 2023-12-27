package com.zeniapp.segmentmiddleware.dtos;

import com.zeniapp.segmentmiddleware.enums.IntensityMeasureUnit;
import com.zeniapp.segmentmiddleware.enums.IntensityTechnique;
import com.zeniapp.segmentmiddleware.enums.SetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingSessionExecutionSetDto {
    @Getter
    @Setter
    private String reps;
    
    @Getter
    @Setter
    private String intensity;

    @Getter
    @Setter
    private String timeUnderTension;

    @Getter
    @Setter
    private IntensityMeasureUnit intensityMeasureUnit;

    @Getter
    @Setter
    private IntensityTechnique intensityTechnique;
    
    @Getter
    @Setter
    private SetType type;
}