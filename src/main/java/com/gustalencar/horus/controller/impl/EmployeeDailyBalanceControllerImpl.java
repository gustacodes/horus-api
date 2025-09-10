package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.EmployeeDailyBalanceController;
import com.gustalencar.horus.service.EmployeeDailyBalanceService;
import lombok.RequiredArgsConstructor;
import models.responses.EmployeeDailyBalancerResponse;
import models.responses.UserCompanyEmployeeDailyBalanceResponse;
import models.responses.UserHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class EmployeeDailyBalanceControllerImpl implements EmployeeDailyBalanceController {

    private final EmployeeDailyBalanceService service;

    @Override
    public ResponseEntity<List<EmployeeDailyBalancerResponse>> findById(Long cmpId) {
        return ResponseEntity.status(OK).body(service.findAllByCompany(cmpId));
    }

    @Override
    public ResponseEntity<List<UserCompanyEmployeeDailyBalanceResponse>> findUsersByCompanyAndEmployeeDailyBalance(final Long cmpId) {
        return ResponseEntity.status(OK).body(service.findUsersByCompanyAndEmployeeDailyBalance(cmpId));
    }
}
