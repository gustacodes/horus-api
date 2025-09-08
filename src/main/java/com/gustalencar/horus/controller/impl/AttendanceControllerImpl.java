package com.gustalencar.horus.controller.impl;

import com.gustalencar.horus.controller.AttendanceController;
import com.gustalencar.horus.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.AttendanceAdjustmentsUserResponse;
import models.responses.WorkedHoursHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<List<WorkedHoursHorusResponse>> calculateWorkedHours() {
        return ResponseEntity.ok(service.calculateWorkedHours());
    }

    @Override
    public ResponseEntity<List<AttendanceAdjustmentsUserResponse>> adjustmentsUserHoursResponse(final String cpf, final String data) {
        return ResponseEntity.status(OK).body(service.adjustmentsUserResponse(cpf, data));
    }

    @Override
    public ResponseEntity<Void> updateAdjustmentHourUser(String hour, Long attId, String type) {
        service.updateAdjustmentHourUser(hour, attId, type);
        return ResponseEntity.ok().build();
    }

}
