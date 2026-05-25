package com.controller;

import com.config.HibernateConfig;
import com.enums.Day;
import com.enums.Role;
import com.enums.Specialty;
import com.enums.Status;
import com.exception.ProfileNotFoundException;
import com.exception.ResourceNotFoundException;
import com.model.*;
import com.service.*;
import com.util.Slot;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Session session = HibernateConfig.getSessionFactory().openSession();
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService(session);
        while (true) {
            System.out.println("WELCOME TO AMAZECARE");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");

            int op = sc.nextInt();
            if (op == 0) {
                break;
            }
            switch (op) {
                case 1:
                    User user1 = new User();
                    System.out.println("Enter name:");
                    user1.setName(sc.next());
                    System.out.println("Enter email");
                    user1.setEmail(sc.next());
                    System.out.println("Enter password");
                    user1.setPassword(sc.next());
                    System.out.println("Enter Role");
                    user1.setRole(Role.valueOf(sc.next().toUpperCase()));
                    try {
                        userService.register(user1);
                    }catch (RuntimeException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Registered Successfully");
                    break;
                case 2:
                    System.out.println("Enter email");
                    String mail = sc.next();
                    System.out.println("Enter password");
                    String pass = sc.next();
                    try {
                        User user = userService.login(mail, pass);
                        System.out.println("LoggedIn Successfully");
                        if (user.getRole() == Role.ADMIN) {
                            adminLogin(userService, sc, session);
                        } else if (user.getRole() == Role.DOCTOR) {
                            doctorLogin(user, userService, sc, session);
                        } else {
                            patientLogin(user,userService, sc, session);
                        }
                    } catch (ResourceNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }

        }

        sc.close();
        session.close();


    }

    public static void adminLogin( UserService userService, Scanner sc, Session session) {
        DoctorService doctorService=new DoctorService(session);
        while (true) {
            System.out.println("1. Add Doctor ");
            System.out.println("2. Update Doctor");
            System.out.println("3. Delete Doctor by id");
            System.out.println("4. View all Doctors");
            System.out.println("5. View Doctors by speciality");
//            System.out.println("6. View Doctors by availability Day");
//            System.out.println("7. Manage Appointments");
//            System.out.println("8. Generate Records");
            System.out.println("0. Go to main menu");
            int op = sc.nextInt();
            if (op == 0) {
                break;
            }
            switch (op) {
                case 1:
                    Doctor doctor=new Doctor();
                    User user1=new User();
                    System.out.println("Enter name:");
                    user1.setName(sc.next());
                    System.out.println("Enter email");
                    user1.setEmail(sc.next());
                    System.out.println("Enter password");
                    user1.setPassword(sc.next());
                    user1.setRole(Role.DOCTOR);
                    System.out.println("Enter specialty:");
                    doctor.setSpecialty(Specialty.valueOf(sc.next().toUpperCase()));
                    System.out.println("Enter experience:");
                    doctor.setExperience(sc.nextInt());
                    System.out.println("Enter qualification:");
                    doctor.setQualification(sc.next());
                    System.out.println("Enter designation:");
                    doctor.setDesignation(sc.next());
                    doctor.setUser(user1);
                    try {
                        userService.register(user1);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    doctorService.addDoctor(doctor);
                    System.out.println("Added Successfully");
                    break;
                case 2:
                    System.out.println("Enter id of doctor to update:");
                    int id=sc.nextInt();
                    System.out.println("----Existing information----");
                    Doctor doctor1=null;
                    try {
                        doctor1=doctorService.getById(id);
                        System.out.println(doctor1);
                    }catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Enter specialty:");
                    doctor1.setSpecialty(Specialty.valueOf(sc.next().toUpperCase()));
                    System.out.println("Enter experience:");
                    doctor1.setExperience(sc.nextInt());
                    System.out.println("Enter qualification:");
                    doctor1.setQualification(sc.next());
                    System.out.println("Enter designation:");
                    doctor1.setDesignation(sc.next());
                    doctorService.updateDoctor(doctor1);
                    System.out.println("Updated Successfully");
                    break;
                case 3:
                    System.out.println("Enter Doctor id to delete:");
                    try {
                        int id1= sc.nextInt();
                        Doctor doctor2=doctorService.getById(id1);
                        doctorService.deleteById(id1);
                        userService.delete(doctor2.getUser().getId());
                        System.out.println("Deleted Successfully");
                    }catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("---------All doctors---------");
                    doctorService.getAll().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Enter specialty:");
                    try{
                    doctorService.getBySpecialty(Specialty.valueOf(sc.next().toUpperCase())).forEach(System.out::println);}
                    catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;

            }
        }

    }

    public static void doctorLogin(User user, UserService userService, Scanner sc, Session session) {
        AvailabilityService availabilityService=new AvailabilityService(session);
        AppointmentService appointmentService=new AppointmentService(session);
        Doctor doctor = null;
        try {
            doctor = new DoctorService(session).getDoctorByUserId(user.getId());
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
        while (true) {
            System.out.println("1. Add availability");
            System.out.println("2. Update availability");
            System.out.println("3. delete availability");
            System.out.println("4. View availability");
            System.out.println("5. Manage appointments");
//            System.out.println("View all appointments");
//            System.out.println("View appointment by status");
//            System.out.println("View pending appointments");
//            System.out.println("Confirm appointments");

            System.out.println("6. Do consultation");
            // first list of all confirmed appointments and ask to choose one.
            // after confirmed there should be a list with see patient details with that appointment details, medical history, add,update,view;
            // before adding consultation the doctor must be able to see that patient's details along with neccessary details
            // (only 1 consultation per appointment)
            // later to add change password
            System.out.println("7. view all previous consultations record");
            System.out.println("0. Go to main menu");
            int op = sc.nextInt();
            if (op == 0) {
                break;
            }
            switch (op) {
                case 1:
                    Availability availability=new Availability();
                    System.out.println("Enter the day:");
                    availability.setDay(Day.valueOf(sc.next().toUpperCase()));
                    System.out.println("Enter start time (HH:MM):");
                    LocalTime startTime=LocalTime.parse(sc.next());
                    availability.setStartTime(startTime);
                    System.out.println("Enter End time (HH:MM):");
                    LocalTime endTime=LocalTime.parse(sc.next());
                    availability.setEndTime(endTime);
                    int duration;
                    while(true){
                    System.out.println("Enter duration of one slot:");
                     duration = sc.nextInt();
                    int count=0;
                    LocalTime i = startTime;
                    while(i.isBefore(endTime.minusMinutes(duration)) || i.equals(endTime.minusMinutes(duration))){
                        count+=1;
                        i = i.plusMinutes(duration);
                    }
                    if(!i.equals(endTime)){
                        System.out.println("Kindly set the slot duration in such a way that it parts the availability time equally");
                    }else{
                        System.out.println("Total number of slots will be "+count);
                        break;
                    }
                    }
                    availability.setDuration(duration);
                    availability.setDoctor(doctor);
                    try {
                        availabilityService.addAvailability(availability);
                        System.out.println("Availability added Successfully");
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("Enter the id of availability to update:");
                    int id= sc.nextInt();
                    System.out.println("----------Existing------");
                    try{
                        Availability availability2 =availabilityService.getById(id, doctor.getId());
                        System.out.println(availability2);
                        System.out.println("Enter the day:");
                        availability2.setDay(Day.valueOf(sc.next().toUpperCase()));
                        System.out.println("Enter start time (HH:MM):");
                        availability2.setStartTime(LocalTime.parse(sc.next()));
                        System.out.println("Enter End time (HH:MM):");
                        availability2.setEndTime(LocalTime.parse(sc.next()));
                        System.out.println("Enter duration of one slot:");
                        availability2.setDuration(sc.nextInt());
                        try {
                            availabilityService.updateAvailability(availability2);
                            System.out.println("Availability updated Successfully");
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }

                    break;
                case 3:
                    System.out.println("Enter the id of availability to delete:");
                    int id2= sc.nextInt();
                    try {
                        availabilityService.getById(id2,doctor.getId());
                    }catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    availabilityService.delete(id2);
                    System.out.println("Deleted successfully");

                    break;
                case 4:
                    System.out.println("--------All availabilities---------");
                    availabilityService.getAllForMe(doctor.getId()).forEach(System.out::println);
                    break;
                case 5:
                    while(true) {
                        System.out.println("----Manage Appointments-----");
                        System.out.println("1. View pending appointments for confirmation");
                        System.out.println("2. View Confirmed appointments");
                        System.out.println("3. View completed appointments");
                        System.out.println("4. Confirm pending appointments");
                        System.out.println("5. Cancel appointments");
                        System.out.println("0. Back");
                        op = sc.nextInt();
                        if(op==0){
                            break;
                        }
                        switch (op){
                            case 1:
                                System.out.println("---------pending appointments for confirmation---------");
                                List<Appointment> appointments=appointmentService.getDoctorPendingAppointments(doctor.getId());
                                for (Appointment a:appointments){
                                    System.out.println(a.printForDoctor());
                                }
                                break;
                            case 2:
                                System.out.println("---------Confirmed appointments---------");
                                List<Appointment> appointments1=appointmentService.getDoctorConfirmedAppointments(doctor.getId());
                                for (Appointment a:appointments1){
                                    System.out.println(a.printForDoctor());
                                }
                                break;
                            case 3:
                                System.out.println("--------completed appointments----------");
                                List<Appointment> appointments2=appointmentService.getDoctorCompletedAppointments(doctor.getId());
                                for (Appointment a:appointments2){
                                    System.out.println(a.printForDoctor());
                                }
                                break;
                            case 4:
                                System.out.println("--------Confirm pending appointments----------");
                                System.out.println("Enter Appointment id to confirm:");
                                try{
                                appointmentService.changeAppointmentStatusConfirmed(doctor.getId(),sc.nextInt());
                                }
                                catch (ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                    break;
                                }
                                System.out.println("Confirmed appointment");
                                break;
                            case 5:
                                System.out.println("--------Cancel pending appointments----------");
                                System.out.println("Enter Appointment id to cancel:");
                                try{
                                    appointmentService.changeAppointmentStatusCancelled(doctor.getId(),sc.nextInt());
                                }
                                catch (ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                    break;
                                }
                                System.out.println("Cancelled appointment");
                                break;
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    }


                    break;
                case 6:
                    System.out.println("-------Consultation--------");
                    System.out.println("Enter id of appointment to do consultation:");
                    int appId=sc.nextInt();
                    Appointment appointment;
                    try {
                        appointment=appointmentService.checkAppointmentOwnership(doctor.getId(),appId);
                    }catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    ConsultationService consultationService=new ConsultationService(session);

                    Consultation consultation=new Consultation();
                    consultation.setAppointment(appointment);
                    System.out.println("Enter the Physical examination:");
                    sc.nextLine();
                    consultation.setPhyExam(sc.nextLine());
                    System.out.println("Enter the Symptoms:");
                    consultation.setSymptoms(sc.nextLine());
                    System.out.println("Enter the Treatment:");
                    consultation.setTreatment(sc.nextLine());
                    System.out.println("Enter the Prescription:");
                    consultation.setPrescription(sc.nextLine());
                    System.out.println("Enter the Recommendation:");
                    consultation.setRecommended(sc.nextLine());
                    try {
                        consultationService.addConsultation(consultation);
                    }catch (RuntimeException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    appointmentService.changeAppointmentStatusCompleted(appointment.getId());
                    System.out.println("Consultation added and appointment completed successfully");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

        }
    }

    public static void patientLogin(User user, UserService userService, Scanner sc, Session session) {
        PatientService patientService=new PatientService(session);
        DoctorService doctorService=new DoctorService(session);
        Patient patient=new Patient();
        while (true) {
            System.out.println("1. Add Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. View Profile");
            System.out.println("4. Search doctor by speciality");
            System.out.println("5. Manage appointments");
            System.out.println("6. View Consultations");
            System.out.println("0. Go to main menu");
            int op = sc.nextInt();
            if (op == 0) {
                break;
            }
            switch (op) {
                case 1:
                    Patient patient1 = null;
                    try {
                        patient1 = patientService.getProfile(user.getId());
                    }catch (ProfileNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    if(patient1!=null){
                        System.out.println("Profile already exists");
                        break;
                    }
                    System.out.println("Enter Date of Birth (yyyy-MM-dd):");
                    patient.setDob(LocalDate.parse(sc.next()));
                    System.out.println("Enter gender:");
                    patient.setGender(sc.next());
                    System.out.println("Enter contact:");
                    patient.setContact(sc.next());
                    patient.setUser(user);
                    patientService.add(patient);
                    System.out.println("Added profile");
                    break;
                case 2:
                    try{
                    patient =patientService.getProfile(user.getId());}
                    catch(ProfileNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Enter Date of Birth (yyyy-MM-dd):");
                    patient.setDob(LocalDate.parse(sc.next()));
                    System.out.println("Enter gender:");
                    patient.setGender(sc.next());
                    System.out.println("Enter contact:");
                    patient.setContact(sc.next());
                    patient.setUser(user);
                    patientService.update(patient);
                    System.out.println("Updated profile");
                    break;
                case 3:
                    try{
                        System.out.println(patientService.getProfile(user.getId()));}
                    catch(ProfileNotFoundException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case 4:
                    System.out.println("Enter specialty:");
                    try{
                        doctorService.getBySpecialty(Specialty.valueOf(sc.next().toUpperCase())).forEach(System.out::println);}
                    catch (ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:

                    while(true){
                        AvailabilityService availabilityService=new AvailabilityService(session);
                        AppointmentService appointmentService=new AppointmentService(session);
                        System.out.println("-----Manage Appointments-------");// these will be specific to that patient
                        System.out.println("1. Make an appointment");
                        System.out.println("2. All Confirmed appointments");
                        System.out.println("3. View Confirmed appointments"); //
                        System.out.println("4. Cancel appointment"); // not completed appointments
                        System.out.println("0. Back ");
                        op=sc.nextInt();
                        if(op==0){
                            break;
                        }
                        switch (op){
                            case 1:
                                System.out.println("---Booking appointment---");
                                try{
                                    patient =patientService.getProfile(user.getId());}
                                catch(ProfileNotFoundException e){
                                    System.out.println(e.getMessage());
                                    System.out.println("Kindly set profile before booking appointment. Can't book without profile");
                                    break;
                                }
                                System.out.println("Enter specialty of doctor you want to visit:");
                                 Specialty special=Specialty.valueOf(sc.next().toUpperCase());
                                List<Doctor> doclist=null;
                                try {
                                    doclist = doctorService.getBySpecialty(special);


                                    for (Doctor doc : doclist) {
                                        System.out.println("Doctor{" +
                                                "id=" + doc.getId() +
                                                ", specialty=" + doc.getSpecialty() +
                                                ", experience=" + doc.getExperience() +
                                                ", qualification='" + doc.getQualification() +
                                                ", designation='" + doc.getDesignation());
                                    }
                                } catch (ResourceNotFoundException e) {
                                System.out.println(e.getMessage());
                                break;
                                      }
                                int docid;
                                while(true) {
                                    System.out.println("Enter doctor id of who you want to book appointment :");
                                    docid = sc.nextInt();
                                    try {
                                        doctorService.getById(docid);
                                        break;
                                    } catch (ResourceNotFoundException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                    List<Availability> availabilityList=availabilityService.getAllForMe(docid);
                                    for(Availability a:availabilityList){
                                        System.out.println("----Avalabilities of doctor with id "+docid+"------");
                                        System.out.println("Availability{" +
                                                "id=" + a.getId() +
                                                ", day=" + a.getDay() +
                                                ", startTime=" + a.getStartTime() +
                                                ", endTime=" + a.getEndTime() +
                                                ", Each Slot duration=" + a.getDuration() +
                                                '}');
                                    }
                                    LocalDate appointmentDate;
                                    while(true){
                                    System.out.println("Enter the date for appointment (YYYY-MM-DD):");
                                    appointmentDate=LocalDate.parse(sc.next());
                                    if(appointmentDate.isBefore(LocalDate.now())){
                                        System.out.println("Can't make appointment for past dates");
                                    }else{
                                        break;
                                    }

                                    }

                                    Day day=Day.valueOf(appointmentDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase());
                                    List<LocalTime> startTimingsOfAppointments=appointmentService.getAppointmentsStartTimeOnDate(appointmentDate, docid); // specific to that doctors
                                    List<Slot> availableSlots=availabilityService.listAvailableSlots(docid, day, startTimingsOfAppointments ); //doctor, date,day
                                    List<LocalTime> startTimingsOfAvailableSlots = new ArrayList<>();
                                    availableSlots.forEach(slot -> startTimingsOfAvailableSlots.add(slot.getStartTime()));
                                    LocalTime startTime;
                                    while(true) {
                                            System.out.println("-----------Available slots--------------");
                                            availableSlots.forEach(System.out::println);
                                            System.out.println("Enter start time of slot to book from the available slots (HH:MM):");
                                            startTime = LocalTime.parse(sc.next());
                                            if(appointmentDate.equals(LocalDate.now()) && startTime.isBefore(LocalTime.now())){

                                                System.out.println("Cannot book past slot");
                                                continue;
                                            }
                                            if (!startTimingsOfAvailableSlots.contains(startTime)) {
                                                System.out.println("that slot is not available. Kindly choose from available slots");
                                            }else{
                                                break;
                                            }
                                        }
                                    Appointment newappointment = new Appointment();
                                    newappointment.setDate(appointmentDate);
                                    try {
                                        newappointment.setDoctor(doctorService.getById(docid));
                                    } catch (ResourceNotFoundException e) {
                                        System.out.println("Error");
                                        break;
                                    }
                                    newappointment.setPatient(patient);
                                    newappointment.setStartTime(startTime);
                                    newappointment.setStatus(Status.PENDING);
                                    int slotDuration=0;
                                    for(Slot s:availableSlots){
                                        if(s.getStartTime().equals(startTime)){
                                            slotDuration=s.getDuration();
                                            break;
                                        }
                                    }
                                    newappointment.setEndTime(startTime.plusMinutes(slotDuration));
                                    System.out.println("Enter Reason for visit:");
                                    sc.nextLine();
                                    newappointment.setReason(sc.nextLine());;
                                    System.out.println("Enter symptoms:");
                                    newappointment.setSymptoms(sc.nextLine());

                                    appointmentService.addAppointment(newappointment);
                                System.out.println("Appointment added successfully");

                                break;
                            case 2:
                                System.out.println("All Appointments");
                                List<Appointment> appointments=appointmentService.getPatientAppointments(patient);
                                for (Appointment appointment1 : appointments) {
                                    System.out.println(appointment1.printForPatient());
                                }
                                break;
                            case 3:
                                System.out.println("Confirmed Appointments");
                                List<Appointment> appointments2=appointmentService.getPatientConfirmedAppointments(patient);
                                for (Appointment appointment1 : appointments2) {
                                    System.out.println(appointment1.printForPatient());
                                }
                                break;
                            case 4:
                                System.out.println("Enter id of appointment to cancel:");
                                try{
                                appointmentService.cancelAppointmentByPatient(patient.getId(), sc.nextInt());
                                System.out.println("Cancelled Successfully");}
                                catch (ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                    }
                    break;

                case 6:
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }


        }

    }

}