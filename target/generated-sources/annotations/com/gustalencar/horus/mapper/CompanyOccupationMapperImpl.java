package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.CompanyOccupation;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import models.requests.CreateCompanyOccupationRequest;
import models.responses.CompanyOccupationHorusResponse;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-20T19:02:08-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CompanyOccupationMapperImpl implements CompanyOccupationMapper {

    @Override
    public CompanyOccupation fromRequest(CreateCompanyOccupationRequest request) {
        if ( request == null ) {
            return null;
        }

        CompanyOccupation.CompanyOccupationBuilder companyOccupation = CompanyOccupation.builder();

        if ( request.workload() != null ) {
            companyOccupation.workload( request.workload() );
        }
        if ( request.name() != null ) {
            companyOccupation.name( request.name() );
        }
        if ( request.description() != null ) {
            companyOccupation.description( request.description() );
        }

        return companyOccupation.build();
    }

    @Override
    public CompanyOccupationHorusResponse fromEntity(CompanyOccupation entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String workload = null;
        Boolean active = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        if ( entity.getId() != null ) {
            id = entity.getId();
        }
        if ( entity.getName() != null ) {
            name = entity.getName();
        }
        if ( entity.getDescription() != null ) {
            description = entity.getDescription();
        }
        if ( entity.getWorkload() != null ) {
            workload = entity.getWorkload();
        }
        if ( entity.getActive() != null ) {
            active = entity.getActive();
        }
        if ( entity.getCreatedAt() != null ) {
            createdAt = entity.getCreatedAt();
        }
        if ( entity.getUpdatedAt() != null ) {
            updatedAt = entity.getUpdatedAt();
        }

        Long cmpId = null;

        CompanyOccupationHorusResponse companyOccupationHorusResponse = new CompanyOccupationHorusResponse( id, cmpId, name, description, workload, active, createdAt, updatedAt );

        return companyOccupationHorusResponse;
    }
}
