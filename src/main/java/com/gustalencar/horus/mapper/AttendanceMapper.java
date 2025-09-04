package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Attendance;
import com.gustalencar.horus.entity.Firm;
import models.requests.CreateAttendanceHorusRequest;
import models.requests.CreateFirmHorusRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface AttendanceMapper {

    @Mapping(target = "id", ignore = true)
    Attendance fromRequest(CreateAttendanceHorusRequest createAttendanceRequest);
}
