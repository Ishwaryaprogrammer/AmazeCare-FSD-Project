package com.amaze.service;


import com.amaze.dto.DoctorReqDto;
import com.amaze.enums.Role;
import com.amaze.enums.Specialty;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.model.Doctor;
import com.amaze.model.User;
import com.amaze.repository.DoctorRepository;
import com.amaze.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    private DoctorService doctorService;
    @Mock
    private UserRepository userRepository;

    private Doctor doctor1;
    private User user1;

    @BeforeEach
    public void sampleData(){
        user1=new User();
        user1.setId(100);
        user1.setName("ishu");
        user1.setEmail("i@gmail.com");
        user1.setPassword("ishu@123");
        user1.setRole(Role.DOCTOR);

        doctor1= new Doctor();
        doctor1.setId(1);
        doctor1.setFullName("Ishwarya");
        doctor1.setSpecialty(Specialty.CARDIOLOGIST);
        doctor1.setExperience(2);
        doctor1.setQualification("MBBS");
        doctor1.setDesignation("Intern");
        doctor1.setUser(user1);
    }



    @Test
    void getAllDoctors_MustReturnSomething(){
        when(doctorRepository.findAll()).thenReturn(List.of(doctor1));
        List<Doctor> actualCall = doctorService.getAll();


        assertThat(actualCall).hasSize(1);
        Doctor doctor=actualCall.getFirst();
        assertThat(doctor.getId()).isEqualTo(1);
        assertThat(doctor.getFullName()).isEqualToIgnoringCase("Ishwarya");
        assertThat(doctor.getSpecialty()).isEqualTo(Specialty.CARDIOLOGIST);
        assertThat(doctor.getExperience()).isEqualTo(2);
        assertThat(doctor.getQualification()).isEqualTo("MBBS");
        assertThat(doctor.getDesignation()).isEqualTo("Intern");
        assertThat(doctor.getUser().getId()).isEqualTo(100);
        assertThat(doctor.getUser().getName()).isEqualTo("ishu");
        assertThat(doctor.getUser().getRole()).isEqualTo(Role.DOCTOR);
        assertThat(doctor.getUser().getEmail()).isEqualTo("i@gmail.com");
        assertThat(doctor.getUser().getPassword()).isEqualTo("ishu@123");
    }

    @Test
    void getAllDoctors_ReturnsEmptyList(){
        when(doctorRepository.findAll()).thenReturn(List.of());
        List<Doctor> actualCall = doctorService.getAll();
        assertThat(actualCall).hasSize(0);
        assertThat(actualCall).isEmpty();
    }

    @Test
    void getByDoctorId_doctorExists(){
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor1));
        Doctor doctor=doctorService.getDoctorById(1);
        assertThat(doctor.getId()).isEqualTo(1);
        assertThat(doctor.getFullName()).isEqualTo("Ishwarya");
        assertThat(doctor.getSpecialty()).isEqualTo(Specialty.CARDIOLOGIST);
        assertThat(doctor.getExperience()).isEqualTo(2);
        assertThat(doctor.getQualification()).isEqualTo("MBBS");
        assertThat(doctor.getDesignation()).isEqualTo("Intern");
        assertThat(doctor.getUser().getId()).isEqualTo(100);
        assertThat(doctor.getUser().getName()).isEqualTo("ishu");
        assertThat(doctor.getUser().getRole()).isEqualTo(Role.DOCTOR);
        assertThat(doctor.getUser().getEmail()).isEqualTo("i@gmail.com");
        assertThat(doctor.getUser().getPassword()).isEqualTo("ishu@123");
        verify(doctorRepository, times(1)).findById(1);
    }

    @Test
    void getById_categoryDoesNotExist(){
        when(doctorRepository.findById(100)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> doctorService.getDoctorById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Doctor id not found.");
        verify(doctorRepository, times(1)).findById(100);
    }



    @Test
    void addDoctor_mustSaveAndReturnDoctor(){
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor1);
        ReflectionTestUtils.setField(doctorService, "password", "ishu@123");
        when(userRepository.existsByName("ishu")).thenReturn(false);
        when(userRepository.existsByEmail("i@gmail.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        DoctorReqDto dto = new DoctorReqDto("ishu","Ishwarya","i@gmail.com",
                Specialty.CARDIOLOGIST,2,"MBBS","Intern");
        Doctor actualDoctor=  doctorService.addDoctor(dto);
        assertThat(actualDoctor.getFullName()).isEqualTo(doctor1.getFullName());
        assertThat(actualDoctor.getSpecialty()).isEqualTo(doctor1.getSpecialty());
        assertThat(actualDoctor.getExperience()).isEqualTo(doctor1.getExperience());
        assertThat(actualDoctor.getQualification()).isEqualTo(doctor1.getQualification());
        assertThat(actualDoctor.getDesignation()).isEqualTo(doctor1.getDesignation());
        assertThat(actualDoctor.getUser().getId()).isEqualTo(doctor1.getUser().getId());
        assertThat(actualDoctor.getUser().getName()).isEqualTo(doctor1.getUser().getName());
        assertThat(actualDoctor.getUser().getRole()).isEqualTo(doctor1.getUser().getRole());
        assertThat(actualDoctor.getUser().getEmail()).isEqualTo(doctor1.getUser().getEmail());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }




}
