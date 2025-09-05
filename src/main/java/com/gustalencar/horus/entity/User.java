package com.gustalencar.horus.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import models.enums.ProfileBellopaneEnum;
import models.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

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

    @Column(name = "USR_USERNAME")
    private String username;

    @Column(name = "USR_PASSWORD")
    private String password;

    @Column(name = "USR_POSITION")
    private String position;

    @Column(name = "USR_DATEREGISTER")
    private LocalDateTime dateRegister;

    @Column(name = "USR_PROFILE")
    @Enumerated(EnumType.STRING)
    private ProfileBellopaneEnum profile;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ADMIN;

    @ManyToOne
    @JoinColumn(name = "FIRM_ID")
    private Firm firm;

    @Lob
    @Column(name = "USR_FINGERPRINT")
    private byte[] fingerprint;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority(("ROLE_USER")));
        else  return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
