package com.amaze.service;

import com.amaze.dto.StatDto;
import com.amaze.enums.Status;
import com.amaze.model.*;
import com.amaze.repository.AppointmentRepository;
import com.amaze.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment3;
    private Report report1;


    @BeforeEach
    public void sampleData(){
        appointment1 =new Appointment();
        appointment1.setSymptoms("fever");
        appointment1.setReason("general");
        appointment1.setStatus(Status.PENDING);
        appointment1.setStartTime(LocalTime.parse("11:00"));
        appointment1.setEndTime(LocalTime.parse("11:20"));
        appointment1.setDate(LocalDate.parse("2025-12-15"));

        appointment2 =new Appointment();
        appointment2.setSymptoms("head ache");
        appointment2.setReason("general");
        appointment2.setStatus(Status.CONFIRMED);
        appointment2.setStartTime(LocalTime.parse("11:00"));
        appointment2.setEndTime(LocalTime.parse("11:10"));
        appointment2.setDate(LocalDate.parse("2025-12-16"));

        appointment3 =new Appointment();
        appointment3.setSymptoms("heavy fever");
        appointment3.setReason("serious");
        appointment3.setStatus(Status.COMPLETED);
        appointment3.setStartTime(LocalTime.parse("10:00"));
        appointment3.setEndTime(LocalTime.parse("10:30"));
        appointment3.setDate(LocalDate.parse("2025-12-06"));

        report1=new Report();
        report1.setTestName("testname");
        report1.setFileName("filename");
        report1.setDate(LocalDate.parse("2025-03-04"));

    }


    @Test
    void getDoctorStats_MustReturnSomething(){
        List<Status> statuses=new ArrayList<>();
        statuses.add(Status.CANCELLED);
        statuses.add(Status.COMPLETED);
        when(appointmentRepository.getCountAllUpcomingAppointmentsForDoctor("ishu",statuses)).thenReturn(Stream.of(appointment1,appointment2).count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForDoctor(Status.COMPLETED,"ishu")).thenReturn(Stream.of(appointment3).count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForDoctor(Status.PENDING,"ishu")).thenReturn(Stream.of(appointment1).count());
        StatDto actualCall = appointmentService.getDoctorStats("ishu");
        List<String> actualLabel=actualCall.label();
        assertThat(actualLabel).isEqualTo(List.of("Upcoming Appointments","Completed Appointments","Pending Appointments"));
        assertThat(actualCall.count().getFirst()).isEqualTo(2);
        assertThat(actualCall.count().get(1)).isEqualTo(1);
        assertThat(actualCall.count().get(2)).isEqualTo(1);
    }

    @Test
    void getDoctorStats_ReturnCountsZero(){
        List<Status> statuses=new ArrayList<>();
        statuses.add(Status.CANCELLED);
        statuses.add(Status.COMPLETED);
        when(appointmentRepository.getCountAllUpcomingAppointmentsForDoctor("nivi",statuses)).thenReturn(Stream.of().count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForDoctor(Status.COMPLETED,"nivi")).thenReturn(Stream.of().count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForDoctor(Status.PENDING,"nivi")).thenReturn(Stream.of().count());
        StatDto actualCall = appointmentService.getDoctorStats("nivi");
        List<String> actualLabel=actualCall.label();
        assertThat(actualLabel).isEqualTo(List.of("Upcoming Appointments","Completed Appointments","Pending Appointments"));
        assertThat(actualCall.count().getFirst()).isEqualTo(0);
        assertThat(actualCall.count().get(1)).isEqualTo(0);
        assertThat(actualCall.count().get(2)).isEqualTo(0);

    }

    @Test
    void getPatientStats_MustReturnSomething(){
        when(reportRepository.getCountFindAllByPatientUserNameAndIsActive("ishu")).thenReturn(Stream.of(report1).count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.CONFIRMED,"ishu")).thenReturn(Stream.of(appointment2).count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.COMPLETED,"ishu")).thenReturn(Stream.of(appointment3).count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.PENDING,"ishu")).thenReturn(Stream.of(appointment1).count());
        StatDto actualCall = appointmentService.getPatientStats("ishu");
        List<String> actualLabel=actualCall.label();
        assertThat(actualLabel).isEqualTo(List.of("Medical Reports","Upcoming Appointments","Completed Appointments","Pending Appointments"));
        assertThat(actualCall.count().getFirst()).isEqualTo(1);
        assertThat(actualCall.count().get(1)).isEqualTo(1);
        assertThat(actualCall.count().get(2)).isEqualTo(1);
        assertThat(actualCall.count().get(2)).isEqualTo(1);
    }

    @Test
    void getPatientStats_ReturnCountsZero(){
        when(reportRepository.getCountFindAllByPatientUserNameAndIsActive("ishu")).thenReturn(Stream.of().count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.CONFIRMED,"ishu")).thenReturn(Stream.of().count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.COMPLETED,"ishu")).thenReturn(Stream.of().count());
        when(appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.PENDING,"ishu")).thenReturn(Stream.of().count());
        StatDto actualCall = appointmentService.getPatientStats("ishu");
        List<String> actualLabel=actualCall.label();
        assertThat(actualLabel).isEqualTo(List.of("Medical Reports","Upcoming Appointments","Completed Appointments","Pending Appointments"));
        assertThat(actualCall.count().getFirst()).isEqualTo(0);
        assertThat(actualCall.count().get(1)).isEqualTo(0);
        assertThat(actualCall.count().get(2)).isEqualTo(0);
        assertThat(actualCall.count().get(2)).isEqualTo(0);
    }




}
