package com.gustalencar.horus.repository;

import com.gustalencar.horus.entity.EmployeeDailyBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDailyBalanceRepository extends JpaRepository<EmployeeDailyBalance, Long> {

}
