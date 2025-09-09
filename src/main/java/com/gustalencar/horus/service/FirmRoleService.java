package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.FirmRole;
import com.gustalencar.horus.mapper.FirmRoleMapper;
import com.gustalencar.horus.repository.FirmRoleRespository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateFirmRoleRequest;
import models.responses.FirmRoleHorusResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirmRoleService {

    private final FirmRoleRespository repository;
    private final FirmRoleMapper mapper;
    private final CompanyService companyService;

    public void save(CreateFirmRoleRequest firmRoleRequest) {
        var firm = companyService.find(firmRoleRequest.firmId());
        FirmRole firmRole = mapper.fromRequest(firmRoleRequest);
        firmRole.setCompany(firm);
        repository.save(firmRole);
    }

    public FirmRoleHorusResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    public FirmRole find(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Role not found. Id: " + id + ", Type: " + FirmRoleHorusResponse.class.getSimpleName()
        ));
    }

    public List<FirmRoleHorusResponse> findAllByFirmId(final Long id) {
        return repository.findAllByCompany_Id(id)
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

}
