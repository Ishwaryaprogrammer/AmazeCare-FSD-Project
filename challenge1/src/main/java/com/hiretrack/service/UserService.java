package com.hiretrack.service;

import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.mapper.UserMapper;
import com.hiretrack.model.User;
import com.hiretrack.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserMapper userMapper;


    public void addUser(RegisterReqDto dto) {
        User user=userMapper.registerDtoToEntity(dto);
        user.setPassword(   new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return getByName(username);
    }

    public User getByName(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));

    }


}
