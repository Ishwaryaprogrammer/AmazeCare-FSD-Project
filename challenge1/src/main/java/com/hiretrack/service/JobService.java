package com.hiretrack.service;


import com.hiretrack.dto.CreateJobRequest;
import com.hiretrack.dto.JobResponse;
import com.hiretrack.dto.JobResponseDto;
import com.hiretrack.exception.ResourceNotFoundException;
import com.hiretrack.mapper.JobMapper;
import com.hiretrack.model.Employer;
import com.hiretrack.model.Job;
import com.hiretrack.repository.JobRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService {

    private JobMapper jobMapper;
    private EmployerService employerService;
    private JobRepository jobRepository;

    public void add(@Valid CreateJobRequest dto, String name) {
        Job job=jobMapper.mapDtoToEntity(dto);
        Employer employer=employerService.getByName(name);
        if(employer==null){
            throw new ResourceNotFoundException("Employer not found");
        }
        job.setEmployer(employer);
        jobRepository.save(job);

    }

    public JobResponseDto all(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Job> jobPage=jobRepository.findAll(pageable);
        List<JobResponse> jobResponseList=jobPage.stream().map(jobMapper::mapEntityToDto).toList();
        return jobMapper.mapDtoListToDto(jobResponseList,jobPage);

    }

    public Job getById(int jobId) {
        return jobRepository.findById(jobId).orElseThrow(
                ()-> new ResourceNotFoundException(" Job Id not found")
        );
    }
}
