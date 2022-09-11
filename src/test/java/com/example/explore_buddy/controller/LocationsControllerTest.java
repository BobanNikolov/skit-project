package com.example.explore_buddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.LocationFullInfo;
import com.example.explore_buddy.model.enumeration.LocationType;
import com.example.explore_buddy.service.ILocationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LocationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private ILocationsService locationsService;

  @Test
  void testGetLocations() throws Exception {
    this.mockMvc.perform(get("/home")).andExpect(status().isOk());
  }

  @Test
  void testPostLocations() throws Exception {
    File f = new File("src/test/resources/waterfall.csv");
    MockMultipartFile file =
        new MockMultipartFile("file", "waterfall.csv", MediaType.TEXT_PLAIN_VALUE,
            new FileInputStream(f));

    this.mockMvc.perform(multipart("/home/importCsv").file(file)).andExpect(status().isOk());
  }

  @Test
  void testGetMarkersNullLocationType() throws Exception {
    when(locationsService.getMarkers(any(), any(), any())).thenReturn(List.of());
    this.mockMvc.perform(get("/home/markers")
            .param("searchText", "test")
            .param("isFavourite", "true"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetMarkers() throws Exception {
    when(locationsService.getMarkers(any(), any(), any())).thenReturn(List.of());

    this.mockMvc.perform(get("/home/markers")
            .param("searchText", "test")
            .param("locationTypes", "SPRING, LAKE")
            .param("isFavourite", "true"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetLocation() throws Exception {
    when(locationsService.getLocation(anyInt())).thenReturn(
        new LocationFullInfo(new Location(), true));

    this.mockMvc.perform(get("/home/getLocation")
            .param("id", "1"))
        .andExpect(status().isOk());
  }

  @Test
  void testPostLocation () throws Exception {
    var location = new Location("test", 24.2, 44.5, "test", LocationType.SPRING);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson=ow.writeValueAsString(location);

    this.mockMvc.perform(post("/home/add").contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestJson))
        .andExpect(status().isOk());
  }

  @Test
  void testDeleteLocation() throws Exception {
    doNothing().when(locationsService).deleteLocationById(isA(Integer.class));

    this.mockMvc.perform(post("/home/delete/{id}", 1))
        .andExpect(status().isOk());
  }

  @Test
  void testUpdateLocation() throws Exception {
    var location = new Location("test", 24.2, 44.5, "test", LocationType.SPRING);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson=ow.writeValueAsString(location);

    this.mockMvc.perform(post("/home/update").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestJson))
        .andExpect(status().isOk());
  }
}