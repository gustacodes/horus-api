package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Firm;
import com.gustalencar.horus.entity.User;
import models.requests.CreateFirmHorusRequest;
import models.requests.CreateUserHorusRequest;
import models.responses.FirmHorusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface FirmMapper {

    @Mapping(target = "id", ignore = true)
    Firm fromRequest(CreateFirmHorusRequest createFirmHorusRequest);

    FirmHorusResponse fromEntity(final Firm entity);
}
