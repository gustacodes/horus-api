package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.AttendanceController;
import com.gustalencar.horus.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.FirmHorusResponse;
import models.responses.WorkedHoursHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class AttendanceControllerImpl implements AttendanceController {

    private final AttendanceService service;

    @Override
    public ResponseEntity<Void> save(CreateAttendanceHorusRequest request) {
        service.registerPoint(request);
        return ResponseEntity.status(CREATED).build();
    }

    @Override
    public ResponseEntity<WorkedHoursHorusResponse> findById(Long userId, LocalDate date) {
        return ResponseEntity.ok(service.calculateWorkedHours(userId, date));
    }
}
