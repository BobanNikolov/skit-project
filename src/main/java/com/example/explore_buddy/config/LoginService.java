package com.example.explore_buddy.config;

import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

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
