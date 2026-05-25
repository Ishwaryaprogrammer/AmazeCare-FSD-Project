package com.app;

import com.app.config.AppConfig;
import com.app.dao.DoctorDao;
import com.app.dao_impl.DoctorDaoImpl;
import com.app.model.Doctor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(AppConfig.class);

        DoctorDao doctorDao=context.getBean(DoctorDaoImpl.class);
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("1. Insert the doctor");
            System.out.println("2. Delete the doctor");
            System.out.println("3. View all doctor");
            System.out.println("4. search doctor by id");
            System.out.println("5. update doctor");
            System.out.println("0. exit");
            int op=sc.nextInt();
            if(op==0){
                break;
            }
            switch(op){
                case 1:
                    System.out.println("Enter name:");
                    String name=sc.next();
                    System.out.println("Enter specialty:");
                    String specialty=sc.next();
                    System.out.println("Enter experience:");
                    int experience=sc.nextInt();
                    System.out.println("Enter qualification:");
                    String qualification=sc.next();
                    System.out.println("Enter designation:");
                    String designation=sc.next();

                    doctorDao.insert(new Doctor(name,specialty,experience, qualification, designation));
                    System.out.println("Inserted successfully");

                    break;
                case 2:
                    System.out.println("Enter id to delete:");
                    int id=sc.nextInt();
                    try {
                        doctorDao.deleteById(id);
                        System.out.println("Deleted Successfully");
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 3:
                    System.out.println("-----Doctors------");
                    doctorDao.getAll().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Enter id to search doctor:");
                    id=sc.nextInt();
                    try{
                        System.out.println(doctorDao.getById(id));

                    }
                    catch (EmptyResultDataAccessException e){
                        System.out.println("Invalid id");
                    }

                    break;
                case 5:
                    System.out.println("Enter id to update doctor:");
                    id=sc.nextInt();
                    System.out.println("-----Existing information----");
                    try{
                        System.out.println(doctorDao.getById(id));

                    }
                    catch (EmptyResultDataAccessException e){
                        System.out.println("Invalid id");
                        break;
                    }
                    System.out.println("----------------------------");
                    System.out.println("Enter name:");
                    name=sc.next();
                    System.out.println("Enter specialty:");
                    specialty=sc.next();
                    System.out.println("Enter experience:");
                    experience=sc.nextInt();
                    System.out.println("Enter qualification:");
                    qualification=sc.next();
                    System.out.println("Enter designation:");
                    designation=sc.next();
                    doctorDao.update(id,new Doctor(name,specialty,experience, qualification, designation));
                    System.out.println("Updated Successfully");
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }


    }
}
