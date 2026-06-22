package com.amaze.controller;

import com.amaze.dto.*;
import com.amaze.enums.Status;
import com.amaze.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/appointment")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {
    private AppointmentService appointmentService;


    /* in step 1 of booking appointment, we will get date and docID and
    we will give list of available slots on that date for that doctor
    also setting pagination */
    @GetMapping("/book-step1/{docId}")
    public AppointmentSlotsResDto getAvailableSlotsByDateAndDocId(@PathVariable int docId,
                                                                     @Valid @RequestParam LocalDate appointmentDate) {
        return appointmentService.getAvailableSlotsByDateAndDocId(docId, appointmentDate);
    }

    /* in step 2 of booking appointment, we will get date, doctorId, startTime
    endTime, details for appointment (symptoms, reason) and patient can be taken from principal
    and perform add appointment */
    @PostMapping("/book-step2/{docId}")
    public void addAppointment(@PathVariable int docId,
                               @Valid @RequestBody AppointmentBookReqDto dto, Principal principal) {


        appointmentService.addAppointment(docId, principal.getName(), dto);
    }

    @GetMapping("/patient/all-appointments") // it also must be sorted descending
    public AppointmentResDto getAllAppointmentsForPatient(@RequestParam int page,
                                                                 @RequestParam int size,
                                                          @RequestParam String direction,
                                                          @RequestParam(required = false) LocalDate date,
                                                          @RequestParam(required = false) String word,
                                                                 Principal principal) {
        return appointmentService.getAllAppointmentsForPatient(page, size, direction,date,word, principal.getName());
    }


    @GetMapping("/patient/all-appointments-by-status") // it also must be sorted ascending
    public AppointmentResDto getAllAppointmentsByStatusForPatient(@RequestParam int page,
                                                                         @RequestParam int size,
                                                                  @Valid @RequestParam Status status,
                                                                  @RequestParam String direction,
                                                                  @RequestParam(required = false) LocalDate date,
                                                                  @RequestParam(required = false) String word,

                                                                         Principal principal) {
        return appointmentService.getAllAppointmentsByStatusForPatient(page, size, status,direction,date,word, principal.getName());
    }


    @GetMapping("/doctor/all-upcoming-appointments")
    // it must not be cancelled and also must not be completed and must be sorted in ascending timings
    public AppointmentResDto getAllAppointmentsForDoctor(@RequestParam int page,
                                                         @RequestParam int size,
                                                         @RequestParam String direction,
                                                         @RequestParam(required = false) LocalDate date,
                                                         @RequestParam(required = false) String word,
                                                         Principal principal) {
        return appointmentService.getAllAppointmentsForDoctor(page, size,direction,date,word, principal.getName());


    }

    @PutMapping("doctor/past-appointments-pending-or-confirmed-cancelled")
    public void setCancel(Principal principal) {
        appointmentService.setCancel(principal.getName());
    }


    @GetMapping("/doctor/all-appointments-by-status") // it also must be sorted ascending
    public AppointmentResDto getAllAppointmentsByStatusForDoctor(@RequestParam int page,
                                                                 @RequestParam int size,
                                                                 @Valid @RequestParam Status status,
                                                                 @RequestParam String direction,
                                                                 @RequestParam(required = false) LocalDate date,
                                                                 @RequestParam(required = false) String word,
                                                                 Principal principal) {
        return appointmentService.getAllAppointmentsByStatusForDoctor(page, size, status,direction,date,word, principal.getName());
    }

    @GetMapping("/admin/all-upcoming-appointments/{docId}")
    // it must not be cancelled and must be sorted in ascending timings
    public AppointmentResDto getAllAppointmentsForAdmin(@PathVariable int docId,
                                                        @RequestParam int page,
                                                        @RequestParam int size,
                                                        @RequestParam String direction,
                                                        @RequestParam(required = false) LocalDate date,
                                                        @RequestParam(required = false) String word) {
        return appointmentService.getAllAppointmentsForAdmin(docId, page, size,direction,date,word);


    }

    @PutMapping("admin/past-appointments-pending-or-confirmed-cancelled/{docId}")
    public void setCancel(@PathVariable int docId) {

        appointmentService.setCancelByAdmin(docId);
    }

    @GetMapping("/admin/all-appointments-by-status/{docId}") // it also must be sorted ascending
    public AppointmentResDto getAllAppointmentsByStatusForAdmin(@PathVariable int docId,
                                                                @RequestParam int page,
                                                                @RequestParam int size,
                                                                @Valid @RequestParam Status status,
                                                                @RequestParam String direction,
                                                                @RequestParam(required = false) LocalDate date,
                                                                @RequestParam(required = false) String word) {
        return appointmentService.getAllAppointmentsByStatusForAdmin(docId, page, size, status,direction,date,word);
    }

    @PutMapping("/confirm-appointment/{appId}")
    public void confirmAppointment(@PathVariable int appId,
                                   Principal principal) {
        appointmentService.confirmAppointment(appId, principal.getName());
    }

    @PutMapping("/patient-cancel-appointment/{appId}")
    public void cancelAppointmentByPatient(@PathVariable int appId,
                                           Principal principal) {
        appointmentService.cancelAppointmentByPatient(appId, principal.getName());
    }

    @PutMapping("/doctor-cancel-appointment/{appId}")
    public void cancelAppointmentByDoctor(@PathVariable int appId,
                                          Principal principal) {
        appointmentService.cancelAppointmentByDoctor(appId, principal.getName());
    }

    @PutMapping("/admin-cancel-appointment/{appId}/{docId}")
    public void cancelAppointmentByDoctor(@PathVariable int appId,
                                          @PathVariable int docId) {
        appointmentService.cancelAppointmentByAdmin(appId, docId);
    }


    @GetMapping("/doctor/stat")
    public StatDto getDoctorStats(Principal principal) {
        return appointmentService.getDoctorStats(principal.getName());
    }
    @GetMapping("/patient/stat")
    public StatDto getPatientStats(Principal principal) {
        return appointmentService.getPatientStats(principal.getName());
    }

    @GetMapping("/doctor/patient-details/{appId}")
    public PatientResDto getPatientDetailsByAppId(@PathVariable int appId) {
        return appointmentService.getPatientDetailsByAppId(appId);
    }













}
