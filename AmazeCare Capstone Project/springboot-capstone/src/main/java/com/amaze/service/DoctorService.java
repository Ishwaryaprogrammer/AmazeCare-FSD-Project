package com.amaze.service;

import com.amaze.dto.*;
import com.amaze.enums.Specialty;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.exception.UserEmailAlreadyExistsException;
import com.amaze.exception.UserNameExistsException;
import com.amaze.mapper.DoctorMapper;
import com.amaze.model.Doctor;
import com.amaze.model.User;
import com.amaze.repository.DoctorRepository;
import com.amaze.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    @Value("${doctor.temp.password}")
    private String password;


    public Doctor addDoctor(DoctorReqDto dto) {

        User user=DoctorMapper.doctorDtoToUser(dto);
        Doctor doctor=DoctorMapper.doctorDtoToDoctorEntity(dto);
        user.setPassword(password);
        if(userRepository.existsByName(user.getName())){
            throw new UserNameExistsException("Username already exists");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserEmailAlreadyExistsException("Email already exists");
        }
        user.setPassword(   new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        doctor.setUser(user);
        return doctorRepository.save(doctor);
    }

    public DoctorResDto getById(int id) {
        return DoctorMapper.mapEntityToDto(getDoctorById(id));

    }

    public void update(int id, DoctorUpdateReqDto dto) {
        Doctor doctor=getDoctorById(id);
        doctorRepository.save(doctorMapper.mapUpdateReqDtoToEntity(doctor, dto));

    }

    public Doctor getDoctorById(int id){
        return doctorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Doctor id not found."));

    }


    public DoctorListResDto all(int pageNo, int size, String word) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<Doctor> page=doctorRepository.findAllWithWord(word,pageable);
        return doctorMapper.mapPageToDto(page);

    }

    public DoctorListResDto allBySpecialty(int pageNo, int size,String word, Specialty specialty) {
        Pageable pageable=PageRequest.of(pageNo,size);
        Page<Doctor> page=doctorRepository.findAllBySpecialtyWithWord(specialty,word,pageable);
        return doctorMapper.mapPageToDto(page);
    }

    public Doctor getByName(String name){
        return doctorRepository.findByUserName(name);
    }

    public DoctorResDto getByUsername(String name) {
        return DoctorMapper.mapEntityToDto(getByName(name));
    }


    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public SpecialtyDto getSpecialties() {
        return new SpecialtyDto(Arrays.stream(Specialty.values()).toList());
    }
}
