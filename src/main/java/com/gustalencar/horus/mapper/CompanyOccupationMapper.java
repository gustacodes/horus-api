package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.CompanyOccupation;
import models.requests.CreateCompanyOccupationRequest;
import models.responses.CompanyOccupationHorusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface CompanyOccupationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workload", source = "workload")
    CompanyOccupation fromRequest(CreateCompanyOccupationRequest request);

    CompanyOccupationHorusResponse fromEntity(final CompanyOccupation entity);
}
