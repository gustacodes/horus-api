package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.Company;
import com.gustalencar.horus.entity.EmployeeDailyBalance;
import models.responses.UserCompanyEmployeeDailyBalanceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeDailyBalanceRepository extends JpaRepository<EmployeeDailyBalance, Long> {
    List<EmployeeDailyBalance> findAllByCompany(Company company);

    @Query(value = """
        SELECT
            CMP.CMP_NAME AS companyName,
            CMP.CMP_CNPJ AS companyCnpj,
            CMP.CMP_EMAIL AS companyEmail,
            CMP.CMP_PHONE AS companyPhone,
            USR.USR_NAME AS userName,
            USR.USR_CPF AS userCpf,
            CO.CO_NAME AS occupationName,
            CO.CO_WORKLOAD AS workload,
            ATT.ATT_DATETIME AS dateTime,
            ATT.ATT_TYPE AS type,
            ATT.ATT_OBSERVATION AS observation,
            ATT.ATT_STATUS AS status,
            EDB.BALANCE_DATE AS balanceDate,
            EDB.WORKED_SECONDS AS workedSeconds,
            EDB.EXPECTED_SECONDS AS expectedSeconds,
            EDB.BALANCE_SECONDS AS balanceSeconds,
            EDB.BALANCE_TYPE AS balanceType
        FROM COMPANY CMP
        INNER JOIN USERS USR ON USR.CMP_ID = CMP.CMP_ID
        INNER JOIN COMPANY_OCCUPATION CO ON CO.CO_ID = USR.CO_ID
        LEFT JOIN ATTENDANCE ATT ON ATT.USR_ID = USR.USR_ID
        LEFT JOIN EMPLOYEE_DAILY_BALANCE EDB ON EDB.USR_ID = USR.USR_ID
        WHERE CMP.CMP_ID = :cmpId
        AND USR.USR_ROLE NOT IN ('SUPER', 'OWNER')
    """, nativeQuery = true)
    List<Map<String, Object>> findRawUsersByCompanyAndEmployeeDailyBalance(@Param("cmpId") Long cmpId);
}
