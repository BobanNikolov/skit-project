package com.example.explore_buddy.config;

import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public LoginService(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public AppUser login(LoginRequest loginRequest){
        AppUser user=userService.findUserByEmail(loginRequest.getEmail());
        if(user!=null){
            if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword()))
                throw new IllegalStateException("gresen pass");
            return user;
        }
        else{
            throw new IllegalStateException("acc doesnt exist");
        }
    }
}
