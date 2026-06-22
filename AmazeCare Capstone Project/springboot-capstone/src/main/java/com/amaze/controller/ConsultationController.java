package com.amaze.controller;

import com.amaze.dto.ConsultationDto;
import com.amaze.dto.ConsultationReqDto;
import com.amaze.dto.ConsultationResDto;
import com.amaze.enums.Specialty;
import com.amaze.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/consultation")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsultationController {
    private ConsultationService consultationService;

    @PostMapping("/add/{appId}")
    public void add(@PathVariable int appId,
                    @Valid @RequestBody ConsultationReqDto dto,
                    Principal principal) {
        consultationService.add(appId, dto, principal.getName());
    }


    /*to view the consultation of particular appointment by the patient
     view consultation buttons will be in row of completed appointments */
    @GetMapping("/get-one/{appId}")
    public ConsultationDto getConsultationByAppointmentIdForPatient(
            @PathVariable int appId, Principal principal) {
        return consultationService.getByAppointmentIdForPatient(appId, principal.getName());
    }


    /*for medical records -- collection of consultation
     patient needs to see their entire collection of consultation using separate button inside medical records.
    */
    @GetMapping("/all")
    public ConsultationResDto getAllConsultationsByPatient(
        @RequestParam int page,
        @RequestParam int size,
        Principal principal
    ){
        return consultationService.getAllConsultationsByPatient(page, size, principal.getName());
    }


    // doctor wants ot see the collection of consultations
    @GetMapping("/all/{appId}")
    public ConsultationResDto getAllConsultationsByDoctor(
            @PathVariable int appId,
            @RequestParam int page,
            @RequestParam int size
    ){
        return consultationService.getAllConsultationsByDoctor(page, size, appId);
    }

    @GetMapping("/all-by-status")
    public ConsultationResDto getAllConsultationsByPatient(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Specialty specialty,
            Principal principal
    ){
        return consultationService.getAllConsultationsByPatientBySpecialty(page, size, specialty,principal.getName());
    }


    // doctor wants ot see the collection of consultations
    @GetMapping("/all-by-status/{appId}")
    public ConsultationResDto getAllConsultationsByDoctor(
            @PathVariable int appId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam Specialty specialty
    ){
        return consultationService.getAllConsultationsByDoctorBySpecialty(page, size,specialty, appId);
    }


}