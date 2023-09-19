package com.zeniapp.segmentmiddleware.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import lombok.Getter;

public class WrongPayloadException extends Exception {
    @Getter
    private ErrorResponseDto errorResponseDto;

    public WrongPayloadException(List<String> errorMessages) {
        super();
        
        this.errorResponseDto = new ErrorResponseDto();
        this.errorResponseDto.setError(true);
        this.errorResponseDto.setName(HttpStatus.BAD_REQUEST.getReasonPhrase());
        this.errorResponseDto.setMessages(errorMessages);
    }
}
