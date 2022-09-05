package com.example.explore_buddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class LocationsControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testGetLocations() throws Exception {
    this.mockMvc.perform(get("/home")).andExpect(status().isOk());
  }

  @Test
  void testPostLocations() throws Exception {
    File f = new File("src/test/resources/waterfall.csv");
    MockMultipartFile file = new MockMultipartFile("file", "waterfall.csv", MediaType.TEXT_PLAIN_VALUE, new FileInputStream(f));

    this.mockMvc.perform(multipart("/home/importCsv").file(file)).andExpect(status().isOk());
  }

}