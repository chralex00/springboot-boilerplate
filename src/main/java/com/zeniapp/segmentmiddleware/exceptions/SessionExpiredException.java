package com.zeniapp.segmentmiddleware.exceptions;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class SessionExpiredException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public SessionExpiredException() {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.FORBIDDEN.getReasonPhrase());
        this.errorResponseDto.setMessages(Arrays.asList(new String[] { "session expired" }));
    }
}
