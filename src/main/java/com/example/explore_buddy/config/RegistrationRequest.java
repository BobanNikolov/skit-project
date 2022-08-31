package com.example.explore_buddy.config;

import com.example.explore_buddy.model.enumeration.UserRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RegistrationRequest {

    private  String email;
    private  String password;
    private  UserRole role;
}