package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.FirmRole;
import com.gustalencar.horus.mapper.FirmRoleMapper;
import com.gustalencar.horus.repository.FirmRoleRespository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateFirmRoleRequest;
import models.responses.FirmRoleHorusResponse;
import models.responses.UserHorusResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirmRoleService {

    private final FirmRoleRespository repository;
    private final FirmRoleMapper mapper;
    private final FirmService firmService;

    public void save(CreateFirmRoleRequest firmRoleRequest) {
        var firm = firmService.find(firmRoleRequest.firmId());
        FirmRole firmRole = mapper.fromRequest(firmRoleRequest);
        firmRole.setFirm(firm);
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

}
