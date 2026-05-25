package com.app.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.app")
public class AppConfig {

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/amazecare");
        dataSource.setUsername("root");
        dataSource.setPassword("Kalaivani@123");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManager(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean emf =new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);

        emf.setPackagesToScan("com.app.model");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties properties=new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        emf.setJpaProperties(properties);
        return emf;

    }

    @Bean
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);

    }
}
