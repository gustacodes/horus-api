package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCompanyOccupationRequest(

        Long coId,

        @NotNull(message = "O ID da empresa é obrigatório")
        @Schema(description = "Company id", example = "3")
        Long cmpId,

        @NotBlank(message = "O nome do cargo é obrigatório")
        @Size(max = 100, message = "O nome do cargo deve ter no máximo 100 caracteres")
        @Schema(description = "Name the profission", example = "SERVICE_GENERAL")
        String name,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        @Schema(description = "Description the profission", example = "Responsible for cleaning the company")
        String description,

        @NotBlank(message = "A carga horária é obrigatória")
        @Schema(description = "Carga horária", example = "08:00")
        String workload
) {

}
