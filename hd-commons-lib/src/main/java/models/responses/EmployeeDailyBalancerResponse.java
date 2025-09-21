package models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

public record EmployeeDailyBalancerResponse(
        Long id,
        UserHorusResponse user,
        CompanyHorusResponse company,
        LocalDate balanceDate,
        Long workedSeconds,
        Long expectedSeconds,
        Long balanceSeconds,
        String balanceType
) {
}
