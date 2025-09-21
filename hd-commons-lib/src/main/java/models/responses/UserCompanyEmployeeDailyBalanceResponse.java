package models.responses;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserCompanyEmployeeDailyBalanceResponse(
        String companyName,
        String companyCnpj,
        String companyEmail,
        String companyPhone,
        String userName,
        String userCpf,
        String occupationName,
        String workload,
        LocalDateTime dateTime,
        String type,
        String observation,
        String status,
        LocalDateTime balanceDate,
        String workedSeconds,
        String expectedSeconds,
        String balanceSeconds,
        String balanceType
) {
}
