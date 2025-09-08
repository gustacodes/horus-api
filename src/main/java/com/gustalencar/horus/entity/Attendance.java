package com.gustalencar.horus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.AttendanceStatusEnum;
import models.enums.AttendanceTypeEnum;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ATTENDANCE")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_ID", nullable = false)
    private User user;

    @Column(name = "ATT_DATETIME", nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "ATT_TYPE", nullable = false, length = 20)
    private AttendanceTypeEnum type;

    @Column(name = "ATT_LOCATION", length = 255)
    private String location;

    @Column(name = "ATT_OBSERVATION", length = 500)
    private String observation;

    @Enumerated(EnumType.STRING)
    @Column(name = "ATT_STATUS", nullable = false)
    private AttendanceStatusEnum status = AttendanceStatusEnum.VALID;
}
