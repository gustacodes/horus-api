package com.gustalencar.horus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.enums.ProfileBellopaneEnum;

import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Long id;

    @Column(name = "USR_NAME")
    private String name;

    @Column(name = "USR_CPF")
    private String cpf;

    @Column(name = "USR_STATUS")
    private String status;

    @Column(name = "USR_POSITION")
    private String position;

    @Column(name = "USR_PROFILE")
    @Enumerated(EnumType.STRING)
    private Set<ProfileBellopaneEnum> profiles;

    @ManyToOne
    @JoinColumn(name = "FIRM_ID")
    private Firm firm;

}
