package com.amaze.service;

import com.amaze.model.Report;
import com.amaze.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    public ReportRepository reportRepository;
    @InjectMocks
    public ReportService reportService;

    private Report report1;

    @BeforeEach
    public void sampleData(){
        report1=new Report();
        report1.setTestName("Xray");
        report1.setFileName("xray-file");
        report1.setDate(LocalDate.parse("2024-12-15"));
        report1.setIsActive(true);

    }

    @Test
    void deleteReport_mustDeleteAndReturnNothing(){
        when(reportRepository.findById(1)).thenReturn(Optional.of(report1));
        when(reportRepository.save(any(Report.class))).thenReturn(report1);
        reportService.delete(1);
        assertThat(report1.getIsActive()).isFalse();
        verify(reportRepository, times(1)).save(report1);
        verify(reportRepository, times(1)).findById(1);
    }



}
