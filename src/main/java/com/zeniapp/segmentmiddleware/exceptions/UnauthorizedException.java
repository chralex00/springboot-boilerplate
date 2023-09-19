package com.zeniapp.segmentmiddleware.exceptions;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class UnauthorizedException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public UnauthorizedException() {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        this.errorResponseDto.setMessages(Arrays.asList(new String[] { "unauthorized" }));
    }
}
