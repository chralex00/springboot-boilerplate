package com.zeniapp.segmentmiddleware.dtos;

import com.zeniapp.segmentmiddleware.enums.ActivityType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrainingActivityDto {
    @Getter
    @Setter
    private String name;
    
    @Getter
    @Setter
    private ActivityType type;

    @Getter
    @Setter
    private Integer mets;

    @Getter
    @Setter
    private List<String> tags;
}
