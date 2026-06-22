package com.amaze.service;

import com.amaze.dto.AppointmentSlotsDto;
import com.amaze.dto.AvailabilityReqDto;
import com.amaze.dto.AvailabilityResDto;
import com.amaze.enums.Day;
import com.amaze.exception.AppointmentException;
import com.amaze.exception.EndTimeInvalidException;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.exception.TimeOverlapException;
import com.amaze.mapper.AvailabilityMapper;
import com.amaze.model.Availability;
import com.amaze.model.Doctor;
import com.amaze.repository.AvailabilityRepository;
import com.amaze.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AvailabilityService {

    private AvailabilityRepository availabilityRepository;
    private AvailabilityMapper availabilityMapper;
    private DoctorService doctorService;
    private DoctorRepository doctorRepository;

    public Availability add( AvailabilityReqDto dto, String name) {

        Availability availability=AvailabilityMapper.mapDtoToEntity(dto);
        availability.setDoctor(doctorRepository.findByUserName(name));
        checkTiming(availability);

        List<Availability> availabilities=getAllAvailabilitiesByDoctorId(availability.getDoctor().getId()).stream().filter(availability2-> availability2.getDay().equals(availability.getDay())).toList();
        for(Availability old:availabilities){
            if(availability.getStartTime().isBefore(old.getEndTime()) &&
                    availability.getEndTime().isAfter(old.getStartTime())){
                throw new TimeOverlapException("Timings of this availability overlaps with others.");

            }
        }
        return availabilityRepository.save(availability);
    }




    public void update(int id,  AvailabilityReqDto dto, String name) {


        Availability availability=availabilityRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No existing availability with given id"));
        if(!availabilityRepository.existsByIdAndDoctorUserName(id,name)){
            throw new AppointmentException("Availability doesn't belongs to doctor");
        }
        availability=availabilityMapper.mapDtoToExistingEntity(availability,dto);
        checkTiming(availability);

        Availability finalAvailability = availability;  // suggestion given by intelliJ that lamba expression variable must be final or effectively final variable.

        List<Availability> availabilities=getAllAvailabilitiesByDoctorId(availability.getDoctor().getId()).stream().filter(availability2-> availability2.getDay().equals(finalAvailability.getDay())).toList();
        availabilities=availabilities.stream().filter(availability1 -> availability1.getId() != finalAvailability.getId()).toList();
        for(Availability old:availabilities){
            if(availability.getStartTime().isBefore(old.getEndTime()) &&
                    availability.getEndTime().isAfter(old.getStartTime())){
                throw new TimeOverlapException("Timings of this availability overlaps with others.");

            }
        }

        availabilityRepository.save(availability);

    }

    //helper function
    public List<Availability> getAllAvailabilitiesByDoctorId(int docId){
        return availabilityRepository.findAllByDoctorId(docId);
    }

    //helper function
    public void checkTiming(Availability availability){
        if(availability.getEndTime().isBefore(availability.getStartTime()) || availability.getEndTime().equals(availability.getStartTime())){
            throw new EndTimeInvalidException("Invalid timing. Endtime must be after start time");
        }
    }

    public Availability  getById(int id){
        return availabilityRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Availability Id not found"));

    }



    public void delete(int id) {
        getById(id);
        availabilityRepository.deleteById(id);
    }

    public AvailabilityResDto viewAll(int page, int size, String name) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Availability> availabilityPage=availabilityRepository.findAllByDoctorUserName(name,pageable);
        return AvailabilityMapper.mapPageToDto(availabilityPage);
    }

    public AvailabilityResDto viewAllByDoctorId(int page, int size, int docId) {

        Doctor doctor=doctorService.getDoctorById(docId);
        return viewAll(page,size,doctor.getUser().getName());
    }


    public List<AppointmentSlotsDto> listAvailableSlots(int docId, Day day, List<LocalTime> startTimingsOfAppointments) {


        List<Availability> availabilities=availabilityRepository.getAvailabilitiesByDayAndDoctorId(docId,day);

        List<AppointmentSlotsDto> slots=new ArrayList<>();

        for(Availability a:availabilities){
            int duration=a.getDuration();
            LocalTime startTime=a.getStartTime();
            LocalTime endTime=a.getEndTime();

            LocalTime i=startTime;
            while(i.isBefore(endTime.minusMinutes(duration)) || i.equals(endTime.minusMinutes(duration))){

                if(!startTimingsOfAppointments.contains(i)){
                    slots.add(new AppointmentSlotsDto(i,i.plusMinutes(duration),duration));
                }
                i=i.plusMinutes(duration);
            }

        }
        return slots;
    }


    public AvailabilityResDto viewAllByDay(int page, int size,Day day, String name) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Availability> availabilityPage=availabilityRepository.findAllByDoctorUserNameAndDay(name,day,pageable);
        return AvailabilityMapper.mapPageToDto(availabilityPage);

    }

    public AvailabilityResDto viewAllByDoctorIdAndByDay(int page, int size,Day day, int docId) {
        Doctor doctor=doctorService.getDoctorById(docId);
        return viewAllByDay(page,size,day,doctor.getUser().getName());
    }
}
