package com.amaze.service;

import com.amaze.dto.ChangePasswordReqDto;
import com.amaze.exception.UserEmailAlreadyExistsException;
import com.amaze.exception.UserNameExistsException;
import com.amaze.mapper.UserMapper;
import com.amaze.model.User;
import com.amaze.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    public void addUser(User user) {

        if(userRepository.existsByName(user.getName())){
            throw new UserNameExistsException("Username already exists");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserEmailAlreadyExistsException("Email already exists");
        }
        user.setPassword(   new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }


    public void update(String name, ChangePasswordReqDto dto) {
        User user=getByName(name);
        user.setPassword(   new BCryptPasswordEncoder().encode(userMapper.passwordDtoToString(dto)));
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   return getByName(username);
    }

    public User getByName(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
