package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.AttendanceController;
import com.gustalencar.horus.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateAttendanceHorusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AttendanceControllerImpl implements AttendanceController {

    private final AttendanceService service;

    @Override
    public ResponseEntity<Void> save(CreateAttendanceHorusRequest request) {
        service.save(request);
        return ResponseEntity.status(CREATED).build();
    }
}
