package com.amaze.service;

import com.amaze.dto.GraphDto;
import com.amaze.dto.StatDto;
import com.amaze.enums.Role;
import com.amaze.enums.Specialty;
import com.amaze.enums.Status;
import com.amaze.model.Appointment;
import com.amaze.model.Doctor;
import com.amaze.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AdminService {
    private UserService userService;
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    public StatDto getStats() {
        List<User> userList=userService.getAll();
        List<Appointment> appointmentList=appointmentService.getAll();
        long totalDoctors=  userList.stream().filter(user->user.getRole()== Role.DOCTOR).toList().size();
        long totalPatients=  userList.stream().filter(user->user.getRole()== Role.PATIENT).toList().size();
        long totalCompletedAppointments=appointmentList.stream().filter(appointment -> appointment.getStatus()== Status.COMPLETED).toList().size();
        long totalPendingAppointments=appointmentList.stream().filter(appointment -> appointment.getStatus()== Status.PENDING).toList().size();
        List<String> label= List.of("Total Patients","Total Doctors","Completed Appointments","Pending Appointments");
        List<Long> count=List.of(totalPatients,totalDoctors,totalCompletedAppointments,totalPendingAppointments);
        return new StatDto(label,count);

    }

    public GraphDto getGraph1() {
        List<String> label=List.of(
                "GENERAL_PHYSICIAN",
                "CARDIOLOGIST",
                "NEUROLOGIST",
                "ORTHOPEDIC",
                "DERMATOLOGIST",
                "PEDIATRICIAN",
                "GYNECOLOGIST",
                "ENT",
                "OPHTHALMOLOGIST",
                "PSYCHIATRIST",
                "DENTIST",
                "SURGEON"
        );
        List<Doctor> doctors=doctorService.getAll();
        List<Long> data=label.stream().map(specialty->doctors.stream().filter(doctor -> doctor.getSpecialty()== Specialty.valueOf(specialty)).count()).toList();

        return new GraphDto("Doctor Specialty Distribution",label,data);

    }

    public GraphDto getGraph2() {
        List<Appointment> appointmentList=appointmentService.getAll();
        List<String> label=List.of("PENDING","CONFIRMED","COMPLETED","CANCELLED");
        long d1=appointmentList.stream().filter(appointment -> appointment.getStatus()==Status.PENDING).toList().size();
        long d2=appointmentList.stream().filter(appointment -> appointment.getStatus()==Status.CONFIRMED).toList().size();
        long d3=appointmentList.stream().filter(appointment -> appointment.getStatus()==Status.COMPLETED).toList().size();
        long d4=appointmentList.stream().filter(appointment -> appointment.getStatus()==Status.CANCELLED).toList().size();

        return new GraphDto("Appointments Count By Status",label,List.of(d1,d2,d3,d4));


    }
}
