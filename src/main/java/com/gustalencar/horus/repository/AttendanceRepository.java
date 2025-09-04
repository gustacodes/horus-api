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
    @Query("""
                SELECT a FROM Attendance a
                WHERE a.user.id = :userId
                AND FUNCTION('DATE', a.dateTime) = :date
                ORDER BY a.dateTime ASC
            """)
    List<Attendance> findByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);


}
