package com.zeniapp.segmentmiddleware.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CountResponseDto {
    @Getter
    @Setter
    private Long total;
}
