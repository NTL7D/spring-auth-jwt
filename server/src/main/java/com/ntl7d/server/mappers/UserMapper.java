package com.ntl7d.server.mappers;

import org.mapstruct.Mapper;

import com.ntl7d.server.dto.requests.RegisterRequest;
import com.ntl7d.server.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toRegister(RegisterRequest request);
}
