package com.empmarket.employmentmarketplace.service.job;

import com.empmarket.employmentmarketplace.dto.req.JobDto;
import com.empmarket.employmentmarketplace.dto.res.JobResponseDto;
import com.empmarket.employmentmarketplace.dto.res.AllJobsResponseDto;
import com.empmarket.employmentmarketplace.entity.Job;
import com.empmarket.employmentmarketplace.enums.LevelEnum;

import java.time.Instant;
import java.util.List;

public interface JobService {

    JobResponseDto createJob(JobDto jobDto);

    JobResponseDto getJobById(Long id);

    AllJobsResponseDto getAllJobs(int pageNumber);

    List<Job> searchJobs(String name, String location, Double salary, LevelEnum level,
                         Instant startDate, Instant endDate, String createdBy, String predicateType,
                         int pageNumber, int pageSize, String sortBy, String sortDir);

    boolean updateJob(Long id, JobDto jobDto);

    void deleteJob(Long jobId);

}
