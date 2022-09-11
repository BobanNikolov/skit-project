package com.example.explore_buddy.controller;

import com.example.explore_buddy.config.RegistrationRequest;
import com.example.explore_buddy.config.RegistrationService;
import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.service.IUserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final IUserService userService;
  private final RegistrationService registrationService;

  @GetMapping
  public List<AppUser> getUsers() {
    return userService.findAll();
  }

  @GetMapping("/getUser")
  public AppUser getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList()).get(0).equals("ROLE_ANONYMOUS")) {
          return null;
      }
    return userService.findUserByEmail(authentication.getName());
  }

  @PostMapping("/registration")
  public String register(@RequestBody RegistrationRequest request) {
    return registrationService.register(request);
  }

  @GetMapping("/registration/confirm")
  public String confirm(@RequestParam("token") String token) {
    return registrationService.confirmToken(token);
  }

  @PostMapping("/registration/admin")
  public String registerAdmin(@RequestBody RegistrationRequest request) {
    return registrationService.register(request);
  }

  @GetMapping("/favourites")
  public List<Integer> getFavourites(@RequestParam String email) {
    return userService.getFavourites(email);
  }

  @PostMapping("/setFavourite/{id}")
  public boolean setFavourites(@RequestParam String email, @PathVariable Integer id) {
    return userService.changeFavourite(id, email);
  }
}
