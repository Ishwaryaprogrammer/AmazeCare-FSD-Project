package com.hiretrack.service;

import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.dto.SeekerRegisterReqDto;
import com.hiretrack.mapper.SeekerMapper;
import com.hiretrack.model.Employer;
import com.hiretrack.model.JobSeeker;
import com.hiretrack.model.User;
import com.hiretrack.repository.SeekerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SeekerService {
    private UserService userService;
    private SeekerMapper seekerMapper;
    private SeekerRepository seekerRepository;

    public void add(@Valid SeekerRegisterReqDto dto) {
        RegisterReqDto registerReqDto= seekerMapper.mapDtoToUserDto(dto);
        JobSeeker jobSeeker=seekerMapper.mapDtoToEmployer(dto);
        userService.addUser(registerReqDto);
        User user=userService.getByName(registerReqDto.username());
        jobSeeker.setUser(user);
        seekerRepository.save(jobSeeker);
    }

    public JobSeeker getByUsername(String name) {
        return seekerRepository.findByUserUsername(name);
    }
}
