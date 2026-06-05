package com.hiretrack.controller;

import com.hiretrack.dto.CreateJobRequest;
import com.hiretrack.dto.JobResponseDto;
import com.hiretrack.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/jobs")
public class JobController {

    private JobService jobService;

    @PostMapping // the job will be created by the employer so we have to map that employee to this job
    public void createJob(
            @Valid @RequestBody CreateJobRequest dto,
            Principal principal
    ){
        jobService.add(dto,principal.getName());

    }

    @GetMapping // both employer and seeker can see all jobs
    public JobResponseDto all(
            @Valid @RequestParam int page,
            @Valid @RequestParam int size
    ){
        return jobService.all(page, size);
    }

}
