package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthHorusRequest(
        @Schema(description = "CPF user", example = "08554825403")
        @NotBlank String cpf,
        @Schema(description = "User password", example = "15421542")
        @NotBlank String password)
{}
