package com.amaze.config;

import com.amaze.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private static final String DOCTOR = "DOCTOR";
    private static final String PATIENT = "PATIENT";
    private static final String ADMIN = "ADMIN";

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/patient/register").permitAll() //frontend in register page, only patient role
                        .requestMatchers(HttpMethod.GET, "/api/auth/login").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/auth/user-details").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/auth/update").hasAnyAuthority(DOCTOR, PATIENT)

                        .requestMatchers(HttpMethod.POST, "/api/doctor/add").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/doctor/all").hasAnyAuthority(ADMIN, PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/doctor/all-by-specialty").hasAnyAuthority(ADMIN, PATIENT)
                        .requestMatchers(HttpMethod.PUT, "/api/doctor//update/{id}").hasAuthority(ADMIN)

                        .requestMatchers(HttpMethod.GET,"/api/patient/view-profile").hasAuthority(PATIENT)

                        .requestMatchers(HttpMethod.POST,"/api/avail/add").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.PUT,"/api/avail/update/{id}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.DELETE,"/api/avail/delete/{id}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET, "/api/doctor/view-profile").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/avail/view-availabilities").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/avail/view-availabilities/{docId}").hasAnyAuthority(PATIENT, ADMIN)
                        .requestMatchers(HttpMethod.GET,"/api/avail/view-availabilities-by-day").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/avail/view-availabilities-by-day/{docId}").hasAnyAuthority(PATIENT, ADMIN)


                        .requestMatchers(HttpMethod.GET,"/api/appointment/book-step1/{docId}").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.POST,"/api/appointment/book-step2/{docId}").hasAuthority(PATIENT)

                        .requestMatchers(HttpMethod.GET,"/api/appointment/patient/all-appointments").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.GET,"/api/appointment/patient/all-appointments-by-status").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.GET, "/api/report/patient/stat").hasAuthority(PATIENT)

                        .requestMatchers(HttpMethod.GET,"/api/appointment/doctor/all-upcoming-appointments").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.PUT,"/api/appointment/doctor/past-appointments-pending-or-confirmed-cancelled").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/appointment/doctor/all-appointments-by-status").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET, "/api/appointment/doctor/stat").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET, "/api/appointment/doctor/patient-details/{appId}").hasAuthority(DOCTOR)

                        .requestMatchers(HttpMethod.GET,"/api/appointment/admin/all-upcoming-appointments/{docId}").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.PUT,"/api/appointment/admin/past-appointments-pending-or-confirmed-cancelled/{docId}").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.GET,"/api/appointment/admin/all-appointments-by-status/{docId}").hasAuthority(ADMIN)

                        .requestMatchers(HttpMethod.PUT,"/api/appointment/confirm-appointment/{appId}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.PUT,"/api/appointment/patient-cancel-appointment/{appId}").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.PUT,"/api/appointment/doctor-cancel-appointment/{appId}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.PUT,"/api/appointment/admin-cancel-appointment/{appId}/{docId}").hasAuthority(ADMIN)

                        .requestMatchers(HttpMethod.POST,"/api/consultation/add/{appId}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/consultation/get-one/{appId}").hasAnyAuthority(PATIENT, DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/consultation/all").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.GET,"/api/consultation/all/{appId}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/consultation/all-by-specialty").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.GET,"/api/consultation/all-y-specialty/{appId}").hasAuthority(DOCTOR)

                        .requestMatchers(HttpMethod.POST,"/api/report/add").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.PUT,"/api/report/update/{id}").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.DELETE,"/api/report/delete/{id}").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.GET,"/api/report/all").hasAuthority(PATIENT)
                        .requestMatchers(HttpMethod.GET,"/api/report/all/{appId}").hasAuthority(DOCTOR)
                        .requestMatchers(HttpMethod.GET,"/api/report/path/{id}").hasAnyAuthority(DOCTOR, PATIENT)

                        .requestMatchers(HttpMethod.GET,"/api/admin/stat").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.GET,"/api/admin/graph1").hasAuthority(ADMIN)
                        .requestMatchers(HttpMethod.GET,"/api/admin/graph2").hasAuthority(ADMIN)

                        .requestMatchers(HttpMethod.GET,"/api/doctor/get-specialties").permitAll()







                        .anyRequest().authenticated()

                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
