package com.empmarket.employmentmarketplace.service.job;

import com.empmarket.employmentmarketplace.dto.req.JobDto;
import com.empmarket.employmentmarketplace.dto.res.JobResponseDto;
import com.empmarket.employmentmarketplace.dto.res.AllJobsResponseDto;
import com.empmarket.employmentmarketplace.entity.Job;
import com.empmarket.employmentmarketplace.entity.Skill;
import com.empmarket.employmentmarketplace.enums.LevelEnum;
import com.empmarket.employmentmarketplace.mapper.JobMapper;
import com.empmarket.employmentmarketplace.repository.JobRepository;
import com.empmarket.employmentmarketplace.repository.SkillRepository;
import com.empmarket.employmentmarketplace.specification.GenericSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final SkillRepository skillRepository;

    private final JobRepository jobRepository;

    private final JobMapper jobMapper = JobMapper.INSTANCE;

    public JobResponseDto createJob(JobDto jobDto) {
        try {

            if (jobDto.getSkills() != null) {
                List<Long> reqSkills = jobDto.getSkills()
                        .stream().map(Skill::getId)
                        .toList();

                List<Skill> skills = skillRepository.findByIdIn(reqSkills);
                jobDto.setSkills(skills);
            }

            Job job = jobMapper.toEntity(jobDto);

            Job savedJob = jobRepository.save(job);

            JobResponseDto jobResponseDto = jobMapper.toJobResDto(savedJob);


            if (savedJob.getSkills() != null) {
                List<String> skills = savedJob.getSkills()
                        .stream().map(Skill::getName)
                        .toList();
                jobResponseDto.setSkills(skills);
            }

            return jobResponseDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JobResponseDto getJobById(Long id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            JobResponseDto jobResponseDto = jobMapper.toJobResDto(optionalJob.get());
            if (optionalJob.get().getSkills() != null) {
                List<String> skills = optionalJob.get().getSkills()
                        .stream().map(Skill::getName)
                        .toList();
                jobResponseDto.setSkills(skills);
            }
            return jobResponseDto;
        } else {
            throw new EntityNotFoundException("Job not found");
        }
    }

    public AllJobsResponseDto getAllJobs(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Job> jobPage = jobRepository.findAll(pageable);

        AllJobsResponseDto allJobsResponseDto = new AllJobsResponseDto();
        allJobsResponseDto.setPageNumber(jobPage.getPageable().getPageNumber());
        allJobsResponseDto.setTotalPages(jobPage.getTotalPages());
        List<JobResponseDto> jobResponseDtos = jobPage.stream().map(job -> {
            JobResponseDto jobResponseDto = jobMapper.toJobResDto(job);
            if (job.getSkills() != null) {
                List<String> skillNames = job.getSkills().stream()
                        .map(Skill::getName)
                        .collect(Collectors.toList());
                jobResponseDto.setSkills(skillNames);
            }
            return jobResponseDto;
        }).toList();

        allJobsResponseDto.setJobs(jobResponseDtos);
        return allJobsResponseDto;
    }

    public List<Job> searchJobs(String name, String location, Double salary, LevelEnum level,
                                Instant startDate, Instant endDate, String createdBy, String predicateType,
                                int pageNumber, int pageSize, String sortBy, String sortDir) {
        GenericSpecification<Job> builder = new GenericSpecification<>();
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", name);
        filters.put("location", location);
        filters.put("salary", salary);
        filters.put("level", level);
        filters.put("startDate", startDate);
        filters.put("endDate", endDate);
        filters.put("createdBy", createdBy);

        filters.forEach((field, value) -> {
            if (value != null) {
                builder.with(field, "=", value, predicateType);
            }
        });

        Pageable pageable;

        if (sortBy != null && sortDir != null) {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            pageable = PageRequest.of(pageNumber, pageSize, sort);
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return jobRepository.findAll(builder.build(), pageable).getContent();
    }

    public boolean updateJob(Long id, JobDto jobDto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {

            Job job = optionalJob.get();

            jobMapper.updateJobFromDto(jobDto, job);

            jobRepository.save(job);

            return true;
        }
        return false;
    }

    public void deleteJob(Long jobId) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isPresent()) {
            Job currentJob = optionalJob.get();
            currentJob.getSkills().forEach(skill -> skill.getJobs().remove(currentJob));
            jobRepository.deleteById(jobId);
        } else {
            throw new EntityNotFoundException("Job not found");
        }
    }
}
