package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.req.PermissionDto;
import com.empmarket.employmentmarketplace.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissionMapper {

    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    Permission toEntity(PermissionDto permissionDto);

    PermissionDto toDto(Permission permission);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updatePermissionFromDto(PermissionDto permissionDto, @MappingTarget Permission permission);

}
