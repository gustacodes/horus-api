package models.exceptions;

public class NotUpdateHoursUserException extends RuntimeException {
    public NotUpdateHoursUserException(String message) {
        super(message);
    }
}
