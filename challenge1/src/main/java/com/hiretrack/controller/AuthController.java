package com.hiretrack.controller;

import com.hiretrack.dto.EmployerRegisterReqDto;
import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.dto.SeekerRegisterReqDto;
import com.hiretrack.dto.TokenDto;
import com.hiretrack.service.EmployerService;
import com.hiretrack.service.SeekerService;
import com.hiretrack.service.UserService;
import com.hiretrack.utility.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private JwtUtility jwtUtility;
    private EmployerService employerService;
    private SeekerService seekerService;

    // if we use this api, then the role specific details has to be inserted manually.
    @PostMapping("/register")
    public void addUser(@Valid @RequestBody RegisterReqDto dto) {

        userService.addUser(dto);
    }

    // in frontend if the role is selected as employer then the additional text fields will be opened
    // and the person has to fill those extra details during the registration itself
    // based on the role they selected

    @PostMapping("/register/employer")
    public void addEmployer(@Valid @RequestBody EmployerRegisterReqDto dto) {

        employerService.add(dto);
    }

    @PostMapping("/register/seeker")
    public void addSeeker(@Valid @RequestBody SeekerRegisterReqDto dto) {

        seekerService.add(dto);
    }





    @PostMapping("/login")
    public TokenDto login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username,token);
    }






}
