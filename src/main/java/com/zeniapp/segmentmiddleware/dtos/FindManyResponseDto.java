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
public class FindManyResponseDto<T> {
    @Getter
    @Setter
    private Long total;
    
    @Getter
    @Setter
    private List<T> results;
}
