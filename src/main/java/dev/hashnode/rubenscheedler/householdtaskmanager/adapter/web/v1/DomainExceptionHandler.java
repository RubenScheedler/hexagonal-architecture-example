package dev.hashnode.rubenscheedler.householdtaskmanager.adapter.web.v1;

import dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Catches exceptions that were thrown by usecases (the domain) and maps them to corresponding
 * HTTP responses.
 */
@ControllerAdvice
public class DomainExceptionHandler {
    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<String> handleUnauthorizedException(ForbiddenException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
