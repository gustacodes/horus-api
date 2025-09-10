package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.EmployeeDailyBalance;
import com.gustalencar.horus.mapper.EmployeeDailyBalanceMapper;
import com.gustalencar.horus.repository.EmployeeDailyBalanceRepository;
import lombok.RequiredArgsConstructor;
import models.responses.EmployeeDailyBalancerResponse;
import models.responses.UserCompanyEmployeeDailyBalanceResponse;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDailyBalanceService {

    private final EmployeeDailyBalanceRepository repository;
    private final EmployeeDailyBalanceMapper mapper;
    private final CompanyService companyService;

    public void save(EmployeeDailyBalance employeeDailyBalance) {
        repository.save(employeeDailyBalance);
    }

    public List<EmployeeDailyBalancerResponse> findAllByCompany(Long cmpId) {
        var company = companyService.find(cmpId);
        return repository.findAllByCompany(company).stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserCompanyEmployeeDailyBalanceResponse> findUsersByCompanyAndEmployeeDailyBalance(final Long cmpId) {
        List<Map<String, Object>> rows = repository.findRawUsersByCompanyAndEmployeeDailyBalance(cmpId);

        return rows.stream().map(row -> UserCompanyEmployeeDailyBalanceResponse.builder()
                .companyName((String) row.get("companyName"))
                .companyCnpj((String) row.get("companyCnpj"))
                .companyEmail((String) row.get("companyEmail"))
                .companyPhone((String) row.get("companyPhone"))
                .userName((String) row.get("userName"))
                .userCpf((String) row.get("userCpf"))
                .occupationName((String) row.get("occupationName"))
                .workload((String) row.get("workload"))
                .dateTime(toLocalDateTime(row.get("dateTime")))
                .type((String) row.get("type"))
                .observation((String) row.get("observation"))
                .status((String) row.get("status"))
                .balanceDate(toLocalDateTime(row.get("balanceDate")))
                .workedSeconds(formatSecondsToHMS(row.get("workedSeconds") != null ? ((Number) row.get("workedSeconds")).longValue() : null, false))
                .expectedSeconds(formatSecondsToHMS(row.get("expectedSeconds") != null ? ((Number) row.get("expectedSeconds")).longValue() : null, false))
                .balanceSeconds(formatSecondsToHMS(row.get("balanceSeconds") != null ? ((Number) row.get("balanceSeconds")).longValue() : null, true))
                .balanceType((String) row.get("balanceType"))
                .build()
        ).toList();
    }

    private String formatSecondsToHMS(Long totalSeconds, Boolean isBalance) {
        if (totalSeconds == null) return null;
        String sign = "";

        if (isBalance) {
            sign = totalSeconds < 0 ? "-" : totalSeconds == 0 ? "" : "+";
        }

        long absSeconds = Math.abs(totalSeconds);

        long hours = absSeconds / 3600;
        long minutes = (absSeconds % 3600) / 60;
        long seconds = absSeconds % 60;

        return String.format("%s%02d:%02d", sign, hours, minutes);
    }


    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null) return null;

        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }

        if (value instanceof java.sql.Date date) {
            return date.toLocalDate().atStartOfDay();
        }

        if (value instanceof java.util.Date date) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        throw new IllegalArgumentException("Tipo de data não suportado: " + value.getClass());
    }


}
