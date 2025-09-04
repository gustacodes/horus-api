package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.mapper.AttendanceMapper;
import com.gustalencar.horus.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import models.enums.AttendanceStatusEnum;
import models.requests.CreateAttendanceHorusRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository repository;
    private final AttendanceMapper mapper;
    private final UserService userService;
    private final FirmService firmService;

    public void save(CreateAttendanceHorusRequest request) {
        Attendance attendance = mapper.fromRequest(request);
        var user = userService.find(request.userId());
        var firm = firmService.find(request.firmId());
        attendance.setFirm(firm);
        attendance.setUser(user);
        attendance.setDateTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatusEnum.VALID);
        repository.save(attendance);
    }
}
