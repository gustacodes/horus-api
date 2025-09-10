package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.User;
import com.gustalencar.horus.infra.util.Util;
import com.gustalencar.horus.mapper.UserMapper;
import com.gustalencar.horus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.enums.UserRole;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserHorusRequest;
import models.responses.UserHorusResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final CompanyService companyService;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final CompanyOccupationService companyOccupationService;

    public UserHorusResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    public User find(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User not found. Id: " + id + ", Type: " + UserHorusResponse.class.getSimpleName()
        ));
    }

    public List<UserHorusResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserHorusResponse> findAllByStatusAndRole(String status, UserRole role) {
        return repository.findAllByStatusAndRole(status, role)
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    public void save(CreateUserHorusRequest request, byte[] fingerprintTemplate) {
        verifyIfCpfAlreadyExists(request.cpf());
        verifyUsernameAlreadyExists(request.username());
        User user = mapper.fromRequest(request).withPassword(encoder.encode(request.password()));
        var company = companyService.find(request.companyOccupationId().cmpId());
        var role = companyOccupationService.find(request.companyOccupationId().coId());
        user.setName(Util.removeAccents(Util.removeAccents(request.name())).toUpperCase());
        user.setCompanyOccupationId(role);
        user.setCmpId(company);
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

    private void verifyUsernameAlreadyExists(final String username) {
        repository.findByUsername(username)
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Username [ " + username + " ] already exists");
                });
    }

}
