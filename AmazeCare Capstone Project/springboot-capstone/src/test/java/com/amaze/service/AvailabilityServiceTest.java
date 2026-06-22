package com.amaze.service;


import com.amaze.dto.AvailabilityDto;
import com.amaze.dto.AvailabilityReqDto;
import com.amaze.dto.AvailabilityResDto;
import com.amaze.enums.Day;
import com.amaze.enums.Role;
import com.amaze.enums.Specialty;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.model.Availability;
import com.amaze.model.Doctor;
import com.amaze.model.User;
import com.amaze.repository.AvailabilityRepository;
import com.amaze.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceTest {

    @Mock
    private AvailabilityRepository availabilityRepository;
    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private  AvailabilityService availabilityService;



    private Availability availability1;
    private Doctor doctor1;
    private User user1;

    @BeforeEach
    public void sampleData(){
        availability1= new Availability();
        availability1.setDay(Day.MONDAY);
        availability1.setStartTime(LocalTime.parse("11:00"));
        availability1.setEndTime(LocalTime.parse("12:00"));
        availability1.setDuration(10);

        doctor1= new Doctor();
        doctor1.setId(1);
        doctor1.setFullName("Ishwarya");
        doctor1.setSpecialty(Specialty.CARDIOLOGIST);
        doctor1.setExperience(2);
        doctor1.setQualification("MBBS");
        doctor1.setDesignation("Intern");
        doctor1.setUser(user1);

        user1=new User();
        user1.setId(100);
        user1.setName("ishu");
        user1.setEmail("i@gmail.com");
        user1.setPassword("ishu@123");
        user1.setRole(Role.DOCTOR);
    }

    @Test
    void addAvailability_mustSaveAndReturnAvailability(){
        when(availabilityRepository.save(any(Availability.class))).thenReturn(availability1);
        when(doctorRepository.findByUserName("ishu")).thenReturn(doctor1);
        AvailabilityReqDto dto = new AvailabilityReqDto(Day.MONDAY, LocalTime.parse("11:00"),LocalTime.parse("12:00"),10);
        Availability actualAvailability =  availabilityService.add(dto,"ishu");
        assertThat(actualAvailability.getDay()).isEqualTo(availability1.getDay());
        assertThat(actualAvailability.getStartTime()).isEqualTo(availability1.getStartTime());
        assertThat(actualAvailability.getEndTime()).isEqualTo(availability1.getEndTime());
        assertThat(actualAvailability.getDuration()).isEqualTo(availability1.getDuration());
        verify(availabilityRepository, times(1)).save(any(Availability.class));
    }

    @Test
    void deleteAvailability_mustDeleteAndReturnNothing(){
        when(availabilityRepository.findById(1)).thenReturn(Optional.of(availability1));
        doNothing().when(availabilityRepository).deleteById(1);
        availabilityService.delete(1);
        verify(availabilityRepository, times(1)).deleteById(1);
        verify(availabilityRepository, times(1)).findById(1);
    }


    @Test
    void getAllAvailabilities_MustReturnSomething(){  // for viewAll method

        Pageable pageable= PageRequest.of(0,5);
        Page<Availability> availabilityPage =
                new PageImpl<>(List.of(availability1));
        AvailabilityResDto expectedDto = new AvailabilityResDto(
                1,
                1, List.of(new AvailabilityDto(
                                        availability1.getId(),
                                        availability1.getDay(),
                                        availability1.getStartTime(),
                                        availability1.getEndTime(),
                                        availability1.getDuration()
                                )
                        )
                );
        when(availabilityRepository.findAllByDoctorUserName("ishu",pageable)).thenReturn(availabilityPage);
        AvailabilityResDto actualCall = availabilityService.viewAll(0,5,"ishu");
        assertThat(actualCall).isEqualTo(expectedDto);
        assertThat(actualCall.list()).hasSize(1);
        assertThat(actualCall.list().getFirst().day()).isEqualTo(availability1.getDay());
        assertThat(actualCall.list().getFirst().startTime()).isEqualTo(availability1.getStartTime());
        assertThat(actualCall.list().getFirst().endTime()).isEqualTo(availability1.getEndTime());
        assertThat(actualCall.list().getFirst().duration()).isEqualTo(availability1.getDuration());
    }

    @Test
    void getAllAvailabilities_ReturnsEmptyList(){
        Pageable pageable= PageRequest.of(0,5);
        Page<Availability> availabilityPage = new PageImpl<>(List.of());
        when(availabilityRepository.findAllByDoctorUserName("ishu",pageable)).thenReturn(availabilityPage);
        AvailabilityResDto expectedDto = new AvailabilityResDto(
                1,
                0, List.of());
        AvailabilityResDto actualCall = availabilityService.viewAll(0,5,"ishu");
        assertThat(actualCall).isEqualTo(expectedDto);
        assertThat(actualCall.list()).hasSize(0);
        assertThat(actualCall.list()).isEmpty();
    }



    @Test
    void getById_availabilityExists(){
        when(availabilityRepository.findById(1)).thenReturn(Optional.of(availability1));
        Availability actualCall=availabilityService.getById(1);
        assertThat(actualCall.getDay()).isEqualTo(availability1.getDay());
        assertThat(actualCall.getStartTime()).isEqualTo(availability1.getStartTime());
        assertThat(actualCall.getEndTime()).isEqualTo(availability1.getEndTime());
        assertThat(actualCall.getDuration()).isEqualTo(availability1.getDuration());

    }

    @Test
    void getById_availabilityDoesNotExist(){
        when(availabilityRepository.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> availabilityService.getById(1))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Availability Id not found");
        verify(availabilityRepository, times(1)).findById(1);
    }



}
