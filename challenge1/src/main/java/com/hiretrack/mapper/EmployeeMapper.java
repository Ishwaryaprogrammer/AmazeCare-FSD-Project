package com.hiretrack.mapper;

import com.hiretrack.dto.EmployerRegisterReqDto;
import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.enums.Role;
import com.hiretrack.model.Employer;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public RegisterReqDto mapDtoToUserDto(@Valid EmployerRegisterReqDto dto) {
        return new RegisterReqDto(
                dto.username(),
                dto.password(),
                Role.EMPLOYER
        );
    }

    public Employer mappDtoToEmployer(@Valid EmployerRegisterReqDto dto) {
        Employer employer=new Employer();
        employer.setCompanyName(dto.companyName());
        return employer;
    }
}
