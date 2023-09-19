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
public class ErrorResponseDto {
    @Getter
    @Setter
    private Boolean error;
    
    @Getter
    @Setter
    private String name;
    
    @Getter
    @Setter
    private List<String> messages;
}
