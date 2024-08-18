package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.SignupDto;
import com.empmarket.employmentmarketplace.dto.UserDto;
import com.empmarket.employmentmarketplace.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    User toEntity(SignupDto signupDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);

}
