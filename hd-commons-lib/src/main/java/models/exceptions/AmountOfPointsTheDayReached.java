package models.exceptions;

public class AmountOfPointsTheDayReached extends RuntimeException {
    public AmountOfPointsTheDayReached(String message) {
        super(message);
    }
}
