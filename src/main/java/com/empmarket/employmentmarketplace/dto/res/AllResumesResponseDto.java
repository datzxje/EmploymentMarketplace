package com.empmarket.employmentmarketplace.dto.res;

import lombok.Data;

import java.util.List;

@Data
public class AllResumesResponseDto {

    private List<ResumeResponseDto> resumes;

    private Integer totalPages;

    private Integer pageNumber;

}
