package com.gustalencar.horus.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateFirmHorusRequest;
import models.requests.CreateUserHorusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "FirmController", description = "Controller responsible for firm operations")
@RequestMapping("/api/firm")
public interface FirmController {

    @PostMapping
    @Operation(summary = "Save new firm")
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
    ResponseEntity<Void> save(@Valid @RequestBody CreateFirmHorusRequest request);
}
