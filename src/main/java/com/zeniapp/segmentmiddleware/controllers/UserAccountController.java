package com.zeniapp.segmentmiddleware.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateAccountDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateMyAccountDto;
import com.zeniapp.segmentmiddleware.utils.AccountUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/accounts")
public class UserAccountController {
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AdminAccountController adminAccountController;

    @GetMapping("/me")
    public ResponseEntity<?> findMe(@RequestAttribute(name = "accountId") String accountId) {
        try {
            return this.adminAccountController.findOne(accountId);
        }
        catch (Exception exception) {
            UserAccountController.log.error("error occurred retrieving the account by the jwt");
            UserAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMe(@RequestAttribute(name = "accountId") String accountId, @Valid @RequestBody UpdateMyAccountDto updateMyAccountDto, BindingResult bindingResult) {
        try {
            UpdateAccountDto updateAccountDto = this.modelMapper.map(updateMyAccountDto, UpdateAccountDto.class);
            return this.adminAccountController.updateOne(accountId, updateAccountDto, bindingResult);
        }
        catch (Exception exception) {
            UserAccountController.log.error("error occurred retrieving the account by the jwt");
            UserAccountController.log.error("error message is " + exception.getMessage());
            return new ResponseEntity<ErrorResponseDto>(AccountUtils.getInternalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
