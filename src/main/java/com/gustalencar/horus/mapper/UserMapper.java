package com.gustalencar.horus.mapper;

import com.gustalencar.horus.entity.User;
import models.requests.CreateUserHorusRequest;
import models.responses.UserHorusResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface UserMapper {
    UserHorusResponse fromEntity(final User entity);

    @Mapping(target = "id", ignore = true)
    User fromRequest(CreateUserHorusRequest createUserHorusRequest);
}
