package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.With;
import util.Util;

import java.time.LocalDateTime;

@With
public record CreateCompanyHorusRequest(
        @Schema(description = "Company name", example = "BelloPane")
        @NotBlank(message = "Company name cannot be empty")
        @Size(min = 10, max = 150, message = "Name must contain between 10 and 150 characters")
        String companyName,

        @Schema(description = "CNPJ the firm", example = "94.679.947/0001-14")
        @NotBlank(message = "CNPJ cannot be empty")
        @Size(max = 14, message = "CNPJ must contain 14 characters")
        String cnpj,

        @Schema(description = "Company email", example = "myfirm@gmail.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email cannot be empty")
        @Size(min = 6, max = 50, message = "Email must contain between 3 and 50 characters")
        String email,

        String status,

        @Schema(description = "Phone", example = "(xx) xxxxx-xxxx")
        @NotBlank(message = "Phone cannot be empty")
        String phone,

        @Schema(description = "Address", example = "R. das Árvoes, 321, Centro - Maceió")
        @NotBlank(message = "Address cannot be empty")
        @Size(max = 100, message = "Address must contain 100 characters")
        String address,

        LocalDateTime createdAt

) {
    public CreateCompanyHorusRequest {
        cnpj = Util.onlyNumbers(cnpj);
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "A";
        }
    }
}
