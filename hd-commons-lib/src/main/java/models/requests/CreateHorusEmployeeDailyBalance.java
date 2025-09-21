package models.requests;

import lombok.Builder;
import models.responses.CompanyHorusResponse;
import models.responses.UserHorusResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CreateHorusEmployeeDailyBalance(
        UserHorusResponse user,
        CompanyHorusResponse company,
        LocalDate balanceDate,
        Long workedSeconds,
        Long expectedSeconds,
        Long balanceSeconds,
        String balanceType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}