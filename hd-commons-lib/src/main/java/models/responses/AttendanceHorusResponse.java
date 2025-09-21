package models.responses;

import java.time.LocalDateTime;

public record AttendanceHorusResponse(
        Long id,
        LocalDateTime dateTime,
        String type,
        String location,
        String observation,
        String status) {
}
