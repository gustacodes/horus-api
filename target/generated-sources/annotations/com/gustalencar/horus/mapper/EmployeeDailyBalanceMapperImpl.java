package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Company;
import com.gustalencar.horus.entity.EmployeeDailyBalance;
import com.gustalencar.horus.entity.User;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import models.enums.UserRole;
import models.requests.CreateCompanyOccupationRequest;
import models.requests.CreateHorusEmployeeDailyBalance;
import models.responses.CompanyHorusResponse;
import models.responses.EmployeeDailyBalancerResponse;
import models.responses.UserHorusResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-20T19:02:08-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class EmployeeDailyBalanceMapperImpl implements EmployeeDailyBalanceMapper {

    @Override
    public EmployeeDailyBalancerResponse fromEntity(EmployeeDailyBalance entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        UserHorusResponse user = null;
        CompanyHorusResponse company = null;
        LocalDate balanceDate = null;
        Long workedSeconds = null;
        Long expectedSeconds = null;
        Long balanceSeconds = null;
        String balanceType = null;

        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getUser() != null ) {
            user = userToUserHorusResponse( entity.getUser() );
        }
        if ( entity.getCompany() != null ) {
            company = companyToCompanyHorusResponse( entity.getCompany() );
        }
        if ( entity.getBalanceDate() != null ) {
            balanceDate = entity.getBalanceDate();
        }
        if ( entity.getWorkedSeconds() != null ) {
            workedSeconds = entity.getWorkedSeconds();
        }
        if ( entity.getExpectedSeconds() != null ) {
            expectedSeconds = entity.getExpectedSeconds();
        }
        if ( entity.getBalanceSeconds() != null ) {
            balanceSeconds = entity.getBalanceSeconds();
        }
        if ( entity.getBalanceType() != null ) {
            balanceType = entity.getBalanceType();
        }

        EmployeeDailyBalancerResponse employeeDailyBalancerResponse = new EmployeeDailyBalancerResponse( id, user, company, balanceDate, workedSeconds, expectedSeconds, balanceSeconds, balanceType );

        return employeeDailyBalancerResponse;
    }

    @Override
    public EmployeeDailyBalance fromRequest(CreateHorusEmployeeDailyBalance createHorusEmployeeDailyBalance) {
        if ( createHorusEmployeeDailyBalance == null ) {
            return null;
        }

        EmployeeDailyBalance.EmployeeDailyBalanceBuilder employeeDailyBalance = EmployeeDailyBalance.builder();

        if ( createHorusEmployeeDailyBalance.user() != null ) {
            employeeDailyBalance.user( userHorusResponseToUser( createHorusEmployeeDailyBalance.user() ) );
        }
        if ( createHorusEmployeeDailyBalance.company() != null ) {
            employeeDailyBalance.company( companyHorusResponseToCompany( createHorusEmployeeDailyBalance.company() ) );
        }
        if ( createHorusEmployeeDailyBalance.balanceDate() != null ) {
            employeeDailyBalance.balanceDate( createHorusEmployeeDailyBalance.balanceDate() );
        }
        if ( createHorusEmployeeDailyBalance.workedSeconds() != null ) {
            employeeDailyBalance.workedSeconds( createHorusEmployeeDailyBalance.workedSeconds() );
        }
        if ( createHorusEmployeeDailyBalance.expectedSeconds() != null ) {
            employeeDailyBalance.expectedSeconds( createHorusEmployeeDailyBalance.expectedSeconds() );
        }
        if ( createHorusEmployeeDailyBalance.balanceSeconds() != null ) {
            employeeDailyBalance.balanceSeconds( createHorusEmployeeDailyBalance.balanceSeconds() );
        }
        if ( createHorusEmployeeDailyBalance.balanceType() != null ) {
            employeeDailyBalance.balanceType( createHorusEmployeeDailyBalance.balanceType() );
        }
        if ( createHorusEmployeeDailyBalance.createdAt() != null ) {
            employeeDailyBalance.createdAt( createHorusEmployeeDailyBalance.createdAt() );
        }
        if ( createHorusEmployeeDailyBalance.updatedAt() != null ) {
            employeeDailyBalance.updatedAt( createHorusEmployeeDailyBalance.updatedAt() );
        }

        return employeeDailyBalance.build();
    }

    protected UserHorusResponse userToUserHorusResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        UserRole role = null;

        if ( user.getId() != null ) {
            id = user.getId();
        }
        if ( user.getName() != null ) {
            name = user.getName();
        }
        if ( user.getRole() != null ) {
            role = user.getRole();
        }

        CompanyHorusResponse cmpId = null;
        String position = null;
        CreateCompanyOccupationRequest profile = null;

        UserHorusResponse userHorusResponse = new UserHorusResponse( id, name, position, role, profile, cmpId );

        return userHorusResponse;
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

    protected Company companyHorusResponseToCompany(CompanyHorusResponse companyHorusResponse) {
        if ( companyHorusResponse == null ) {
            return null;
        }

        Company company = new Company();

        if ( companyHorusResponse.id() != null ) {
            company.setId( companyHorusResponse.id() );
        }
        if ( companyHorusResponse.cnpj() != null ) {
            company.setCnpj( companyHorusResponse.cnpj() );
        }
        if ( companyHorusResponse.email() != null ) {
            company.setEmail( companyHorusResponse.email() );
        }
        if ( companyHorusResponse.phone() != null ) {
            company.setPhone( companyHorusResponse.phone() );
        }
        if ( companyHorusResponse.status() != null ) {
            company.setStatus( companyHorusResponse.status() );
        }
        if ( companyHorusResponse.address() != null ) {
            company.setAddress( companyHorusResponse.address() );
        }

        return company;
    }

    protected User userHorusResponseToUser(UserHorusResponse userHorusResponse) {
        if ( userHorusResponse == null ) {
            return null;
        }

        User user = new User();

        if ( userHorusResponse.id() != null ) {
            user.setId( userHorusResponse.id() );
        }
        if ( userHorusResponse.name() != null ) {
            user.setName( userHorusResponse.name() );
        }
        if ( userHorusResponse.role() != null ) {
            user.setRole( userHorusResponse.role() );
        }
        if ( userHorusResponse.cmpId() != null ) {
            user.setCmpId( companyHorusResponseToCompany( userHorusResponse.cmpId() ) );
        }

        return user;
    }
}
