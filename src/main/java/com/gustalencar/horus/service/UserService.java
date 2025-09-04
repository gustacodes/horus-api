package com.gustalencar.horus.service;

import com.gustalencar.horus.mapper.UserMapper;
import com.gustalencar.horus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserHorusRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final FirmService firmService;
    private final UserMapper mapper;

    public void save(CreateUserHorusRequest request) {
        firmService.find(request.firmId());
        repository.save(mapper.fromRequest(request));
    }

}
