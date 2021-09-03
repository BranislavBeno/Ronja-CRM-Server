package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RepresentativeController.class)
class RepresentativeControllerTest {

  @MockBean
  EntityService<Representative, RepresentativeDto> service;
  @Autowired
  MockMvc mockMvc;

  @Test
  void testShouldNotReturnXML() throws Exception {
    this.mockMvc
        .perform(get("/representatives/list")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
        .andExpect(status().isNotAcceptable());
  }

  @Test
  void testFindAll() throws Exception {
    when(service.findAll()).thenReturn(List.of(new Representative(), new Representative()));
    this.mockMvc
        .perform(get("/representatives/list")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", is(2)))
        .andDo(print())
        .andReturn();
    verify(service).findAll();
  }

  @Test
  void testSearch() throws Exception {
    when(service.searchBy(anyString())).thenReturn(List.of(new Representative()));
    this.mockMvc
        .perform(get("/representatives/search?lastName=Baumann")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(1)));
    verify(service).searchBy(anyString());
  }

  @Test
  void testSave() throws Exception {
    when(service.add(any(RepresentativeDto.class))).thenReturn(new Representative());
    this.mockMvc
        .perform(post("/representatives/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "firstName": "Roger",
                    "lastName": "Patrick",
                    "position": "CTO",
                    "region": "ASEAN",
                    "notice": "anything special",
                    "status": "ACTIVE",
                    "lastVisit": "2021-01-17",
                    "scheduledVisit": "2021-05-05"
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Content-Type"))
        .andExpect(header().string("Content-Type", Matchers.equalTo("application/json")));
    verify(service).add(any(RepresentativeDto.class));
  }

  @Test
  void testUpdate() throws Exception {
    when(service.update(any(RepresentativeDto.class))).thenReturn(new Representative());
    this.mockMvc
        .perform(post("/representatives/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "id": 3,
                    "firstName": "Roger",
                    "lastName": "Patrick",
                    "position": "CTO",
                    "region": "ASEAN",
                    "notice": "anything special",
                    "status": "ACTIVE",
                    "lastVisit": "2021-01-17",
                    "scheduledVisit": "2021-05-05",
                    "phoneNumbers": [
                        "+555987654321"
                    ],
                    "emails": [
                        "patrick@foo.com"
                    ],
                    "customer": {
                        "companyName": "IdaCorp",
                        "category": "LEVEL_3",
                        "focus": "TRADE",
                        "status": "ACTIVE"
                    }
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Content-Type"))
        .andExpect(header().string("Content-Type", Matchers.equalTo("application/json")));
    verify(service).update(any(RepresentativeDto.class));
  }

  @Test
  void testDelete() throws Exception {
    service.deleteById(anyInt());
    this.mockMvc
        .perform(delete("/representatives/delete/1"))
        .andExpect(status().is(204));
    verify(service, times(2)).deleteById(anyInt());
  }
}