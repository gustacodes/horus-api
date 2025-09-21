package models.responses;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AttendanceAdjustmentsUserResponse(Long attId, Long usrId, Long cmpId, String name, String data, String hora, String attType, String attObservation, String attStatus, String usrProfile) {

}
