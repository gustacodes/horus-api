package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.With;
import util.Util;

import java.time.LocalDateTime;

@With
public record CreateUserHorusRequest(
        @Schema(description = "User name", example = "Gustavo Alencar")
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name,

        @Schema(description = "CPF user", example = "835.819.880-97")
        @NotBlank(message = "Name cannot be empty")
        @Size(max = 11, message = "CPF must contain 11 characters")
        String cpf,

        @Schema(description = "Username user", example = "gustavoalencar")
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 10, max = 50, message = "Username must contain between 10 and 50 characters")
        String username,

        @Schema(description = "Password user", example = "15421542")
        @NotBlank(message = "Password cannot be empty")
        @Size(min = 5, message = "Password must contain at least 5 characters")
        String password,

        @Schema(description = "Role user", example = "USER")
        @NotBlank(message = "Role cannot be empty")
        String role,

        @Schema(description = "User profile", example = "[\"ROLE_CASHIER\", \"ROLE_BAKER\"]")
        CreateCompanyOccupationRequest companyOccupationId,

        String status,
        LocalDateTime dateRegister

) {
    public CreateUserHorusRequest {
        cpf = Util.onlyNumbers(cpf);
        if (status == null) {
            status = "A";
        }
        if (dateRegister == null) {
            dateRegister = LocalDateTime.now();
        }
    }
}
