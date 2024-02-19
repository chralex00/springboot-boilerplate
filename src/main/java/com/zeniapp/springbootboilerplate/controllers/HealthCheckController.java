package com.zeniapp.springbootboilerplate.controllers;

import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.springbootboilerplate.configs.Configs;
import com.zeniapp.springbootboilerplate.dtos.HealtCheckStatusDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/healthcheck")
public class HealthCheckController {
    @Autowired
    private Configs configs;

    @GetMapping
    public ResponseEntity<?> status() {
        try {
            HealtCheckStatusDto healtCheckStatusDto = new HealtCheckStatusDto();
            healtCheckStatusDto.setServiceVersion(this.configs.getServiceVersion());
            healtCheckStatusDto.setServiceName(this.configs.getServiceName());

            return new ResponseEntity<HealtCheckStatusDto>(healtCheckStatusDto, HttpStatus.OK);
        }
        catch (Exception exception) {
            HealthCheckController.log.error("error occurred returning the health check");
            HealthCheckController.log.error("error message is " + exception.getMessage());

            HashMap<String, Object> internalServerErrorDto = new HashMap<String, Object>();
            internalServerErrorDto.put("error", true);
            internalServerErrorDto.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

            return new ResponseEntity<Map<String, ?>>(internalServerErrorDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
