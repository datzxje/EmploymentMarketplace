package com.empmarket.employmentmarketplace.service.company;

import com.empmarket.employmentmarketplace.dto.CompanyDto;
import com.empmarket.employmentmarketplace.dto.CompanyResponseDto;
import com.empmarket.employmentmarketplace.entity.Company;

import java.util.List;
import java.util.Map;

public interface CompanyService {
    CompanyDto createCompany(CompanyDto companyDto);

    CompanyDto getCompanyById(Long id);

    CompanyResponseDto getAllCompanies(int pageNumber);

    boolean updateCompany(Long companyId, CompanyDto companyDto);

    void deleteCompany(Long companyId);

    List<Company> searchCompanies(String name, String address, String createdBy, String predicateType,
                                  int pageNumber, int pageSize, String sortBy, String sortDir);
}
