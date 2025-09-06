package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.entity.EmployeeDailyBalance;
import models.requests.CreateHorusEmployeeDailyBalance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface EmployeeDailyBalanceMapper {
    @Mapping(target = "id", ignore = true)
    EmployeeDailyBalance fromRequest(CreateHorusEmployeeDailyBalance createHorusEmployeeDailyBalance);
}
