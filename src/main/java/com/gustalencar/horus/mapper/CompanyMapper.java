package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Company;
import models.requests.CreateCompanyHorusRequest;
import models.responses.CompanyHorusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    Company fromRequest(CreateCompanyHorusRequest createFirmHorusRequest);

    CompanyHorusResponse fromEntity(final Company entity);
}
