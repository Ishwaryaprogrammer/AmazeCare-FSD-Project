package com.amaze.mapper;

import com.amaze.dto.ReportDto;
import com.amaze.dto.ReportResDto;
import com.amaze.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportMapper {

    public ReportDto mapEntityToDto(Report report){
        return new ReportDto(
                report.getId(),
                report.getTestName(),
                report.getDate(),
                report.getFileName()
        );
    }

    public ReportResDto mapDtoToResDto(List<ReportDto> reportDtoList, Page<Report> reportPage) {
        return new ReportResDto(
                reportPage.getTotalPages(),
                reportPage.getTotalElements(),
                reportDtoList
        );
    }


}
