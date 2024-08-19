package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.req.CompanyDto;
import com.empmarket.employmentmarketplace.service.company.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyDto companyDto) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.createCompany(companyDto));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(companyService.getCompanyById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot get company by id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/companies/{pageNumber}")
    public ResponseEntity<?> getAllCompanies(@PathVariable int pageNumber) {
        return ResponseEntity.ok(companyService.getAllCompanies(pageNumber));
    }

    @GetMapping("/companies/search")
    public ResponseEntity<?> searchCompanies(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String address,
                                             @RequestParam(required = false) String createdBy,
                                             @RequestParam(required = false, defaultValue = "AND") String predicateType,
                                             @RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "6") int pageSize,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(required = false) String sortDir) {
            return ResponseEntity.ok(companyService.searchCompanies(name, address, createdBy, predicateType,
                                                                    pageNumber, pageSize, sortBy, sortDir));
    }

    @PutMapping("company/{id}")
    public ResponseEntity<?> updateCompany(@Valid @PathVariable Long id, @RequestBody CompanyDto companyDto) {
        boolean success = companyService.updateCompany(id, companyDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body(companyDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> deleteCompany(@Valid @PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
        }
    }
}
