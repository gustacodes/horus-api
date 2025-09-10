package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.EmployeeDailyBalance;
import com.gustalencar.horus.mapper.EmployeeDailyBalanceMapper;
import com.gustalencar.horus.repository.EmployeeDailyBalanceRepository;
import lombok.RequiredArgsConstructor;
import models.responses.EmployeeDailyBalancerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDailyBalanceService {

    private final EmployeeDailyBalanceRepository repository;
    private final EmployeeDailyBalanceMapper mapper;
    private final CompanyService companyService;

    public void save(EmployeeDailyBalance employeeDailyBalance) {
        repository.save(employeeDailyBalance);
    }

    public List<EmployeeDailyBalancerResponse> findAllByCompany(Long cmpId) {
        var company = companyService.find(cmpId);
        return repository.findAllByCompany(company).stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

}
