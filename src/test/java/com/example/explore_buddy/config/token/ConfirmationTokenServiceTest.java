package com.example.explore_buddy.config.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static support.TestObjectGenerator.generateAppUser;
import static support.TestObjectGenerator.generateConfirmationToken;

class ConfirmationTokenServiceTest {
  private ConfirmationTokenRepository repository;
  private ConfirmationTokenService instanceUnderTest;

  @BeforeEach
  void init() {
    repository = mock(ConfirmationTokenRepository.class);
    instanceUnderTest = new ConfirmationTokenService(repository);
  }

  @Test
  void testSaveConfirmationToken() {
    var confirmationToken = generateConfirmationToken();
    var user = generateAppUser();
    confirmationToken.setUser(user);
    confirmationToken.setId(1);

    this.instanceUnderTest.saveConfirmationToken(confirmationToken);

    verify(repository).save(any(ConfirmationToken.class));
  }

  @Test
  void testGetToken() {
    var confirmationToken = generateConfirmationToken();
    var user = generateAppUser();
    confirmationToken.setUser(user);
    confirmationToken.setId(1);

    when(repository.findByToken(anyString())).thenReturn(java.util.Optional.of(confirmationToken));

    final var result = this.instanceUnderTest.getToken("token");

    assertNotNull(result);
    verify(repository).findByToken(anyString());
  }

  @Test
  void testDeleteConfirmationToken() {
    var confirmationToken = generateConfirmationToken();
    var user = generateAppUser();
    confirmationToken.setUser(user);
    confirmationToken.setId(1);

    this.instanceUnderTest.deleteByUserMail("test");

    verify(repository).deleteByUser_Email(anyString());
  }

  @Test
  void testSetConfirmedAt() {
    var confirmationToken = generateConfirmationToken();
    var user = generateAppUser();
    confirmationToken.setUser(user);
    confirmationToken.setId(1);

    when(repository.updateConfirmedAt(anyString(), any(LocalDateTime.class))).thenReturn(1);

    final var result = this.instanceUnderTest.setConfirmedAt("token");

    assertEquals(1, result);
    verify(repository).updateConfirmedAt(anyString(), any(LocalDateTime.class));
  }
}