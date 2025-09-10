package com.gustalencar.horus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.EmployeeDailyBalancerResponse;
import models.responses.UserHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "EmployeeDailyBalancerController", description = "Controller responsible for user balancer daily")
@RequestMapping("/api/employeeDailyBalancer")
public interface EmployeeDailyBalanceController {

    @GetMapping("/{cmpId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER', 'MANAGER')")
    @Operation(summary = "Find by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee daily balancer found"),
            @ApiResponse(responseCode = "404", description = "Employee daily balancer not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<List<EmployeeDailyBalancerResponse>> findById(
            @Parameter(description = "Company id", required = true, example = "3")
            @PathVariable(name = "cmpId") final Long cmpId);
}
