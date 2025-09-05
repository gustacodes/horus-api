package com.gustalencar.horus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateAttendanceHorusRequest;
import models.responses.WorkedHoursHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "AttendanceController", description = "Controller responsible for attendance operations")
@RequestMapping("/api/attendance")
public interface AttendanceController {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Save new attendance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Firm created"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<Void> save(@Valid @RequestBody CreateAttendanceHorusRequest request);

    @GetMapping("/{userId}/worked-hours")
    @PreAuthorize("hasAnyRole('SUPER', 'ADMIN')")
    @Operation(summary = "Find by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<WorkedHoursHorusResponse> findById(@Parameter(description = "User id", required = true,
            example = "7") @PathVariable(name = "userId") final Long userId, @Parameter(description = "Date work", required = true,
            example = "2025-09-04") @RequestParam final LocalDate date);

}
