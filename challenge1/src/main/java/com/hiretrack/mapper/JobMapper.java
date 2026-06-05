package com.hiretrack.mapper;


import com.hiretrack.dto.CreateJobRequest;
import com.hiretrack.dto.JobResponse;
import com.hiretrack.dto.JobResponseDto;
import com.hiretrack.model.Job;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobMapper {
    public Job mapDtoToEntity(@Valid CreateJobRequest dto) {
        Job job=new Job();
        job.setTitle(dto.title());
        job.setDescription(dto.description());
        job.setLocation(dto.location());
        job.setSalary(dto.salary());
        return job;
    }

    public JobResponse mapEntityToDto(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getLocation(),
                job.getSalary(),
                job.getEmployer().getCompanyName()
        );

    }

    public JobResponseDto mapDtoListToDto(List<JobResponse> jobResponseList, Page<Job> jobPage) {
        return new JobResponseDto(jobPage.getTotalPages(),
        jobPage.getTotalElements(),
        jobResponseList);
    }
}
