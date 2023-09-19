package com.zeniapp.segmentmiddleware.dtos;

import java.sql.Timestamp;
import com.zeniapp.segmentmiddleware.enums.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String firstName;

    @Setter
    @Getter
    private String lastName;

    @Setter
    @Getter
    private Boolean isConfirmed;

    @Setter
    @Getter
    private Boolean isBlocked;

    @Setter
    @Getter
    private Timestamp createdOn;

    @Setter
    @Getter
    private Timestamp updatedOn;
    
    @Setter
    @Getter
    private AccountRole role;
}
