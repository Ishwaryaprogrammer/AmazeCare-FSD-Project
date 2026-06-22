package com.amaze.controller;

import com.amaze.dto.PatientReqDto;
import com.amaze.dto.PatientResDto;
import com.amaze.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:5173")
public class PatientController {

    private PatientService patientService;

    @PostMapping("/register")
    public  void register(@Valid @RequestBody PatientReqDto dto){
        patientService.add(dto);
    }


    @GetMapping("/view-profile")
    public PatientResDto viewProfile(Principal principal){
        return patientService.viewProfile(principal.getName());
    }



}
