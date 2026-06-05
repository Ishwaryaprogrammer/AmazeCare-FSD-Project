package com.hiretrack.service;

import com.hiretrack.dto.ApplicationResponse;
import com.hiretrack.dto.ApplicationResponseDto;
import com.hiretrack.mapper.ApplicationMapper;
import com.hiretrack.model.Application;
import com.hiretrack.repository.ApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {
    private ApplicationRepository applicationRepository;
    private SeekerService seekerService;
    private JobService jobService;
    private ApplicationMapper applicationMapper;


    public void add(int jobId, String name) {
        Application application=new Application();
        application.setJob(jobService.getById(jobId));
        application.setJobSeeker(seekerService.getByUsername(name));
        applicationRepository.save(application);
    }

    public ApplicationResponseDto getAllApplicationsForSeeker(int page, int size, String name) {

        Pageable pageable=PageRequest.of(page,size);
        Page<Application> applicationPage=applicationRepository.findAllByJobSeekerUserUsername(name,pageable);
        List<ApplicationResponse> applicationResponseList=applicationPage.stream().map(applicationMapper::mapEntityToDto).toList();
        return applicationMapper.mapListDtoToDto(applicationResponseList,applicationPage);

    }
}
