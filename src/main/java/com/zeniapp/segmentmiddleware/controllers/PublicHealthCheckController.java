package com.zeniapp.segmentmiddleware.controllers;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.configs.Configs;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.HealtCheckStatusDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/healthcheck")
public class PublicHealthCheckController {
    @Autowired
    private Configs configs;

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        try {
            HealtCheckStatusDto healtCheckStatusDto = new HealtCheckStatusDto();
            healtCheckStatusDto.setServiceVersion(this.configs.getServiceVersion());
            healtCheckStatusDto.setServiceName(this.configs.getServiceName());

            return new ResponseEntity<HealtCheckStatusDto>(healtCheckStatusDto, HttpStatus.OK);
        }
        catch (Exception exception) {
            PublicHealthCheckController.log.error("error occurred returning the health check");
            PublicHealthCheckController.log.error("error message is " + exception.getMessage());

            ErrorResponseDto internalServerErrorDto = new ErrorResponseDto();
            internalServerErrorDto.setError(true);
            internalServerErrorDto.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            internalServerErrorDto.setMessages(Arrays.asList(new String[] { "generic error occurred" }));

            return new ResponseEntity<ErrorResponseDto>(internalServerErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
