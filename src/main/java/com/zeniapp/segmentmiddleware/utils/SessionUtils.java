package com.zeniapp.segmentmiddleware.utils;

import java.util.Set;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import com.zeniapp.segmentmiddleware.dtos.CreateSessionDto;
import com.zeniapp.segmentmiddleware.dtos.ErrorResponseDto;
import com.zeniapp.segmentmiddleware.dtos.SessionQueryParamsDto;
import com.zeniapp.segmentmiddleware.entities.Session;
import com.zeniapp.segmentmiddleware.exceptions.AccountBlockedException;
import com.zeniapp.segmentmiddleware.exceptions.DuplicateFieldsException;
import com.zeniapp.segmentmiddleware.exceptions.SessionExpiredException;
import com.zeniapp.segmentmiddleware.exceptions.UnauthorizedException;
import com.zeniapp.segmentmiddleware.exceptions.WrongPayloadException;
import com.zeniapp.segmentmiddleware.services.SessionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

public class SessionUtils {
    public static Session checkSession(SessionService sessionService, String sessionId, Long jwtDuration) throws SessionExpiredException, UnauthorizedException, Exception {
        if (sessionId == null) {
            throw new UnauthorizedException();
        }

        Session session = sessionService.findOne(sessionId).orElseThrow(UnauthorizedException::new);

        Date expirationDate = new Date(session.getLastActivityOn().getTime() + jwtDuration);

        if (new Date().after(expirationDate)) {
            sessionService.deleteOne(session.getId());
            throw new SessionExpiredException();
        }

        if (session.getAccount() == null) {
            sessionService.deleteOne(session.getId());
            throw new UnauthorizedException();
        }

        if (session.getAccount().getIsBlocked()) {
            sessionService.deleteOne(session.getId());
            throw new AccountBlockedException();
        }

        Integer newApiCounter = (session.getApiCounter() < 1_000_000_000) ? session.getApiCounter() + 1 : session.getApiCounter();

        session.setApiCounter(newApiCounter);
        session.setLastActivityOn(new Timestamp(new Date().getTime()));

        return sessionService.save(session);
    }

    public static List<Predicate> getPredicatesBySessionQyeryParamsDto(CriteriaBuilder criteriaBuilder, Root<Session> root, SessionQueryParamsDto sessionQueryParamsDto) throws DuplicateFieldsException {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (sessionQueryParamsDto.getApiCounterMin() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("apiCounter"), sessionQueryParamsDto.getApiCounterMin()));
        }

        if (sessionQueryParamsDto.getApiCounterMax() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("apiCounter"), sessionQueryParamsDto.getApiCounterMax()));
        }

        if (sessionQueryParamsDto.getEmail() != null) {
            predicates.add(criteriaBuilder.like(root.get("account").get("email"), "%" + sessionQueryParamsDto.getEmail() + "%"));
        }

        if (sessionQueryParamsDto.getUsername() != null) {
            predicates.add(criteriaBuilder.like(root.get("account").get("username"), "%" + sessionQueryParamsDto.getUsername() + "%"));
        }

        if (sessionQueryParamsDto.getCreatedOnMin() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdOn"), sessionQueryParamsDto.getCreatedOnMin()));
        }

        if (sessionQueryParamsDto.getCreatedOnMax() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdOn"), sessionQueryParamsDto.getCreatedOnMax()));
        }

        if (sessionQueryParamsDto.getLastActivityOnMin() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lastActivityOn"), sessionQueryParamsDto.getLastActivityOnMin()));
        }

        if (sessionQueryParamsDto.getLastActivityOnMax() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastActivityOn"), sessionQueryParamsDto.getLastActivityOnMax()));
        }
        
        return predicates;
    }

    public static void manageDuplicateFields(Optional<Session> sessionByAccountId, String accountIdentifier) throws DuplicateFieldsException {
        if (sessionByAccountId.isPresent()) {
            throw new DuplicateFieldsException(Arrays.asList(new String[] { "account already logged" }));
        }
    }

    public static void validateCreateSessionDto(CreateSessionDto createSessionDto, BindingResult bindingResult) throws WrongPayloadException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static void validateSessionQueryParamsDto(SessionQueryParamsDto sessionQueryParamsDto) throws WrongPayloadException {
        Set<ConstraintViolation<SessionQueryParamsDto>> validationErrors = Validation.buildDefaultValidatorFactory().getValidator().validate(sessionQueryParamsDto);

        if (!validationErrors.isEmpty()) {
            List<String> errorMessages = validationErrors.stream().map(ConstraintViolation::getMessage).toList();
            throw new WrongPayloadException(errorMessages);
        }
    }

    public static ErrorResponseDto getInternalServerError() {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setError(true);
        errorResponseDto.setName(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        errorResponseDto.setMessages(Arrays.asList(new String[] { "generic error occurred" }));

        return errorResponseDto;
    }
}
