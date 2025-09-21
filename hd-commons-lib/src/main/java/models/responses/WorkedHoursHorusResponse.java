package models.responses;

import java.io.Serial;
import java.io.Serializable;

public record WorkedHoursHorusResponse(
        String workedHours,
        String extraHours,
        String balance,
        String status)
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}