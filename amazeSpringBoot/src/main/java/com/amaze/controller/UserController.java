package com.amaze.controller;

import com.amaze.exception.ResourceNotFoundException;
import com.amaze.model.User;
import com.amaze.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("api/user/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("api/user/add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping("api/user/get-one/{id}")
    public ResponseEntity<Object> getById(@PathVariable int id) {
        try {
            User user = userService.getById(id);
            return ResponseEntity.ok().body(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("api/user/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("api/user/update/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
