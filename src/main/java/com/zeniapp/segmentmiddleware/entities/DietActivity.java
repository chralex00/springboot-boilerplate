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
public class DietActivity { // partial type of the Activity entity
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
}