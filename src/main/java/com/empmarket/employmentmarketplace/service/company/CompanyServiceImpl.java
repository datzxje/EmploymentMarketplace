package com.empmarket.employmentmarketplace.service.company;

import com.empmarket.employmentmarketplace.dto.CompanyDto;
import com.empmarket.employmentmarketplace.dto.CompanyResponseDto;
import com.empmarket.employmentmarketplace.entity.Company;
import com.empmarket.employmentmarketplace.mapper.CompanyMapper;
import com.empmarket.employmentmarketplace.repository.CompanyRepository;
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
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    public CompanyDto createCompany(CompanyDto companyDto) {
        try {
            // Map DTO to Entity
            Company company = companyMapper.toEntity(companyDto);

            // Save entity to repository
            Company savedCompany = companyRepository.save(company);

            // Map saved entity back to DTO
            return companyMapper.toDto(savedCompany);
        } catch (Exception e) {
            return null;
        }
    }

    public CompanyDto getCompanyById(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            return optionalCompany.map(companyMapper::toDto).orElse(null);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public CompanyResponseDto getAllCompanies(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Company> companyPage = companyRepository.findAll(pageable);

        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        companyResponseDto.setPageNumber(companyPage.getPageable().getPageNumber());
        companyResponseDto.setTotalPages(companyPage.getTotalPages());
        companyResponseDto.setCompanies(companyPage.stream().map(companyMapper::toDto).collect(Collectors.toList()));

        return companyResponseDto;
    }

    public List<Company> searchCompanies(String name, String address, String createdBy, String predicateType,
                                         int pageNumber, int pageSize, String sortBy, String sortDir) {
        GenericSpecification<Company> builder = new GenericSpecification<>();
        if (name != null) {
            builder.with("name", "=", name, predicateType);
        }
        if (address != null) {
            builder.with("address", "=", address, predicateType);
        }

        if (createdBy != null) {
            builder.with("createdBy", "=", createdBy, predicateType);
        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return companyRepository.findAll(builder.build(), pageable).getContent();
    }

    public boolean updateCompany(Long companyId, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isPresent()) {

            Company company = optionalCompany.get();

            companyMapper.updateCompanyFromDto(companyDto, company);

            companyRepository.save(company);

            return true;
        }
        return false;
    }

    public void deleteCompany(Long companyId) {
        Optional<Company> optionalUser = companyRepository.findById(companyId);
        if (optionalUser.isPresent()) {
            companyRepository.deleteById(companyId);
        } else {
            throw new EntityNotFoundException("Company not found");
        }
    }
}
