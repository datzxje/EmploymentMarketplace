package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.req.ResumeDto;
import com.empmarket.employmentmarketplace.entity.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResumeMapper {

    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);

    Resume toEntity(ResumeDto resumeDto);

    ResumeDto toDto(Resume resume);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateResumeFromDto(ResumeDto resumeDto, @MappingTarget Resume resume);

}
