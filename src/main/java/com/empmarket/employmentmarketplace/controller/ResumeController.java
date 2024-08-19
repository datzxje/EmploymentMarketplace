package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.req.ResumeDto;
import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;
import com.empmarket.employmentmarketplace.service.resume.ResumeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/resumes")
    public ResponseEntity<?> createResume(@RequestBody ResumeDto resumeDto) {
        return ResponseEntity.status(HttpStatus.OK).body(resumeService.createResume(resumeDto));
    }

    @GetMapping("/resume/{id}")
    public ResponseEntity<?> getResumeById(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(resumeService.getResumeById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Resume not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot get resume by id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/resumes/{pageNumber}")
    public ResponseEntity<?> getAllCompanies(@PathVariable int pageNumber) {
        return ResponseEntity.ok(resumeService.getAllResumes(pageNumber));
    }

    @GetMapping("/resumes/search")
    public ResponseEntity<?> searchCompanies(@RequestParam(required = false) String email,
                                             @RequestParam(required = false) ResumeStateEnum state,
                                             @RequestParam(required = false, defaultValue = "AND") String predicateType,
                                             @RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "6") int pageSize,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(required = false) String sortDir) {
        return ResponseEntity.ok(resumeService.searchResumes(email, state, predicateType,
                pageNumber, pageSize, sortBy, sortDir));
    }

    @PatchMapping("resume/{id}")
    public ResponseEntity<?> updateResume(@Valid @PathVariable Long id, @RequestBody ResumeDto resumeDto) {
            return ResponseEntity.status(HttpStatus.OK).body(resumeService.updateResume(id, resumeDto));
    }

    @DeleteMapping("/resume/{id}")
    public ResponseEntity<?> deleteResume(@Valid @PathVariable Long id) {
        try {
            resumeService.deleteResume(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Resume not found",HttpStatus.NOT_FOUND);
        }
    }
}
