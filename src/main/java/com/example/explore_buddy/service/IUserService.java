package com.example.explore_buddy.service;

import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.AppUser;

import java.util.List;

public interface IUserService {
    AppUser findUserByEmail(String email);
    List<Integer> getFavourites(String email);
    boolean changeFavourite(Integer id,String email);
    String signUpUser(AppUser appUser);
    List<AppUser> findAll();
}
