package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.CompanyOccupationController;
import com.gustalencar.horus.service.CompanyOccupationService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateCompanyOccupationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class CompanyOccupationControllerImpl implements CompanyOccupationController {

    private final CompanyOccupationService service;

    @Override
    public ResponseEntity<Void> save(CreateCompanyOccupationRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED).build();
    }
}
