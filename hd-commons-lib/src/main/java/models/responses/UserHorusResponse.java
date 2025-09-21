package models.responses;

import models.enums.UserRole;
import models.requests.CreateCompanyOccupationRequest;

import java.io.Serial;
import java.io.Serializable;

public record UserHorusResponse(Long id, String name, String position, UserRole role, CreateCompanyOccupationRequest profile, CompanyHorusResponse cmpId) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
