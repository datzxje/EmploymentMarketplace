package com.empmarket.employmentmarketplace.service.skill;

import com.empmarket.employmentmarketplace.dto.req.SkillDto;
import com.empmarket.employmentmarketplace.dto.res.SkillResponseDto;
import com.empmarket.employmentmarketplace.entity.Skill;
import com.empmarket.employmentmarketplace.mapper.SkillMapper;
import com.empmarket.employmentmarketplace.repository.SkillRepository;
import com.empmarket.employmentmarketplace.specification.GenericSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService{

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper = SkillMapper.INSTANCE;

    public boolean isNameExist(String name) {
        return skillRepository.existsByName(name);
    }

    public SkillDto createSkill(SkillDto skillDto) {
        try {
            Skill skill = skillMapper.toEntity(skillDto);

            Skill savedSkill = skillRepository.save(skill);

            return skillMapper.toDto(savedSkill);
        } catch (Exception e) {
            return null;
        }
    }

    public SkillDto getSkillById(Long id) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isPresent()) {
            return optionalSkill.map(skillMapper::toDto).orElse(null);
        } else {
            throw new EntityNotFoundException("Skill not found");
        }
    }

    public SkillResponseDto getAllSkills(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Skill> skillPage = skillRepository.findAll(pageable);

        SkillResponseDto skillResponseDto = new SkillResponseDto();
        skillResponseDto.setPageNumber(skillPage.getPageable().getPageNumber());
        skillResponseDto.setTotalPages(skillPage.getTotalPages());
        skillResponseDto.setSkills(skillPage.stream().map(skillMapper::toDto).collect(Collectors.toList()));

        return skillResponseDto;
    }

    public List<Skill> searchSkills(String name, String createdBy, String predicateType,
                                         int pageNumber, int pageSize, String sortBy, String sortDir) {
        GenericSpecification<Skill> builder = new GenericSpecification<>();
        if (name != null) {
            builder.with("name", "=", name, predicateType);
        }

        if (createdBy != null) {
            builder.with("createdBy", "=", createdBy, predicateType);
        }

        Pageable pageable;

        if (sortBy != null && sortDir != null) {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            pageable = PageRequest.of(pageNumber, pageSize, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return skillRepository.findAll(builder.build(), pageable).getContent();
    }

    public boolean updateSkill(Long id, SkillDto skillDto) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isPresent()) {

            Skill skill = optionalSkill.get();

            skillMapper.updateSkillFromDto(skillDto, skill);

            skillRepository.save(skill);

            return true;
        }
        return false;
    }

    public void deleteSkill(Long skillId) {
        Optional<Skill> optionalSkill = skillRepository.findById(skillId);
        if (optionalSkill.isPresent()) {
            Skill currentSkill = optionalSkill.get();
            currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));
            skillRepository.deleteById(skillId);
        } else {
            throw new EntityNotFoundException("Skill not found");
        }
    }
}
