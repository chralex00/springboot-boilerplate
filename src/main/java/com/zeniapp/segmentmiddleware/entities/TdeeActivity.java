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
public class TdeeActivity { // partial type of the Activity entity
    @Getter
    @Setter
    private String name;
    
    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private Integer mets;

    @Getter
    @Setter
    private List<String> tags;

    @Getter
    @Setter
    private Float hours; // from 1 to 24 hous
}
