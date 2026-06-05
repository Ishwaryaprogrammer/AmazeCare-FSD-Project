package com.hiretrack.mapper;

import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.dto.SeekerRegisterReqDto;
import com.hiretrack.enums.Role;
import com.hiretrack.model.JobSeeker;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class SeekerMapper {
    public RegisterReqDto mapDtoToUserDto(@Valid SeekerRegisterReqDto dto) {
        return new RegisterReqDto(
                dto.username(),
                dto.password(), 
                Role.SEEKER
        );
    }

    public JobSeeker mapDtoToEmployer(@Valid SeekerRegisterReqDto dto) {
        JobSeeker jobSeeker=new JobSeeker();
        jobSeeker.setName(dto.name());
        jobSeeker.setResumeSummary(dto.resumeSummary());
        return jobSeeker;
    }
}
