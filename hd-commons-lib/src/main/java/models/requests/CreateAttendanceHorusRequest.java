package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAttendanceHorusRequest(
        @Schema(description = "User id", example = "1")
        @NotNull(message = "User id cannot be null")
        Long userId,
        LocalDateTime dateTime,

        @Schema(description = "Type", example = "ENTRY")
        @NotBlank(message = "Type cannot be empty")
        String type,

        @Schema(description = "Location", example = "JATIUCA")
        String location,

        @Schema(description = "Observation", example = "User forgot to clock in")
        String observation,

        @Schema(description = "Status", example = "Status in the clock in")
        String status

) {

}
