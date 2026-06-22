package com.amaze.service;

import com.amaze.dto.*;
import com.amaze.enums.Day;
import com.amaze.enums.Status;
import com.amaze.exception.AppointmentException;
import com.amaze.exception.ConcurrentSlotBookingException;
import com.amaze.exception.ResourceNotFoundException;
import com.amaze.exception.TimeOverlapException;
import com.amaze.mapper.AppointmentMapper;
import com.amaze.model.Appointment;
import com.amaze.model.Doctor;
import com.amaze.model.Patient;
import com.amaze.repository.AppointmentRepository;
import com.amaze.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class AppointmentService {

    private static final String startTime = "startTime";
    private static final String message = "Appointment id not found";

    private AppointmentRepository appointmentRepository;
    private AvailabilityService availabilityService;
    private AppointmentMapper appointmentMapper;
    private PatientService patientService;
    private DoctorService doctorService;
    private ReportRepository reportRepository;

    public Appointment findByAppointmentId(int appId) {
        return appointmentRepository.findById(appId).orElseThrow();
    }

    public AppointmentSlotsResDto getAvailableSlotsByDateAndDocId(int docId, LocalDate appointmentDate) {


        //getting the day from the date
        Day day= Day.valueOf(appointmentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase());

        //getting start time of all appointments on that date for that doctor
        List<LocalTime> startTimingsOfAppointments=getAppointmentsStartTimeOnDate(appointmentDate, docId); // specific to that doctors

        // going to check the available slots of specific day and remove the slots whose start time matches with the startTimingsOfAppointments list
        List<AppointmentSlotsDto> availableSlots=availabilityService.listAvailableSlots(docId, day, startTimingsOfAppointments );
        availableSlots.sort(Comparator.comparing(AppointmentSlotsDto::startTime));
        return appointmentMapper.mapAvailableSlotsToDto(availableSlots);

    }

    public List<LocalTime> getAppointmentsStartTimeOnDate(LocalDate appointmentDate, int docId) {

        // getting all appointments on that date for that doctor, where the status is not cancelled
        List<Appointment> appointments= appointmentRepository.getAppointmentsStartTimeOnDate(docId, appointmentDate);


        // creating a list of start timings of existing appointments on that date for that doctor
        List<LocalTime> startTimings=new ArrayList<>();
        for(Appointment appointment:appointments){
            startTimings.add(appointment.getStartTime());
        }

        return startTimings;

    }

    public void addAppointment(int docId, String patientName, AppointmentBookReqDto dto) {
        List<Appointment> existingAppointments = appointmentRepository.findAppointmentsByPatientAndDate(patientName, dto.date());
        for (Appointment existing : existingAppointments) {
            boolean isOverlapping = dto.startTime().isBefore(existing.getEndTime()) &&
                    dto.endTime().isAfter(existing.getStartTime());
            if (isOverlapping) {
                throw new TimeOverlapException("You already have an appointment booked during this time frame. Please select a different slot.");
            }
        }
        Appointment appointment=appointmentMapper.mapDtoToEntity(dto);
        Doctor doctor=doctorService.getDoctorById(docId);
        Patient patient=patientService.getPatientByName(patientName);
        appointment.setStatus(Status.PENDING);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        if(appointmentRepository.existsByDoctorIdAndDateAndStartTimeAndStatusNot(
                        docId,
                        appointment.getDate(),
                        appointment.getStartTime(),
                        Status.CANCELLED)){
            throw new ConcurrentSlotBookingException("Slot already booked");
        }
        appointmentRepository.save(appointment);
    }

    public AppointmentResDto getAllAppointmentsForPatient(int page, int size, String direction, LocalDate date, String doctorNameSearch, String name) {
        Sort sort ="ASC".equalsIgnoreCase(direction) ?
                Sort.by("date").ascending().and(Sort.by(startTime).ascending())
                : Sort.by("date").descending().and(Sort.by(startTime).descending());

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Appointment> appointmentPage=appointmentRepository.findAllByPatientUserName(name,date,doctorNameSearch,pageable);

        List<AppointmentDto> appointmentDtoListOfPage = appointmentPage.stream().map(appointmentMapper::mapEntityToResDto).toList();

        return appointmentMapper.mapListToDto(appointmentPage, appointmentDtoListOfPage);
    }

    public AppointmentResDto getAllAppointmentsByStatusForPatient(int page, int size, Status status, String direction, LocalDate date, String doctorNameSearch, String name) {
        Sort sort ="ASC".equalsIgnoreCase(direction) ?
                Sort.by("date").ascending().and(Sort.by(startTime).ascending())
                : Sort.by("date").descending().and(Sort.by(startTime).descending());

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Appointment> appointmentPage=appointmentRepository.findAllByPatientUserNameAndStatus(name,status,date,doctorNameSearch,pageable);

        List<AppointmentDto> appointmentPatientDtoListOfPage = appointmentPage.stream().map(appointmentMapper::mapEntityToResDto).toList();

        return appointmentMapper.mapListToDto(appointmentPage, appointmentPatientDtoListOfPage);
    }

    //All upcoming appointments for doctor
    public AppointmentResDto getAllAppointmentsForDoctor(int page, int size, String direction, LocalDate date, String patientNameSearch, String name) {
        List<Status> statuses=new ArrayList<>();
        statuses.add(Status.CANCELLED);
        statuses.add(Status.COMPLETED);
        Sort sort ="ASC".equalsIgnoreCase(direction) ?
                Sort.by("date").ascending().and(Sort.by(startTime).ascending())
                : Sort.by("date").descending().and(Sort.by(startTime).descending());

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Appointment> appointmentPage=appointmentRepository.findAllByDoctorUserNameAndStatusNotIn(name,statuses,date,patientNameSearch,pageable);

        List<AppointmentDto> appointmentDtoListOfPage = appointmentPage.stream().map(appointmentMapper::mapEntityToResDto).toList();

        return appointmentMapper.mapListToDto(appointmentPage, appointmentDtoListOfPage);


    }

    public AppointmentResDto getAllAppointmentsByStatusForDoctor(int page, int size, Status status, String direction, LocalDate date, String patientNameSearch, String name) {
        Sort sort ="ASC".equalsIgnoreCase(direction) ?
                Sort.by("date").ascending().and(Sort.by(startTime).ascending())
                : Sort.by("date").descending().and(Sort.by(startTime).descending());

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Appointment> appointmentPage=appointmentRepository.findAllByDoctorUserNameAndStatus(name,status,date,patientNameSearch,pageable);

        List<AppointmentDto> appointmentDtoListOfPage = appointmentPage.stream().map(appointmentMapper::mapEntityToResDto).toList();

        return appointmentMapper.mapListToDto(appointmentPage, appointmentDtoListOfPage);

    }

    public AppointmentResDto getAllAppointmentsForAdmin(int docId, int page, int size,String direction, LocalDate date, String patientNameSearch) {
        String doctorName=doctorService.getDoctorById(docId).getUser().getName();
       return getAllAppointmentsForDoctor(page,size, direction, date, patientNameSearch, doctorName);


    }

    public AppointmentResDto getAllAppointmentsByStatusForAdmin(int docId, int page, int size, Status status,String direction, LocalDate date, String patientNameSearch) {
        String doctorName=doctorService.getDoctorById(docId).getUser().getName();
        return getAllAppointmentsByStatusForDoctor(page,size,status, direction, date, patientNameSearch, doctorName);

    }

    public void setCancel(String name) {
        appointmentRepository.setCancel(name);
    }

    public void setCancelByAdmin(int docId) {
        String doctorName=doctorService.getDoctorById(docId).getUser().getName();
        setCancel(doctorName);
    }

    public void confirmAppointment(int appId, String name) {
        Appointment appointment=appointmentRepository.findById(appId).orElseThrow(
                ()->new ResourceNotFoundException(message)
        );
        if(!appointment.getDoctor().getUser().getName().contentEquals(name)){
            throw new AppointmentException("Appointment not belongs to this doctor");
        }
        appointment.setStatus(Status.CONFIRMED);
        appointmentRepository.save(appointment);
    }

    public void cancelAppointmentByPatient(int appId, String name) {
        Appointment appointment=appointmentRepository.findById(appId).orElseThrow(
                ()->new ResourceNotFoundException(message)
        );
        if(!appointment.getPatient().getUser().getName().contentEquals(name))
            throw new AppointmentException("Appointment not belongs to this patient");

        if(appointment.getStatus().equals(Status.COMPLETED)){
            throw new AppointmentException("Completed appointment cannot be cancelled");
        }
        if(appointment.getStatus().equals(Status.CANCELLED)){
            throw new AppointmentException("Already cancelled");
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public void cancelAppointmentByDoctor(int appId, String name) {
        Appointment appointment=appointmentRepository.findById(appId).orElseThrow(
                ()->new ResourceNotFoundException(message)
        );
        if(!appointment.getDoctor().getUser().getName().contentEquals(name)){
            throw new AppointmentException("Appointment not belongs to this doctor");
        }
        if(appointment.getStatus().equals(Status.COMPLETED)){
            throw new AppointmentException("Completed appointment cannot be cancelled");
        }
        if(appointment.getStatus().equals(Status.CANCELLED)){
            throw new AppointmentException("Already cancelled");
        }
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
    }


    public void cancelAppointmentByAdmin(int appId, int docId) {
        String doctorName=doctorService.getDoctorById(docId).getUser().getName();
        cancelAppointmentByDoctor(appId,doctorName);
    }

    public Appointment getById(int appId) {
        return appointmentRepository.findById(appId)
                .orElseThrow(()->new ResourceNotFoundException(message));
    }

    public void completeAppointment(int appId) {
        Appointment appointment=appointmentRepository.findById(appId).orElseThrow(
                ()->new ResourceNotFoundException(message)
        );
        appointment.setStatus(Status.COMPLETED);
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public StatDto getDoctorStats(String name) {
        List<Status> statuses=new ArrayList<>();
        statuses.add(Status.CANCELLED);
        statuses.add(Status.COMPLETED);
        long upcoming=appointmentRepository.getCountAllUpcomingAppointmentsForDoctor(name,statuses);
        long completed=appointmentRepository.getCountAllAppointmentsByStatusForDoctor(Status.COMPLETED,name);
        long pending=appointmentRepository.getCountAllAppointmentsByStatusForDoctor(Status.PENDING,name);
        List<String> label=List.of("Upcoming Appointments","Completed Appointments","Pending Appointments");
        List<Long> count=List.of(upcoming,completed,pending);
        return new StatDto(label,count);

    }

    public StatDto getPatientStats(String name) {
        long reportCount=reportRepository.getCountFindAllByPatientUserNameAndIsActive(name);
        long confirmed = appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.CONFIRMED, name);
        long completed=appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.COMPLETED, name);
        long pending=appointmentRepository.getCountAllAppointmentsByStatusForPatient(Status.PENDING, name);
        List<String> label=List.of("Medical Reports","Upcoming Appointments","Completed Appointments","Pending Appointments");
        List<Long> count=List.of(reportCount,confirmed,completed,pending);
        return new StatDto(label,count);
    }

    public PatientResDto getPatientDetailsByAppId(int appId) {
        Appointment appointment = findByAppointmentId(appId);
        return appointmentMapper.mapAppointmentToPatientDto(appointment);
    }
}
