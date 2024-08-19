package com.empmarket.employmentmarketplace.dto.res;

import lombok.Data;

import java.util.List;

@Data
public class AllJobsResponseDto {

    private List<JobResponseDto> jobs;

    private Integer totalPages;

    private Integer pageNumber;

}
