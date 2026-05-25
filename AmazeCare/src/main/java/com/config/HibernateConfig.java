package com.config;

import com.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null){
            Configuration configuration=new Configuration();
            configuration.setProperty("hibernate.connection.url","jdbc:mysql://localhost:3306/amazecare?createDatabaseIfNotExist=true");
            configuration.setProperty("hibernate.connection.username","root");
            configuration.setProperty("hibernate.connection.password","Kalaivani@123");
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

            configuration.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");

            configuration.setProperty("hibernate.hbm2ddl.auto","update");
            // need to add model class
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Doctor.class);
            configuration.addAnnotatedClass(Availability.class);
            configuration.addAnnotatedClass(Patient.class);
            configuration.addAnnotatedClass(Appointment.class);
            configuration.addAnnotatedClass(Consultation.class);
            sessionFactory = configuration.buildSessionFactory();
        }


        return sessionFactory;
    }
    public void close(){
        sessionFactory.close();
    }
}
