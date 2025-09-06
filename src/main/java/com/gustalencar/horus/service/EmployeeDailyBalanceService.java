package com.gustalencar.horus.service;

import com.gustalencar.horus.mapper.EmployeeDailyBalanceMapper;
import com.gustalencar.horus.repository.EmployeeDailyBalanceRepository;
import lombok.RequiredArgsConstructor;
import models.requests.CreateHorusEmployeeDailyBalance;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDailyBalanceService {

    private final EmployeeDailyBalanceRepository repository;
    private final EmployeeDailyBalanceMapper mapper;

    public void save(CreateHorusEmployeeDailyBalance createHorusEmployeeDailyBalance) {
        repository.save(mapper.fromRequest(createHorusEmployeeDailyBalance));
    }
}
