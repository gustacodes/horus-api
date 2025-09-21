package models.responses;

import java.time.LocalDateTime;

public record CompanyOccupationHorusResponse(
        Long id,
        Long cmpId,
        String name,
        String description,
        String workload,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
