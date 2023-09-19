package com.zeniapp.segmentmiddleware.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccountRole {
    ADMIN("ADMIN"),
    USER("USER");
    
    @Getter
    private String accountRole;
}
