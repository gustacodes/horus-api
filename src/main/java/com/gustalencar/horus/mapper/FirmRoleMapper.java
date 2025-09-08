package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.FirmRole;
import models.requests.CreateFirmRoleRequest;
import models.responses.FirmRoleHorusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface FirmRoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workload", source = "workload")
    FirmRole fromRequest(CreateFirmRoleRequest request);

    FirmRoleHorusResponse fromEntity(final FirmRole entity);
}
