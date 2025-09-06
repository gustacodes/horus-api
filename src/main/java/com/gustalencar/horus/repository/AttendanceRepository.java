package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query(value = """
                SELECT
                ATT.*
                FROM ATTENDANCE ATT
                INNER JOIN USERS USR ON USR.USR_ID = ATT.USR_ID
                WHERE USR.USR_ID = :userId
                  AND DATE(ATT.ATT_DATETIME) = :date
                  AND USR.ROLE NOT IN ('SUPER', 'ADMIN')
                ORDER BY ATT.ATT_DATETIME ASC
            """, nativeQuery = true)
    List<Attendance> findByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

}
