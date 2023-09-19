package com.zeniapp.segmentmiddleware.exceptions;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class WrongCredentialsException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public WrongCredentialsException() {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.BAD_REQUEST.getReasonPhrase());
        this.errorResponseDto.setMessages(Arrays.asList(new String[] { "username, or password wrong" }));
    }
}
