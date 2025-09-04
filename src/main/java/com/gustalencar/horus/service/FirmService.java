package com.gustalencar.horus.service;

import com.gustalencar.horus.entity.Firm;
import com.gustalencar.horus.mapper.FirmMapper;
import com.gustalencar.horus.repository.FirmRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateFirmHorusRequest;
import models.responses.FirmHorusResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirmService {

    private final FirmRepository repository;
    private final FirmMapper mapper;

    public FirmHorusResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    public void save(CreateFirmHorusRequest createFirmHorusRequest) {
        verifyIfCnpjAlreadyExists(createFirmHorusRequest.cnpj(), null);
        repository.save(mapper.fromRequest(createFirmHorusRequest));
    }

    public Firm find(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Firm not found. Id: " + id + ", Type: " + FirmHorusResponse.class.getSimpleName()
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
