package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.Company;
import javax.annotation.processing.Generated;
import models.requests.CreateCompanyHorusRequest;
import models.responses.CompanyHorusResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-20T19:02:08-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public Company fromRequest(CreateCompanyHorusRequest createFirmHorusRequest) {
        if ( createFirmHorusRequest == null ) {
            return null;
        }

        Company company = new Company();

        if ( createFirmHorusRequest.cnpj() != null ) {
            company.setCnpj( createFirmHorusRequest.cnpj() );
        }
        if ( createFirmHorusRequest.email() != null ) {
            company.setEmail( createFirmHorusRequest.email() );
        }
        if ( createFirmHorusRequest.phone() != null ) {
            company.setPhone( createFirmHorusRequest.phone() );
        }
        if ( createFirmHorusRequest.status() != null ) {
            company.setStatus( createFirmHorusRequest.status() );
        }
        if ( createFirmHorusRequest.address() != null ) {
            company.setAddress( createFirmHorusRequest.address() );
        }
        if ( createFirmHorusRequest.createdAt() != null ) {
            company.setCreatedAt( createFirmHorusRequest.createdAt() );
        }

        return company;
    }

    @Override
    public CompanyHorusResponse fromEntity(Company entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String cnpj = null;
        String email = null;
        String status = null;
        String phone = null;
        String address = null;

        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getCnpj() != null ) {
            cnpj = entity.getCnpj();
        }
        if ( entity.getEmail() != null ) {
            email = entity.getEmail();
        }
        if ( entity.getStatus() != null ) {
            status = entity.getStatus();
        }
        if ( entity.getPhone() != null ) {
            phone = entity.getPhone();
        }
        if ( entity.getAddress() != null ) {
            address = entity.getAddress();
        }

        String companyName = null;

        CompanyHorusResponse companyHorusResponse = new CompanyHorusResponse( id, companyName, cnpj, email, status, phone, address );

        return companyHorusResponse;
    }
}
