package com.zeniapp.segmentmiddleware.utils;

import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.zeniapp.segmentmiddleware.dtos.CreateAccountDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.AccountQueryParamsDto;
import com.zeniapp.segmentmiddleware.dtos.UpdateAccountDto;
import com.zeniapp.segmentmiddleware.entities.Account;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class AccountUtils {
    public static List<Predicate> getPredicatesByAccountQyeryParamsDto(CriteriaBuilder criteriaBuilder, Root<Account> root, AccountQueryParamsDto accountQueryParamsDto) throws DuplicateFieldsException {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (accountQueryParamsDto.getIsDeleted() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), accountQueryParamsDto.getIsDeleted()));
        }
        else {
            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
        }

        if (accountQueryParamsDto.getIsBlocked() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isBlocked"), accountQueryParamsDto.getIsBlocked()));
        }

        if (accountQueryParamsDto.getIsConfirmed() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isConfirmed"), accountQueryParamsDto.getIsConfirmed()));
        }

        if (accountQueryParamsDto.getEmail() != null) {
            predicates.add(criteriaBuilder.like(root.get("email"), "%" + accountQueryParamsDto.getEmail() + "%"));
        }

        if (accountQueryParamsDto.getUsername() != null) {
            predicates.add(criteriaBuilder.like(root.get("username"), "%" + accountQueryParamsDto.getUsername() + "%"));
        }
        
        if (accountQueryParamsDto.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + accountQueryParamsDto.getFirstName() + "%"));
        }
        
        if (accountQueryParamsDto.getLastName() != null) {
            predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + accountQueryParamsDto.getLastName() + "%"));
        }

        if (accountQueryParamsDto.getCreatedOnMin() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), accountQueryParamsDto.getCreatedOnMin()));
        }

        if (accountQueryParamsDto.getCreatedOnMax() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdOn"), accountQueryParamsDto.getCreatedOnMax()));
        }

        if (accountQueryParamsDto.getUpdatedOnMin() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("updatedOn"), accountQueryParamsDto.getUpdatedOnMin()));
        }

        if (accountQueryParamsDto.getUpdatedOnMax() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("updatedOn"), accountQueryParamsDto.getUpdatedOnMax()));
        }

        if (accountQueryParamsDto.getRole() != null) {
            predicates.add(criteriaBuilder.equal(root.get("role"), accountQueryParamsDto.getRole()));
        }
        
        return predicates;
    }

    public static void manageDuplicateFields(List<Account> accountsByEmailOrUsername, String email, String username) throws DuplicateFieldsException {
        if (!accountsByEmailOrUsername.isEmpty()) {
            List<String> errorMessages = new ArrayList<String>();
            
            for (Account accountByEmailOrUsername : accountsByEmailOrUsername) {
                if (email.equalsIgnoreCase(accountByEmailOrUsername.getEmail())) {
                    errorMessages.add("email already taken");
                }

                if (username.equalsIgnoreCase(accountByEmailOrUsername.getUsername())) {
                    errorMessages.add("username already taken");
                }
            }

            throw new DuplicateFieldsException(errorMessages);
        }
    }

    public static void validateCreateAccountDto(CreateAccountDto createAccountDto, BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateUpdateAccountDto(UpdateAccountDto updateAccountDto, BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateAccountQueryParamsDto(AccountQueryParamsDto accountQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<AccountQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(accountQueryParamsDto);

        if (!validationErrors.isEmpty()) {
            List<String> errorMessages = validationErrors.stream().map(ConstraintViolation::getMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static String hashPassword(String plainPassword, Integer saltLength, Integer hashLength, Integer memoryCosts, Integer iterations) {
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(saltLength, hashLength, 1, memoryCosts, iterations);
        return argon2PasswordEncoder.encode(plainPassword);
    }

    public static ErrorResponseDto getInternalServerError() {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setError(true);
        errorResponseDto.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponseDto.setMessages(Arrays.asList(new String[] { "generic error occurred" }));

        return errorResponseDto;
    }
}
