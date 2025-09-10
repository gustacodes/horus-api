package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.User;
import models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCpf(String cpf);

    Optional<User> findByUsername(String username);

    List<User> findAllByStatusAndRole(String status, UserRole role);
}
