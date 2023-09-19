package com.zeniapp.segmentmiddleware.exceptions;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class AccountBlockedException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public AccountBlockedException() {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.BAD_REQUEST.getReasonPhrase());
        this.errorResponseDto.setMessages(Arrays.asList(new String[] { "account blocked" }));
    }
}
