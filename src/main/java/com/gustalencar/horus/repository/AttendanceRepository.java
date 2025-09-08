package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.Attendance;
import models.responses.AttendanceAdjustmentsUserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query(value = """
                SELECT
                ATT.*
                FROM ATTENDANCE ATT
                INNER JOIN USERS USR ON USR.USR_ID = ATT.USR_ID
                WHERE USR.USR_ID = :userId
                  AND DATE(ATT.ATT_DATETIME) = :date
                  AND USR.USR_ROLE NOT IN ('SUPER', 'ADMIN')
                ORDER BY ATT.ATT_DATETIME ASC
            """, nativeQuery = true)
    List<Attendance> findByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query(value = "SELECT " +
            "ATT.ATT_ID,\n" +
            "USR.USR_ID,\n" +
            "USR.FIRM_ID,\n" +
            "USR.USR_NAME,\n" +
            "ATT.ATT_DATETIME,\n" +
            "ATT.ATT_TYPE,\n" +
            "ATT.ATT_OBSERVATION,\n" +
            "ATT.ATT_STATUS,\n" +
            "ATT.ATT_LOCATION,\n" +
            "USR.USR_PROFILE\n" +
            "FROM ATTENDANCE ATT\n" +
            "INNER JOIN USERS USR ON USR.USR_ID = ATT.USR_ID\n" +
            "WHERE USR.USR_CPF = ? AND DATE_TRUNC('day', ATT.ATT_DATETIME) = TO_DATE(?, 'DD/MM/YYYY')", nativeQuery = true)
    List<Attendance> adjustmentsHoursUser(final String cpf, final String data);

}
