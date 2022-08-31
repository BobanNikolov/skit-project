package com.example.explore_buddy.service;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static support.TestObjectGenerator.generateAppUser;
import static support.TestObjectGenerator.generateLocation;

import com.example.explore_buddy.config.email.EmailValidator;
import com.example.explore_buddy.config.token.ConfirmationToken;
import com.example.explore_buddy.config.token.ConfirmationTokenRepository;
import com.example.explore_buddy.config.token.ConfirmationTokenService;
import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.repository.IUserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {
  private IUserRepository userRepository;
  private ILocationsRepository locationsRepository;
  private ConfirmationTokenService tokenService;
  private PasswordEncoder passwordEncoder;
  private EmailValidator emailValidator;
  private UserService instanceUnderTest;

  @BeforeEach
  void init() {
    userRepository = mock(IUserRepository.class);
    locationsRepository = mock(ILocationsRepository.class);
    tokenService = mock(ConfirmationTokenService.class);
    passwordEncoder = mock(PasswordEncoder.class);
    emailValidator = mock(EmailValidator.class);
    instanceUnderTest =
        new UserService(userRepository, locationsRepository, tokenService,
            passwordEncoder, emailValidator);

  }

  @Test
  void testGetFavouritesEmailNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.getFavourites(null);
    });
  }

  @Test
  void testGetFavouritesEmailEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.getFavourites("");
    });
  }

  @Test
  void testGetFavouritesEmailValidatorFalse() {
    when(emailValidator.test(anyString())).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.getFavourites("test");
    });
  }

  @Test
  void testGetFavourites() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userRepository.findUserByEmail(anyString())).thenReturn(user);

    final var result = this.instanceUnderTest.getFavourites(user.getEmail());

    assertNotNull(result);
    assertEquals(2, result.size());
  }

  @Test
  void testChangeFavouritesEmailNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.changeFavourite(1, null);
    });
  }

  @Test
  void testChangeFavouritesEmailEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.changeFavourite(1, "");
    });
  }

  @Test
  void testChangeFavouritesEmailValidatorFalse() {
    when(emailValidator.test(anyString())).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.changeFavourite(1, "test");
    });
  }

  @Test
  void testChangeFavouriteFalse() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    when(emailValidator.test(anyString())).thenReturn(true);
    when(locationsRepository.getById(anyInt())).thenReturn(location1);
    when(userRepository.findUserByEmail(anyString())).thenReturn(user);

    final var result = this.instanceUnderTest.changeFavourite(1, user.getEmail());

    assertFalse(result);
  }

  @Test
  void testChangeFavouriteTrue() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    var location3 = generateLocation();
    location3.setId(3);

    when(emailValidator.test(anyString())).thenReturn(true);
    when(locationsRepository.getById(anyInt())).thenReturn(location3);
    when(userRepository.findUserByEmail(anyString())).thenReturn(user);

    final var result = this.instanceUnderTest.changeFavourite(3, user.getEmail());

    assertTrue(result);
  }

  @Test
  void testFindAll() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    var users = List.of(user);

    when(userRepository.findAll()).thenReturn(users);

    final var result = this.instanceUnderTest.findAll();

    assertNotNull(result);
    verify(userRepository).findAll();
  }

  @Test
  void testEnableUser() {
    when(userRepository.enableUser(anyString())).thenReturn(1);

    final var result = this.instanceUnderTest.enableUser("test");

    assertEquals(1, result);
    verify(userRepository).enableUser(anyString());
  }

  @Test
  void testLoadUserByUsername() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    when(userRepository.findUserByEmail(anyString())).thenReturn(user);

    final var result = this.instanceUnderTest.loadUserByUsername("test");

    assertNotNull(result);
  }

  @Test
  void testLoadUserByUsernameError() {
    final var result = this.instanceUnderTest.loadUserByUsername(randomUUID().toString());

    assertNull(result);
  }

  @Test
  void testSignUpUserEmailNull() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));
    user.setEmail(null);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.signUpUser(user);
    });
  }

  @Test
  void testSignUpUserEmailEmpty() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));
    user.setEmail("");

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.signUpUser(user);
    });
  }

  @Test
  void testSignUpUserEmailTestFalse() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    when(emailValidator.test(anyString())).thenReturn(false);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.signUpUser(user);
    });
  }

  @Test
  void testSignUpUserEmailIdNull() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userRepository.findUserByEmail(anyString())).thenReturn(user);

    assertThrows(IllegalStateException.class, () -> {
      this.instanceUnderTest.signUpUser(user);
    });
  }

  @Test
  void testSignUpUserPasswordNull() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));
    user.setPassword(null);

    when(emailValidator.test(anyString())).thenReturn(true);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.signUpUser(user);
    });
  }

  @Test
  void testSignUpUserPasswordEmpty() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));
    user.setPassword("");

    when(emailValidator.test(anyString())).thenReturn(true);

    assertThrows(IllegalArgumentException.class, () -> {
      this.instanceUnderTest.signUpUser(user);
    });
  }

  @Test
  void testSignUpUser() {
    var user = generateAppUser();
    var location1 = generateLocation();
    location1.setId(1);
    var location2 = generateLocation();
    location2.setId(2);
    user.setFavouriteLocations(List.of(location1, location2));

    String encodedPass = randomUUID().toString();

    when(emailValidator.test(anyString())).thenReturn(true);
    when(userRepository.findUserByEmail(anyString())).thenReturn(null);
    when(passwordEncoder.encode(anyString())).thenReturn(encodedPass);

    final var result = this.instanceUnderTest.signUpUser(user);

    assertNotNull(result);
    verify(emailValidator).test(anyString());
    verify(userRepository).findUserByEmail(anyString());
    verify(passwordEncoder).encode(anyString());
    verify(userRepository).save(any(AppUser.class));
    verify(tokenService).saveConfirmationToken(any(ConfirmationToken.class));
  }

}


