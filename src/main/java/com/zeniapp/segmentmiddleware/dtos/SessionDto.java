package com.zeniapp.segmentmiddleware.dtos;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SessionDto {
    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private Integer apiCounter;

    @Setter
    @Getter
    private Timestamp createdOn;

    @Setter
    @Getter
    private Timestamp lastActivityOn;
}
