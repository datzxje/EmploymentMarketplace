package com.empmarket.employmentmarketplace.service.resume;

import com.empmarket.employmentmarketplace.dto.req.ResumeDto;
import com.empmarket.employmentmarketplace.dto.res.CreateResumeResponseDto;
import com.empmarket.employmentmarketplace.dto.res.AllResumesResponseDto;
import com.empmarket.employmentmarketplace.dto.res.ResumeResponseDto;
import com.empmarket.employmentmarketplace.dto.res.UpdateResumeResponseDto;
import com.empmarket.employmentmarketplace.entity.Job;
import com.empmarket.employmentmarketplace.entity.Resume;
import com.empmarket.employmentmarketplace.entity.User;
import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;
import com.empmarket.employmentmarketplace.mapper.ResumeMapper;
import com.empmarket.employmentmarketplace.repository.JobRepository;
import com.empmarket.employmentmarketplace.repository.ResumeRepository;
import com.empmarket.employmentmarketplace.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    private final UserRepository userRepository;

    private final JobRepository jobRepository;

    private final ResumeMapper resumeMapper = ResumeMapper.INSTANCE;

    public CreateResumeResponseDto createResume(ResumeDto resumeDto) {
        Resume resume = resumeMapper.toEntity(resumeDto);

        if (!checkResumeByUserAndJob(resume)) {
            throw new IllegalArgumentException("Invalid User or Job ID provided.");
        }

        Resume currentResume = resumeRepository.save(resume);
        CreateResumeResponseDto res = new CreateResumeResponseDto();
        res.setId(currentResume.getId());
        res.setCreatedAt(currentResume.getCreatedAt());
        res.setCreatedBy(currentResume.getCreatedBy());

        return res;
    }

    public ResumeResponseDto getResumeById(Long id) {
        Optional<Resume> optionalResume = resumeRepository.findById(id);
        if (optionalResume.isPresent()) {
            ResumeResponseDto res = new ResumeResponseDto();
            res.setId(optionalResume.get().getId());
            res.setEmail(optionalResume.get().getEmail());
            res.setUrl(optionalResume.get().getUrl());
            res.setState(optionalResume.get().getState());
            res.setCreatedAt(optionalResume.get().getCreatedAt());
            res.setCreatedBy(optionalResume.get().getCreatedBy());
            res.setUpdatedAt(optionalResume.get().getUpdatedAt());
            res.setUpdatedBy(optionalResume.get().getUpdatedBy());

            res.setUser(new ResumeResponseDto.UserResume(optionalResume.get().getUser().getId(),
                                                         optionalResume.get().getUser().getName()));
            res.setJob(new ResumeResponseDto.JobResume(optionalResume.get().getJob().getId(),
                                                       optionalResume.get().getJob().getName()));
            System.out.println(res);
            return res;
        } else {
            throw new EntityNotFoundException("Resume not found");
        }
    }

    public AllResumesResponseDto getAllResumes(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Resume> resumePage = resumeRepository.findAll(pageable);

        List<ResumeResponseDto> resumeResponseDtos = resumePage.getContent().stream()
                .map(resume -> {
                    ResumeResponseDto resDto = getResumeById(resume.getId());
                    return resDto != null ? resDto : new ResumeResponseDto();
                })
                .toList();

        AllResumesResponseDto allResumesResponseDto = new AllResumesResponseDto();
        allResumesResponseDto.setResumes(resumeResponseDtos);
        allResumesResponseDto.setPageNumber(resumePage.getPageable().getPageNumber());
        allResumesResponseDto.setTotalPages(resumePage.getTotalPages());

        return allResumesResponseDto;
    }

    public List<Resume> searchResumes(String email, ResumeStateEnum state, String predicateType,
                                      int pageNumber, int pageSize, String sortBy, String sortDir) {
        GenericSpecification<Resume> builder = new GenericSpecification<>();
        if (email != null) {
            builder.with("email", "=", email, predicateType);
        }
        if (state != null) {
            builder.with("state", "=", state, predicateType);
        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return resumeRepository.findAll(builder.build(), pageable).getContent();
    }

    public UpdateResumeResponseDto updateResume(Long resumeId, ResumeDto resumeDto) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            Resume resume = optionalResume.get();

            // Update only the state
            if (resumeDto.getState() != null) {
                resume.setState(resumeDto.getState());
            }

            // Save the updated resume
            Resume updatedResume = resumeRepository.save(resume);

            // Prepare and return response DTO
            UpdateResumeResponseDto res = new UpdateResumeResponseDto();
            res.setState(updatedResume.getState());
            res.setUpdatedAt(updatedResume.getUpdatedAt());
            res.setUpdatedBy(updatedResume.getUpdatedBy());

            return res;
        }
        throw new EntityNotFoundException("Resume not found");
    }

    public void deleteResume(Long resumeId) {
        Optional<Resume> optionalResume = resumeRepository.findById(resumeId);
        if (optionalResume.isPresent()) {
            resumeRepository.deleteById(resumeId);
        } else {
            throw new EntityNotFoundException("Resume not found");
        }
    }

    public boolean checkResumeByUserAndJob(Resume resume) {

        Optional<User> userOptional = Optional.ofNullable(resume.getUser())
                .flatMap(user -> userRepository.findById(user.getId()));

        Optional<Job> jobOptional = Optional.ofNullable(resume.getJob())
                .flatMap(job -> jobRepository.findById(job.getId()));

        return userOptional.isPresent() && jobOptional.isPresent();
    }

}
