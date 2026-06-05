package com.hiretrack.controller;

import com.hiretrack.dto.ApplicationResponseDto;
import com.hiretrack.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ApplicationController {
    private ApplicationService applicationService;


    // the jobseeker will see list of jobs using all/jobs api and click a job to apply,
    // we will pass that jobId for this application post api.
    @PostMapping("/applications/{jobId}")
    public void add(
            @Valid @PathVariable int jobId,
            Principal principal //jobseeker
    ){
        applicationService.add(jobId,principal.getName());

    }

    @GetMapping("/my-applications")
    public ApplicationResponseDto getAllApplicationsForSeeker(
            Principal principal,
            @RequestParam int page,
            @RequestParam int size
    ){
        return applicationService.getAllApplicationsForSeeker(page, size, principal.getName());
    }
}
