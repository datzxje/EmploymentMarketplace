package com.empmarket.employmentmarketplace.service.skill;

import com.empmarket.employmentmarketplace.dto.req.SkillDto;
import com.empmarket.employmentmarketplace.dto.res.SkillResponseDto;
import com.empmarket.employmentmarketplace.entity.Skill;

import java.util.List;

public interface SkillService {

    boolean isNameExist(String name);

    SkillDto createSkill(SkillDto skillDto);

    SkillDto getSkillById(Long id);

    SkillResponseDto getAllSkills(int pageNumber);

    List<Skill> searchSkills(String name, String createdBy, String predicateType,
                                int pageNumber, int pageSize, String sortBy, String sortDir);

    boolean updateSkill(Long id, SkillDto skillDto);

    void deleteSkill(Long skillId);

}
