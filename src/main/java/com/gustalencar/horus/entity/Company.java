package com.gustalencar.horus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMPANY")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMP_ID")
    private Long id;

    @Column(name = "CMP_NAME")
    private String firmName;

    @Column(name = "CMP_CNPJ")
    private String cnpj;

    @Column(name = "CMP_EMAIL")
    private String email;

    @Column(name = "CMP_PHONE")
    private String phone;

    @Column(name = "CMP_STATUS")
    private String status;

    @Column(name = "CMP_ADDRESS")
    private String address;

    @Column(name = "CMP_DATEREGISTER")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

}
