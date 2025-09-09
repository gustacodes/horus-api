package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.CompanyController;
import com.gustalencar.horus.service.CompanyService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateCompanyHorusRequest;
import models.responses.CompanyHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class CompanyControllerImpl implements CompanyController {

    private final CompanyService service;

    @Override
    public ResponseEntity<Void> save(CreateCompanyHorusRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<CompanyHorusResponse> findById(final Long id) {
        return ResponseEntity.status(OK).body(service.findById(id));
    }
}
