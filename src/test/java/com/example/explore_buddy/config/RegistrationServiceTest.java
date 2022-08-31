package com.example.explore_buddy.config;

import com.example.explore_buddy.config.email.EmailService;
import com.example.explore_buddy.config.email.EmailValidator;
import com.example.explore_buddy.config.email.Mail;
import com.example.explore_buddy.config.token.ConfirmationTokenService;
import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static support.TestObjectGenerator.*;

class RegistrationServiceTest {
  private RegistrationService instanceUnderTest;
  private UserService userService;
  private ConfirmationTokenService confirmationTokenService;
  private EmailValidator emailValidator;
  private EmailService emailService;

  @BeforeEach
  void init() {
    userService = mock(UserService.class);
    confirmationTokenService = mock(ConfirmationTokenService.class);
    emailValidator = mock(EmailValidator.class);
    emailService = mock(EmailService.class);
    instanceUnderTest = new RegistrationService(userService, confirmationTokenService, emailValidator, emailService);
  }

  @Test
  void testRegisterEmailNull() {
    var registrationRequest = generateRegistrationRequest();
    registrationRequest.setEmail(null);

    assertThrows(IllegalArgumentException.class, () -> {
      final var result = this.instanceUnderTest.register(registrationRequest);
    });
  }

  @Test
  void testRegisterEmailEmpty() {
    var registrationRequest = generateRegistrationRequest();
    registrationRequest.setEmail("");

    assertThrows(IllegalArgumentException.class, () -> {
      final var result = this.instanceUnderTest.register(registrationRequest);
    });
  }

  @Test
  void testRegisterEmailIsValidEmailFalse() {
    var registrationRequest = generateRegistrationRequest();

    when(emailValidator.test(anyString())).thenReturn(false);

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.register(registrationRequest);
    });
  }

  @Test
  void testRegisterEmailIsValidEmailTrueRoleNull() {
    var registrationRequest = generateRegistrationRequest();
    registrationRequest.setRole(null);
    String token = "token";

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userService.signUpUser(any(AppUser.class))).thenReturn(token);

    final var result = this.instanceUnderTest.register(registrationRequest);

    assertNotNull(result);
    verify(emailValidator).test(anyString());
    verify(userService).signUpUser(any(AppUser.class));
    verify(emailService).sendEmail(any(Mail.class));
  }

  @Test
  void testRegisterEmailIsValidEmailTrueRoleNotNull() {
    var registrationRequest = generateRegistrationRequest();
    String token = "token";

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userService.signUpUser(any(AppUser.class))).thenReturn(token);

    final var result = this.instanceUnderTest.register(registrationRequest);

    assertNotNull(result);
    verify(emailValidator).test(anyString());
    verify(userService).signUpUser(any(AppUser.class));
    verify(emailService).sendEmail(any(Mail.class));
  }

  @Test
  void testRegisterEmailIsValidEmailTruePasswordNull() {
    var registrationRequest = generateRegistrationRequest();
    registrationRequest.setPassword(null);
    String token = "token";

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userService.signUpUser(any(AppUser.class))).thenReturn(token);

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.register(registrationRequest);
    });
  }

  @Test
  void testRegisterEmailIsValidEmailTruePasswordEmpty() {
    var registrationRequest = generateRegistrationRequest();
    registrationRequest.setPassword("");
    String token = "token";

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userService.signUpUser(any(AppUser.class))).thenReturn(token);

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.register(registrationRequest);
    });
  }

  @Test
  void testConfirmationTokenConfirmedAtNotNull() {
    var confirmationToken = generateConfirmationToken();
    String token = "token";

    when(confirmationTokenService.getToken(anyString())).thenReturn(java.util.Optional.of(confirmationToken));

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.confirmToken(token);
    });
  }

  @Test
  void testConfirmationTokenElseThrow() {
    String token = "token";

    when(confirmationTokenService.getToken(anyString())).thenThrow(IllegalStateException.class);

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.confirmToken(token);
    });
  }

  @Test
  void testConfirmationTokenCExpiredAtBeforeNow() {
    var confirmationToken = generateConfirmationToken();
    confirmationToken.setExpiresAt(LocalDateTime.now().minusDays(1));
    String token = "token";

    when(confirmationTokenService.getToken(anyString())).thenReturn(java.util.Optional.of(confirmationToken));

    assertThrows(IllegalStateException.class, () -> {
      final var result = this.instanceUnderTest.confirmToken(token);
    });
  }


  @Test
  void testConfirmationToken() {
    var confirmationToken = generateConfirmationToken();
    confirmationToken.setExpiresAt(LocalDateTime.now().plusDays(1));
    confirmationToken.setConfirmedAt(null);
    var user = generateAppUser();
    confirmationToken.setUser(user);
    String token = "token";

    when(confirmationTokenService.getToken(anyString())).thenReturn(java.util.Optional.of(confirmationToken));

    final var result = this.instanceUnderTest.confirmToken(token);

    assertEquals("confirmed", result);
    verify(userService).enableUser(anyString());
  }
}