package com.example.explore_buddy.controller;

import com.example.explore_buddy.model.Location;
import com.example.explore_buddy.model.LocationFullInfo;
import com.example.explore_buddy.model.enumeration.LocationType;
import com.example.explore_buddy.repository.ILocationsRepository;
import com.example.explore_buddy.service.ILocationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LocationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private ILocationsService locationsService;

  @Autowired
  private ILocationsRepository locationsRepository;

  @Test
  void testGetLocations() throws Exception {
    this.mockMvc.perform(get("/home")).andExpect(status().isOk());
  }

  @Test
  void testPostLocations() throws Exception {
    File f = new File("src/test/resources/waterfall.csv");
    MockMultipartFile file =
        new MockMultipartFile("file", "waterfall.csv", "text/csv",
            new FileInputStream(f));

    this.mockMvc.perform(multipart("/home/importCsv").file(file)).andExpect(status().isOk());
  }

  @Test
  void testPostLocationsNonCsv() throws Exception {
    File f = new File("src/test/resources/waterfall.csv");
    MockMultipartFile file =
        new MockMultipartFile("file", "waterfall.csv", "text/plain",
            new FileInputStream(f));

    this.mockMvc.perform(multipart("/home/importCsv").file(file)).andExpect(status().isOk());
  }

  @Test
  void testGetMarkersNullLocationType() throws Exception {
    this.mockMvc.perform(get("/home/markers")
            .param("searchText", "test")
            .param("isFavourite", "true"))
        .andExpect(status().isOk());
  }

  @Test
  void testGetMarkers() throws Exception {
    this.mockMvc.perform(get("/home/markers")
            .param("searchText", "test")
            .param("locationTypeString", "SPRING,LAKE")
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

    var location = new Location("test", 24.2, 44.5, "test", LocationType.SPRING);
    locationsRepository.save(location);

    this.mockMvc.perform(post("/home/delete/{id}", location.getId()))
        .andExpect(status().isOk());
  }

  @Test
  void testUpdateLocation() throws Exception {
    var location = new Location("test", 24.2, 44.5, "test", LocationType.SPRING);
    locationsRepository.save(location);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson=ow.writeValueAsString(location);

    this.mockMvc.perform(post("/home/update").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(requestJson))
        .andExpect(status().isOk());
  }
}