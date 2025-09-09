package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.CompanyOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirmRoleRespository extends JpaRepository<CompanyOccupation, Long> {
    List<CompanyOccupation> findAllByCompany_Id(Long companyId);
}
