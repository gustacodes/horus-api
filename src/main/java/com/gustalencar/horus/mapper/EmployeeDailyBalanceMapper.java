package com.gustalencar.horus.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gustalencar.horus.entity.EmployeeDailyBalance;
import models.requests.CreateHorusEmployeeDailyBalance;
import models.responses.EmployeeDailyBalancerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface EmployeeDailyBalanceMapper {
    @Mapping(target = "user.cmpId", ignore = true)
    EmployeeDailyBalancerResponse fromEntity(final EmployeeDailyBalance entity);

    @Mapping(target = "id", ignore = true)
    EmployeeDailyBalance fromRequest(CreateHorusEmployeeDailyBalance createHorusEmployeeDailyBalance);
}