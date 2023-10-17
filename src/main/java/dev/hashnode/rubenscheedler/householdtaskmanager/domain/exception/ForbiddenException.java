package dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
