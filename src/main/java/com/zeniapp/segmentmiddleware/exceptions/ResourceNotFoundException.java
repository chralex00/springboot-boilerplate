package com.zeniapp.segmentmiddleware.exceptions;

import java.util.Arrays;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class ResourceNotFoundException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public ResourceNotFoundException() {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.NOT_FOUND.getReasonPhrase());
        this.errorResponseDto.setMessages(Arrays.asList(new String[] { "resource not found" }));
    }

    public ResourceNotFoundException(String message) {
        this();
        this.errorResponseDto.setMessages(Arrays.asList(new String[] { message }));
    }
}
