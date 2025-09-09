package com.gustalencar.horus.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "COMPANY_OCCUPATION")
public class CompanyOccupation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CMP_ID", nullable = false)
    private Company company;

    @Column(name = "CO_NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "CO_DESCRIPTION", length = 255)
    private String description;

    @Column(name = "CO_WORKLOAD")
    private String workload;

    @Column(name = "CO_ACTIVE")
    private Boolean active;

    @Column(name = "CO_CREATEDAT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "CO_UPDATEAT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}