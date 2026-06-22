package com.amaze.repository;

import com.amaze.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByName(String username);


    boolean existsByName(@NotBlank(message = "Name cannot be blank") @NotNull(message = "Name cannot be empty") @Size(max=20,message = "Exceeded Maximum Length") String name);

    boolean existsByEmail(@NotBlank(message = "Mail cannot be blank") @NotNull(message = "Mail cannot be empty") @Size(max=35,message = "Exceeded Maximum Length") String email);
}
