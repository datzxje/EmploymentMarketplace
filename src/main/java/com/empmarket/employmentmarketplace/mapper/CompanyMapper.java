package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.CompanyDto;
import com.empmarket.employmentmarketplace.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateCompanyFromDto(CompanyDto companyDto, @MappingTarget Company company);
}
