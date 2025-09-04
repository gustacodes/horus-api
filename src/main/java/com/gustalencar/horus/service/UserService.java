package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.User;
import com.gustalencar.horus.mapper.UserMapper;
import com.gustalencar.horus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserHorusRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final FirmService firmService;
    private final UserMapper mapper;

    public void save(CreateUserHorusRequest request) {
        verifyIfCpfAlreadyExists(request.cpf());
        var firm = firmService.find(request.firmId());
        User user = mapper.fromRequest(request);
        user.setFirm(firm);
        user.setStatus(request.status());
        repository.save(user);
    }

    private void verifyIfCpfAlreadyExists(final String cpf) {
        repository.findByCpf(cpf)
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("CPF [ " + cpf + " ] already exists");
                });
    }

}
