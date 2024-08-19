package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.req.RoleDto;
import com.empmarket.employmentmarketplace.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleDto roleDto);

    RoleDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    void updateRoleFromDto(RoleDto roleDto, @MappingTarget Role role);

}
