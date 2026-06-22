package com.amaze.mapper;

import com.amaze.dto.ChangePasswordReqDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public String passwordDtoToString(ChangePasswordReqDto dto){
        return dto.password();
    }

}
