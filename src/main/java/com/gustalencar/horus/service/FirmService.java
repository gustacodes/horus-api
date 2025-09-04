package com.gustalencar.horus.service;

import com.gustalencar.horus.mapper.FirmMapper;
import com.gustalencar.horus.repository.FirmRepository;
import com.gustalencar.horus.util.Utils;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import models.requests.CreateFirmHorusRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FirmService {

    private final FirmRepository repository;
    private final FirmMapper mapper;

    public void save(CreateFirmHorusRequest createFirmHorusRequest) {
        repository.save(mapper.fromRequest(createFirmHorusRequest));
    }
}
