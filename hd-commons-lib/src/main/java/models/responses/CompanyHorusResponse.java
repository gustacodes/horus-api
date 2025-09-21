package models.responses;

import java.io.Serial;
import java.io.Serializable;

public record CompanyHorusResponse(
        Long id,
        String companyName,
        String cnpj,
        String email,
        String status,
        String phone,
        String address)
        implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
