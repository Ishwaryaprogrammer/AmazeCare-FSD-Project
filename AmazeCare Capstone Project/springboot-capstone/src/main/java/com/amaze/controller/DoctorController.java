package com.amaze.controller;

import com.amaze.dto.*;
import com.amaze.enums.Specialty;
import com.amaze.service.DoctorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@AllArgsConstructor
@RequestMapping("/api/doctor")
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorController {


    private DoctorService doctorService;

    @PostMapping("/add")
    public void add(@Valid @RequestBody DoctorReqDto dto) {
        doctorService.addDoctor(dto);
    }

    @GetMapping("/view-profile")
    public DoctorResDto getByUsername(Principal principal) {
        return doctorService.getByUsername(principal.getName());
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable int id, @Valid @RequestBody DoctorUpdateReqDto dto) {
        doctorService.update(id, dto);
    }

    @GetMapping("/all")
    public DoctorListResDto all(@RequestParam int page, @RequestParam int size, @RequestParam String word) {
        return doctorService.all(page, size,word);
    }

    @GetMapping("/all-by-specialty")
    public DoctorListResDto allBySpecialty(@RequestParam int page, @RequestParam int size,@RequestParam String word, @Valid @RequestParam Specialty specialty) {
        return doctorService.allBySpecialty(page, size,word, specialty);
    }

    @GetMapping("/get-specialties")
    public SpecialtyDto getSpecialties(){
        return doctorService.getSpecialties();
    }


}
