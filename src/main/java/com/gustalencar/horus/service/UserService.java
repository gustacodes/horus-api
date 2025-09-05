package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Firm;
import com.gustalencar.horus.entity.User;
import com.gustalencar.horus.mapper.UserMapper;
import com.gustalencar.horus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserHorusRequest;
import models.responses.FirmHorusResponse;
import models.responses.UserHorusResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final FirmService firmService;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;

    public UserHorusResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    public User find(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User not found. Id: " + id + ", Type: " + UserHorusResponse.class.getSimpleName()
        ));
    }

    public void save(CreateUserHorusRequest request, byte[] fingerprintTemplate) {
        verifyIfCpfAlreadyExists(request.cpf());
        var firm = firmService.find(request.firmId());
        User user = mapper.fromRequest(request).withPassword(encoder.encode(request.password()));
        user.setFirm(firm);
        user.setStatus(request.status());
        user.setFingerprint(fingerprintTemplate);
        repository.save(user);
    }

    private void verifyIfCpfAlreadyExists(final String cpf) {
        repository.findByCpf(cpf)
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("CPF [ " + cpf + " ] already exists");
                });
    }



}
