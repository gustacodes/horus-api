package com.gustalencar.horus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "EMPLOYEE_DAILY_BALANCE")
public class EmployeeDailyBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FIRM_ID", nullable = false)
    private Firm firm;

    @Column(name = "BALANCE_DATE", nullable = false)
    private LocalDate balanceDate;

    @Column(name = "WORKED_SECONDS", nullable = false)
    private Long workedSeconds = 0L;

    @Column(name = "EXPECTED_SECONDS", nullable = false)
    private Long expectedSeconds = 0L;

    @Column(name = "BALANCE_SECONDS", nullable = false)
    private Long balanceSeconds = 0L;

    @Column(name = "BALANCE_TYPE", nullable = false, length = 10)
    private String balanceType;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "UPDATE_AT", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}