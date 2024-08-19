package com.empmarket.employmentmarketplace.dto.res;

import com.empmarket.employmentmarketplace.dto.req.SkillDto;
import lombok.Data;

import java.util.List;

@Data
public class SkillResponseDto {

    private List<SkillDto> skills;

    private Integer totalPages;

    private Integer pageNumber;

}
