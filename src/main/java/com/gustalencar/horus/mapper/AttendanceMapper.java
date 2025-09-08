package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Attendance;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.AttendanceHorusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface AttendanceMapper {

    AttendanceHorusResponse fromEntity(final Attendance entity);

    @Mapping(target = "id", ignore = true)
    Attendance fromRequest(CreateAttendanceHorusRequest createAttendanceRequest);
}
