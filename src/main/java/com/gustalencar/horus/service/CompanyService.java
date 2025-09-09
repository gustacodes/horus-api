package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Company;
import com.gustalencar.horus.mapper.CompanyMapper;
import com.gustalencar.horus.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateCompanyHorusRequest;
import models.responses.CompanyHorusResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public CompanyHorusResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    public void save(CreateCompanyHorusRequest createFirmHorusRequest) {
        verifyIfCnpjAlreadyExists(createFirmHorusRequest.cnpj(), null);
        repository.save(mapper.fromRequest(createFirmHorusRequest));
    }

    public Company find(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Company not found. Id: " + id + ", Type: " + CompanyHorusResponse.class.getSimpleName()
        ));
    }

    private void verifyIfCnpjAlreadyExists(final String cnpj, final Long id) {
        repository.findByCnpj(cnpj)
                .filter(firm -> !firm.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("CNPJ [ " + cnpj + " ] already exists");
                });
    }
}
