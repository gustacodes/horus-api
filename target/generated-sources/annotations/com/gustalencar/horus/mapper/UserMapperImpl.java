package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Company;
import com.gustalencar.horus.entity.CompanyOccupation;
import com.gustalencar.horus.entity.User;
import javax.annotation.processing.Generated;
import models.enums.UserRole;
import models.requests.CreateCompanyOccupationRequest;
import models.requests.CreateUserHorusRequest;
import models.responses.CompanyHorusResponse;
import models.responses.UserHorusResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-20T19:02:08-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserHorusResponse fromEntity(User entity) {
        if ( entity == null ) {
            return null;
        }

        CompanyHorusResponse cmpId = null;
        Long id = null;
        String name = null;
        UserRole role = null;

        if ( entity.getCmpId() != null ) {
            cmpId = companyToCompanyHorusResponse( entity.getCmpId() );
        }
        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getName() != null ) {
            name = entity.getName();
        }
        if ( entity.getRole() != null ) {
            role = entity.getRole();
        }

        String position = null;
        CreateCompanyOccupationRequest profile = null;

        UserHorusResponse userHorusResponse = new UserHorusResponse( id, name, position, role, profile, cmpId );

        return userHorusResponse;
    }

    @Override
    public User fromRequest(CreateUserHorusRequest createUserHorusRequest) {
        if ( createUserHorusRequest == null ) {
            return null;
        }

        User user = new User();

        if ( createUserHorusRequest.companyOccupationId() != null ) {
            user.setCompanyOccupationId( createCompanyOccupationRequestToCompanyOccupation( createUserHorusRequest.companyOccupationId() ) );
        }
        if ( createUserHorusRequest.name() != null ) {
            user.setName( createUserHorusRequest.name() );
        }
        if ( createUserHorusRequest.cpf() != null ) {
            user.setCpf( createUserHorusRequest.cpf() );
        }
        if ( createUserHorusRequest.status() != null ) {
            user.setStatus( createUserHorusRequest.status() );
        }
        if ( createUserHorusRequest.username() != null ) {
            user.setUsername( createUserHorusRequest.username() );
        }
        if ( createUserHorusRequest.password() != null ) {
            user.setPassword( createUserHorusRequest.password() );
        }
        if ( createUserHorusRequest.dateRegister() != null ) {
            user.setDateRegister( createUserHorusRequest.dateRegister() );
        }
        if ( createUserHorusRequest.role() != null ) {
            user.setRole( Enum.valueOf( UserRole.class, createUserHorusRequest.role() ) );
        }

        return user;
    }

    protected CompanyHorusResponse companyToCompanyHorusResponse(Company company) {
        if ( company == null ) {
            return null;
        }

        Long id = null;
        String cnpj = null;
        String email = null;
        String status = null;
        String phone = null;
        String address = null;

        if ( company.getId() != null ) {
            id = company.getId();
        }
        if ( company.getCnpj() != null ) {
            cnpj = company.getCnpj();
        }
        if ( company.getEmail() != null ) {
            email = company.getEmail();
        }
        if ( company.getStatus() != null ) {
            status = company.getStatus();
        }
        if ( company.getPhone() != null ) {
            phone = company.getPhone();
        }
        if ( company.getAddress() != null ) {
            address = company.getAddress();
        }

        String companyName = null;

        CompanyHorusResponse companyHorusResponse = new CompanyHorusResponse( id, companyName, cnpj, email, status, phone, address );

        return companyHorusResponse;
    }

    protected CompanyOccupation createCompanyOccupationRequestToCompanyOccupation(CreateCompanyOccupationRequest createCompanyOccupationRequest) {
        if ( createCompanyOccupationRequest == null ) {
            return null;
        }

        CompanyOccupation.CompanyOccupationBuilder companyOccupation = CompanyOccupation.builder();

        if ( createCompanyOccupationRequest.name() != null ) {
            companyOccupation.name( createCompanyOccupationRequest.name() );
        }
        if ( createCompanyOccupationRequest.description() != null ) {
            companyOccupation.description( createCompanyOccupationRequest.description() );
        }
        if ( createCompanyOccupationRequest.workload() != null ) {
            companyOccupation.workload( createCompanyOccupationRequest.workload() );
        }

        return companyOccupation.build();
    }
}
