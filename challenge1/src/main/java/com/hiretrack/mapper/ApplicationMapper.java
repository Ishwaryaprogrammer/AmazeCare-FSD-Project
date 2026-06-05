package com.hiretrack.mapper;

import com.hiretrack.dto.ApplicationResponse;
import com.hiretrack.dto.ApplicationResponseDto;
import com.hiretrack.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationMapper {
    public ApplicationResponse mapEntityToDto(Application application) {
        return new ApplicationResponse(
                application.getId(),
                application.getAppliedAt(),
                application.getJob().getTitle(),
                application.getJob().getEmployer().getCompanyName()
        );
    }

    public ApplicationResponseDto mapListDtoToDto(List<ApplicationResponse> applicationResponseList, Page<Application> applicationPage) {
        return new ApplicationResponseDto(
                applicationPage.getTotalPages(),
                applicationPage.getTotalElements(),
                applicationResponseList
        );
    }
}

