package dev.hashnode.rubenscheedler.householdtaskmanager.domain.exception;

public class TaskDoesNotExistException extends RuntimeException {
    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
