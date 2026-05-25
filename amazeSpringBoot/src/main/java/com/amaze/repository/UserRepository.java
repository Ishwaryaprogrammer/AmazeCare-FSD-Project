package com.amaze.repository;

import com.amaze.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer>{
}
