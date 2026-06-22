package com.amaze.controller;

import com.amaze.dto.ChangePasswordReqDto;
import com.amaze.dto.LoginResDto;
import com.amaze.dto.TokenDto;
import com.amaze.model.User;
import com.amaze.service.UserService;
import com.amaze.utility.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private UserService userService;
    private JwtUtility jwtUtility;


    @PutMapping("/update")
    public ResponseEntity<Object> updatePassword( @Valid @RequestBody ChangePasswordReqDto dto, Principal principal) {
            userService.update(principal.getName(), dto);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public TokenDto login(Principal principal){
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username,token);
    }

    @GetMapping("/user-details")
    public LoginResDto getUserDetails(Principal principal){
        User user = (User)userService.loadUserByUsername(principal.getName());
        return new LoginResDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }


}
