package com.hiretrack.service;

import com.hiretrack.dto.EmployerRegisterReqDto;
import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.mapper.EmployeeMapper;
import com.hiretrack.model.Employer;
import com.hiretrack.model.User;
import com.hiretrack.repository.EmployerRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployerService {
    private EmployerRepository employerRepository;
    private EmployeeMapper employeeMapper;
    private UserService userService;



    public Employer getByName(String name) {
        return employerRepository.findByName(name);
    }

    public void add(@Valid EmployerRegisterReqDto dto) {
        RegisterReqDto registerReqDto= employeeMapper.mapDtoToUserDto(dto);
        Employer employer=employeeMapper.mappDtoToEmployer(dto);
        userService.addUser(registerReqDto);
        User user=userService.getByName(registerReqDto.username());
        employer.setUser(user);
        employerRepository.save(employer);

    }
}
