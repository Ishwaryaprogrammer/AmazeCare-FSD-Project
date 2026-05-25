package com.amaze.service;

import com.amaze.exception.ResourceNotFoundException;
import com.amaze.model.User;
import com.amaze.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Invalid user id"));

    }

    public void delete(int id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public void update(int id, User newuser) {
        User olduser=getById(id);
        olduser.setName(newuser.getName());
        olduser.setEmail(newuser.getEmail());
        olduser.setPassword(newuser.getPassword());
        olduser.setRole(newuser.getRole());
        userRepository.save(olduser);


    }
}
