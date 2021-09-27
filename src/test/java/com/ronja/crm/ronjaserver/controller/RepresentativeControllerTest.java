package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.dto.RepresentativeMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
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

  private static final String ADD_BODY_FULL = """
      {
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
          "customerId": 1
      }""";

  private static final String UPDATE_BODY_FULL = """
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
          "customerId": 1
      }""";

  private static final String ADD_BODY_SHORT = """
      {
          "firstName": "Roger",
          "lastName": "Patrick",
          "position": "CTO",
          "region": "ASEAN",
          "notice": "anything special",
          "status": "ACTIVE",
          "lastVisit": "2021-01-17",
          "scheduledVisit": "2021-05-05"
      }""";

  private static final String UPDATE_BODY_SHORT = """
      {
          "id": 3,
          "firstName": "Roger",
          "lastName": "Patrick",
          "position": "CTO",
          "region": "ASEAN",
          "notice": "anything special",
          "status": "ACTIVE",
          "lastVisit": "2021-01-17",
          "scheduledVisit": "2021-05-05"
      }""";

  @MockBean
  EntityService<Representative> service;

  @MockBean
  EntityService<Customer> customerService;

  @MockBean
  RepresentativeMapper mapper;

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

  @ParameterizedTest
  @ValueSource(strings = {ADD_BODY_SHORT, ADD_BODY_FULL, "{}"})
  void testAdd(String requestBody) throws Exception {
    when(customerService.findById(anyInt())).thenReturn(new Customer());
    when(mapper.toEntity(any(RepresentativeDto.class), any(Customer.class))).thenReturn(new Representative());
    Representative representative = Mockito.mock(Representative.class);
    when(service.save(any(Representative.class))).thenReturn(representative);
    when(representative.getId()).thenReturn(1);

    this.mockMvc
        .perform(post("/representatives/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated());

    verify(customerService).findById(anyInt());
    verify(mapper).toEntity(any(RepresentativeDto.class), any(Customer.class));
    verify(service).save(any(Representative.class));
    verify(representative).getId();
  }

  @ParameterizedTest
  @ValueSource(strings = {UPDATE_BODY_SHORT, UPDATE_BODY_FULL, "{}"})
  void testUpdate(String requestBody) throws Exception {
    when(service.existsById(anyInt())).thenReturn(true);
    when(customerService.findById(anyInt())).thenReturn(new Customer());
    when(mapper.toEntity(any(RepresentativeDto.class), any(Customer.class))).thenReturn(new Representative());
    when(service.save(any(Representative.class))).thenReturn(any(Representative.class));

    this.mockMvc
        .perform(put("/representatives/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk());

    verify(service).existsById(anyInt());
    verify(customerService).findById(anyInt());
    verify(mapper).toEntity(any(RepresentativeDto.class), any(Customer.class));
    verify(service).save(any(Representative.class));
  }

  @Test
  void testFailingUpdate() throws Exception {
    when(service.existsById(anyInt())).thenReturn(false);
    when(customerService.findById(anyInt())).thenReturn(new Customer());
    when(mapper.toEntity(any(RepresentativeDto.class), any(Customer.class))).thenReturn(new Representative());
    when(service.save(any(Representative.class))).thenReturn(any(Representative.class));

    this.mockMvc
        .perform(put("/representatives/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(UPDATE_BODY_FULL))
        .andExpect(status().isNotFound());

    verify(service).existsById(anyInt());
    verify(customerService, never()).findById(anyInt());
    verify(mapper, never()).toEntity(any(RepresentativeDto.class), any(Customer.class));
    verify(service, never()).save(any(Representative.class));
  }

  @Test
  @DisplayName("Test whether deleting representative is successful")
  void testDelete() throws Exception {
    when(service.existsById(anyInt())).thenReturn(true);
    service.deleteById(anyInt());

    this.mockMvc
        .perform(delete("/representatives/delete/1"))
        .andExpect(status().isNoContent());

    verify(service).existsById(anyInt());
    verify(service, times(2)).deleteById(anyInt());
  }

  @Test
  @DisplayName("Test whether deleting representative fails due to not existing representative")
  void testFailingDelete() throws Exception {
    when(service.existsById(anyInt())).thenReturn(false);
    service.deleteById(anyInt());

    this.mockMvc
        .perform(delete("/representatives/delete/1"))
        .andExpect(status().isNotFound());

    verify(service).existsById(anyInt());
    verify(service).deleteById(anyInt());
  }
}