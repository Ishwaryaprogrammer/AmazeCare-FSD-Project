package com.app;

import com.app.config.AppConfig;
import com.app.dao.AvailabilityDao;
import com.app.dao.UserDao;
import com.app.enums.Day;
import com.app.enums.Role;
import com.app.exception.TimeOverlapException;
import com.app.model.Availability;
import com.app.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalTime;
import java.util.Scanner;


public class App{

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);
        Scanner sc = new Scanner(System.in);
        UserDao userDao=context.getBean(UserDao.class);
        while (true) {
            System.out.println("WELCOME TO AMAZECARE");
            System.out.println("1. Login");
            System.out.println("0. Exit");

            int op = sc.nextInt();
            if (op == 0) {
                break;
            }
            switch (op) {
                case 1:
                    System.out.println("Enter email");
                    String mail = sc.next();
                    System.out.println("Enter password");
                    String pass = sc.next();
                    User user=null;
                    try {

                         user = userDao.login(mail, pass);
                        System.out.println("LoggedIn Successfully");
                    } catch (NoResultException e) {
                        System.out.println("Invalid Credentials");
                        break;
                    }
                        if (user.getRole() == Role.DOCTOR) {

                            AvailabilityDao availabilityDao=context.getBean(AvailabilityDao.class);
                            while (true) {
                                System.out.println("1. Add availability");
                                System.out.println("2. Update availability");
                                System.out.println("3. delete availability");
                                System.out.println("4. View availability");
                                System.out.println("0. Go to main menu");
                                op = sc.nextInt();
                                if (op == 0) {
                                    break;
                                }
                                switch (op) {
                                    case 1:
                                        Availability availability=new Availability();
                                        System.out.println("Enter the day:");
                                        availability.setDay(Day.valueOf(sc.next().toUpperCase()));
                                        System.out.println("Enter start time (HH:MM):");
                                        availability.setStartTime(LocalTime.parse(sc.next()));
                                        System.out.println("Enter End time (HH:MM):");
                                        availability.setEndTime(LocalTime.parse(sc.next()));
                                        System.out.println("Enter duration of one slot:");
                                        availability.setDuration(sc.nextInt());

                                        try {
                                            availabilityDao.add(availability, mail);
                                            System.out.println("Availability added Successfully");
                                        }catch (TimeOverlapException e){
                                            System.out.println(e.getMessage());
                                        }

                                        break;
                                    case 2:
                                        System.out.println("Enter the id of availability to update:");
                                        int id= sc.nextInt();
                                        System.out.println("----------Existing------");
                                        try{
                                            Availability availability2 =availabilityDao.getById(id, mail);
                                            System.out.println(availability2);
                                            System.out.println("Enter the day:");
                                            availability2.setDay(Day.valueOf(sc.next().toUpperCase()));
                                            System.out.println("Enter start time (HH:MM):");
                                            availability2.setStartTime(LocalTime.parse(sc.next()));
                                            System.out.println("Enter End time (HH:MM):");
                                            availability2.setEndTime(LocalTime.parse(sc.next()));
                                            System.out.println("Enter duration of one slot:");
                                            availability2.setDuration(sc.nextInt());
                                            availabilityDao.updateAvailability(availability2,mail);
                                            System.out.println("Availability updated Successfully");
                                        }
                                        catch (TimeOverlapException e){
                                            System.out.println(e.getMessage());
                                        }
                                        catch (Exception e){
                                            System.out.println("Availability id not found");
                                            break;
                                        }



                                        break;
                                    case 3:
                                        System.out.println("Enter the id of availability to delete:");
                                        int id2= sc.nextInt();
                                        try {
                                            availabilityDao.getById(id2,mail);
                                        }catch (Exception e){
                                            System.out.println("Availability id not found");
                                            break;
                                        }
                                        availabilityDao.delete(id2);
                                        System.out.println("Deleted successfully");

                                        break;
                                    case 4:
                                        System.out.println("--------All availabilities---------");
                                        availabilityDao.getAllForMe(mail).forEach(System.out::println);
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                        break;
                                }

                            }



                        } else {
                            System.out.println("Not implemented for your role");
                        }

                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }

        }

        sc.close();
    }



}