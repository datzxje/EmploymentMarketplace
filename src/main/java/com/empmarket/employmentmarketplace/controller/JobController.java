package com.empmarket.employmentmarketplace.controller;

import com.empmarket.employmentmarketplace.dto.req.JobDto;
import com.empmarket.employmentmarketplace.enums.LevelEnum;
import com.empmarket.employmentmarketplace.service.job.JobService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class JobController {

    private final JobService jobService;

    @PostMapping("/jobs")
    public ResponseEntity<?> createJob(@RequestBody JobDto jobDto) {
        System.out.println("isActive in DTO: " + jobDto.isActive());
        return ResponseEntity.ok(jobService.createJob(jobDto));
    }

    @GetMapping("/job/{id}")
    public ResponseEntity<?> getJobById(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(jobService.getJobById(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot get job by id",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/jobs/{pageNumber}")
    public ResponseEntity<?> getAllJobs(@PathVariable int pageNumber) {
        return ResponseEntity.ok(jobService.getAllJobs(pageNumber));
    }

    @GetMapping("/jobs/search")
    public ResponseEntity<?> searchJobs(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String location,
                                        @RequestParam(required = false) Double salary,
                                        @RequestParam(required = false) LevelEnum level,
                                        @RequestParam(required = false) Instant startDate,
                                        @RequestParam(required = false) Instant endDate,
                                        @RequestParam(required = false) String createdBy,
                                        @RequestParam(required = false, defaultValue = "AND") String predicateType,
                                        @RequestParam(defaultValue = "0") int pageNumber,
                                        @RequestParam(defaultValue = "6") int pageSize,
                                        @RequestParam(required = false) String sortBy,
                                        @RequestParam(required = false) String sortDir) {

        return ResponseEntity.ok(jobService.searchJobs(name, location, salary, level,
                startDate, endDate, createdBy, predicateType,
                pageNumber, pageSize, sortBy, sortDir));
    }

    @PutMapping("job/{id}")
    public ResponseEntity<?> updateJob(@Valid @PathVariable Long id, @RequestBody JobDto jobDto) {
        boolean success = jobService.updateJob(id, jobDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body(jobDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<?> deleteJob(@Valid @PathVariable Long id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job not found",HttpStatus.NOT_FOUND);
        }
    }
    
}
