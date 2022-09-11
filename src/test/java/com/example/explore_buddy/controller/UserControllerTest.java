package com.example.explore_buddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.explore_buddy.config.RegistrationRequest;
import com.example.explore_buddy.config.RegistrationService;
import com.example.explore_buddy.model.AppUser;
import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.LocationFullInfo;
import com.example.explore_buddy.model.enumeration.UserRole;
import com.example.explore_buddy.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.List;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private IUserService userService;

  @Mock
  private RegistrationService registrationService;

  @Test
  void testGetAllUsers() throws Exception {
    when(userService.findAll()).thenReturn(List.of());

    this.mockMvc.perform(get("/user"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetUser() throws Exception {
    when(userService.findUserByEmail(anyString())).thenReturn(new AppUser());

    this.mockMvc.perform(get("/user/getUser"))
        .andExpect(status().isOk());
  }

  @Test
  void testRegister() throws Exception {
    var registrationRequest = new RegistrationRequest("test", "test", UserRole.ROLE_USER);

    when(registrationService.register(any(RegistrationRequest.class))).thenReturn("test");

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(registrationRequest);

    this.mockMvc.perform(post("/user/registration").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void testConfirm() throws Exception {
    when(registrationService.confirmToken(anyString())).thenReturn("test");

    this.mockMvc.perform(get("/user/registration/confirm")
            .param("token", "test"))
        .andExpect(status().isOk());
  }

  @Test
  void testRegisterAdmin() throws Exception {
    var registrationRequest = new RegistrationRequest("test", "test", UserRole.ROLE_ADMIN);

    when(registrationService.register(any(RegistrationRequest.class))).thenReturn("test");

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(registrationRequest);

    this.mockMvc.perform(post("/user/registration/admin").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void testGetFavourites() throws Exception {
    when(userService.getFavourites(anyString())).thenReturn(List.of());

    this.mockMvc.perform(get("/user/favourites")
            .param("email", "test"))
        .andExpect(status().isOk());
  }

  @Test
  void testSetFavourites() throws Exception {
    when(userService.changeFavourite(anyInt(), anyString())).thenReturn(true);

    this.mockMvc.perform(post("/user/setFavourite/{id}", 1)
            .param("email", "test"))
        .andExpect(status().isOk());
  }
}