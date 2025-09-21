package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Attendance;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import models.enums.AttendanceStatusEnum;
import models.enums.AttendanceTypeEnum;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.AttendanceHorusResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-20T19:02:08-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class AttendanceMapperImpl implements AttendanceMapper {

    @Override
    public AttendanceHorusResponse fromEntity(Attendance entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        LocalDateTime dateTime = null;
        String type = null;
        String location = null;
        String observation = null;
        String status = null;

        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getDateTime() != null ) {
            dateTime = entity.getDateTime();
        }
        if ( entity.getType() != null ) {
            type = entity.getType().name();
        }
        if ( entity.getLocation() != null ) {
            location = entity.getLocation();
        }
        if ( entity.getObservation() != null ) {
            observation = entity.getObservation();
        }
        if ( entity.getStatus() != null ) {
            status = entity.getStatus().name();
        }

        AttendanceHorusResponse attendanceHorusResponse = new AttendanceHorusResponse( id, dateTime, type, location, observation, status );

        return attendanceHorusResponse;
    }

    @Override
    public Attendance fromRequest(CreateAttendanceHorusRequest createAttendanceRequest) {
        if ( createAttendanceRequest == null ) {
            return null;
        }

        Attendance.AttendanceBuilder attendance = Attendance.builder();

        if ( createAttendanceRequest.dateTime() != null ) {
            attendance.dateTime( createAttendanceRequest.dateTime() );
        }
        if ( createAttendanceRequest.type() != null ) {
            attendance.type( Enum.valueOf( AttendanceTypeEnum.class, createAttendanceRequest.type() ) );
        }
        if ( createAttendanceRequest.location() != null ) {
            attendance.location( createAttendanceRequest.location() );
        }
        if ( createAttendanceRequest.observation() != null ) {
            attendance.observation( createAttendanceRequest.observation() );
        }
        if ( createAttendanceRequest.status() != null ) {
            attendance.status( Enum.valueOf( AttendanceStatusEnum.class, createAttendanceRequest.status() ) );
        }

        return attendance.build();
    }
}
