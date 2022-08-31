package com.example.explore_buddy.config;

import com.example.explore_buddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static support.TestObjectGenerator.generateAppUser;
import static support.TestObjectGenerator.generateLoginRequest;

class LoginServiceTest {
  private PasswordEncoder passwordEncoder;
  private UserService userService;
  private LoginService instanceUnderTest;

  @BeforeEach
  void init() {
    passwordEncoder = mock(PasswordEncoder.class);
    userService = mock(UserService.class);
    instanceUnderTest = new LoginService(passwordEncoder, userService);
  }

  @Test
  void testLoginUserNull() {
    var loginRequest = generateLoginRequest();

    when(userService.findUserByEmail(anyString())).thenReturn(null);

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.login(loginRequest);
    });
  }

  @Test
  void testLoginUserMatchesFalse() {
    var loginRequest = generateLoginRequest();
    var user = generateAppUser();

    when(userService.findUserByEmail(anyString())).thenReturn(user);
    when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(false);

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.login(loginRequest);
    });
  }

  @Test
  void testLoginUserMatchesTrue() {
    var loginRequest = generateLoginRequest();
    var user = generateAppUser();

    when(userService.findUserByEmail(anyString())).thenReturn(user);
    when(passwordEncoder.matches(any(CharSequence.class), anyString())).thenReturn(true);

    final var result = this.instanceUnderTest.login(loginRequest);

    assertNotNull(result);
  }

}