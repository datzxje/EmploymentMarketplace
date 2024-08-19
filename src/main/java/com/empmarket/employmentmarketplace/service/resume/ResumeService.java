package com.empmarket.employmentmarketplace.service.resume;

import com.empmarket.employmentmarketplace.dto.req.ResumeDto;
import com.empmarket.employmentmarketplace.dto.res.CreateResumeResponseDto;
import com.empmarket.employmentmarketplace.dto.res.AllResumesResponseDto;
import com.empmarket.employmentmarketplace.dto.res.ResumeResponseDto;
import com.empmarket.employmentmarketplace.dto.res.UpdateResumeResponseDto;
import com.empmarket.employmentmarketplace.entity.Resume;
import com.empmarket.employmentmarketplace.enums.ResumeStateEnum;

import java.util.List;

public interface ResumeService {

    CreateResumeResponseDto createResume(ResumeDto resumeDto);

    ResumeResponseDto getResumeById(Long id);

    AllResumesResponseDto getAllResumes(int pageNumber);

    List<Resume> searchResumes(String email, ResumeStateEnum state, String predicateType,
                               int pageNumber, int pageSize, String sortBy, String sortDir);

    UpdateResumeResponseDto updateResume(Long resumeId, ResumeDto resumeDto);

    void deleteResume(Long resumeId);
}
