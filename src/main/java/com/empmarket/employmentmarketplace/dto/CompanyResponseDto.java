package com.empmarket.employmentmarketplace.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyResponseDto {

    private List<CompanyDto> companies;

    private Integer totalPages;

    private Integer pageNumber;

}
