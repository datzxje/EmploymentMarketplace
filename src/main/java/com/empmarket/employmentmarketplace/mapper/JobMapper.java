package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.req.JobDto;
import com.empmarket.employmentmarketplace.dto.res.JobResponseDto;
import com.empmarket.employmentmarketplace.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    Job toEntity(JobDto jobDto);

    @Mapping(target = "skills", ignore = true)
    JobResponseDto toJobResDto(Job job);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateJobFromDto(JobDto jobDto, @MappingTarget Job job);

}
