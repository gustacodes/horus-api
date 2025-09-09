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
import models.requests.CreateCompanyHorusRequest;
import models.responses.CompanyHorusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "CompanyController", description = "Controller responsible for company operations")
@RequestMapping("/api/company")
public interface CompanyController {

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER')")
    @Operation(summary = "Save new Company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<Void> save(@Valid @RequestBody CreateCompanyHorusRequest request);

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER', 'ADMIN')")
    @Operation(summary = "Find by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found"),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<CompanyHorusResponse> findById(@Parameter(description = "Company id", required = true,
            example = "1") @PathVariable(name = "id") final Long id);
}
