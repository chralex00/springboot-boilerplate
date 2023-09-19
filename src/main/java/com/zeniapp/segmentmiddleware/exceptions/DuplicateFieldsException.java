package com.zeniapp.segmentmiddleware.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class DuplicateFieldsException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public DuplicateFieldsException(List<String> errorMessages) {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.CONFLICT.getReasonPhrase());
        this.errorResponseDto.setMessages(errorMessages);
    }
}
