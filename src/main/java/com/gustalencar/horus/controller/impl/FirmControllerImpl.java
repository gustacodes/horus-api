package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.FirmController;
import com.gustalencar.horus.service.FirmService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateFirmHorusRequest;
import models.responses.FirmHorusResponse;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class FirmControllerImpl implements FirmController {

    private final FirmService service;

    @Override
    public ResponseEntity<Void> save(CreateFirmHorusRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED.value()).build();
    }

    @Override
    public ResponseEntity<FirmHorusResponse> findById(final Long id) {
        return ResponseEntity.status(OK).body(service.findById(id));
    }
}
