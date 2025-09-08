package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.FirmRoleController;
import com.gustalencar.horus.service.FirmRoleService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateFirmRoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class FirmRoleControllerImpl implements FirmRoleController {

    private final FirmRoleService service;

    @Override
    public ResponseEntity<Void> save(CreateFirmRoleRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED).build();
    }
}
