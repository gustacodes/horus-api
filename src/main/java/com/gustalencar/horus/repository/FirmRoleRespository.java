package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.FirmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmRoleRespository extends JpaRepository<FirmRole, Long> {

}
