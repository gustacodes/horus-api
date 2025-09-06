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
@Table(name = "FIRM")
public class Firm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FIRM_ID")
    private Long id;

    @Column(name = "FIRM_NAME")
    private String firmName;

    @Column(name = "FIRM_CNPJ")
    private String cnpj;

    @Column(name = "FIRM_EMAIL")
    private String email;

    @Column(name = "FIRM_PHONE")
    private String phone;

    @Column(name = "FIRM_STATUS")
    private String status;

    @Column(name = "FIRM_ADDRESS")
    private String address;

    @Column(name = "FIRM_DATEREGISTER")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "firm", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

}
