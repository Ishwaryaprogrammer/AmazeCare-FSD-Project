package com.hiretrack.mapper;

import com.hiretrack.dto.RegisterReqDto;
import com.hiretrack.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerDtoToEntity(RegisterReqDto dto){
        User user=new User();
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setRole(dto.role());
        return user;
    }



}
