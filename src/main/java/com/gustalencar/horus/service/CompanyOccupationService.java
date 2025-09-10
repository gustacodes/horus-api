package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.CompanyOccupation;
import com.gustalencar.horus.infra.util.Util;
import com.gustalencar.horus.mapper.CompanyOccupationMapper;
import com.gustalencar.horus.repository.CompanyOccupationRespository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateCompanyOccupationRequest;
import models.responses.CompanyOccupationHorusResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyOccupationService {

    private final CompanyOccupationRespository repository;
    private final CompanyOccupationMapper mapper;
    private final CompanyService companyService;

    public void save(CreateCompanyOccupationRequest companyOccupationRequest) {
        var company = companyService.find(companyOccupationRequest.cmpId());
        CompanyOccupation companyOccupation = mapper.fromRequest(companyOccupationRequest);
        companyOccupation.setName(Util.removeAccents(companyOccupationRequest.name()).toUpperCase());
        companyOccupation.setDescription(Util.removeAccents(companyOccupationRequest.description().toUpperCase()));
        companyOccupation.setCompany(company);
        repository.save(companyOccupation);
    }

    public CompanyOccupationHorusResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    public CompanyOccupation find(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Role not found. Id: " + id + ", Type: " + CompanyOccupationHorusResponse.class.getSimpleName()
        ));
    }

    public List<CompanyOccupationHorusResponse> findAllByCompanyId(final Long id) {
        return repository.findAllByCompany_Id(id)
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

}
