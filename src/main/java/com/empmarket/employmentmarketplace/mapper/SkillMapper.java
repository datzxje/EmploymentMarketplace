package com.empmarket.employmentmarketplace.mapper;

import com.empmarket.employmentmarketplace.dto.req.SkillDto;
import com.empmarket.employmentmarketplace.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SkillMapper {

    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    SkillDto toDto(Skill skill);

    Skill toEntity(SkillDto skillDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateSkillFromDto(SkillDto skillDto, @MappingTarget Skill skill);

}
