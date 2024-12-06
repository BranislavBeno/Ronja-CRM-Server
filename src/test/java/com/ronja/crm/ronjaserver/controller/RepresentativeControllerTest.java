package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.dto.RepresentativeMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.service.EntityService;
import com.ronja.crm.ronjaserver.service.ExtendedEntityService;
import org.hamcrest.Matchers;
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

import jakarta.validation.ConstraintViolationException;
import java.util.List;

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
                                                      {
                                                          "contact": "+420920920920",
                                                          "type": "HOME",
                                                          "primary": false
                                                      }
                                                  ],
                                                  "emails": [
                                                      {
                                                          "contact": "patrick@example.com",
                                                          "type": "WORK",
                                                          "primary": true
                                                      }
                                                  ],
                                                  "customerId": 1
                                              }\
                                              """;

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
                                                         {
                                                             "contact": "+420920920920",
                                                             "type": "HOME",
                                                             "primary": false
                                                         }
                                                     ],
                                                     "emails": [
                                                         {
                                                             "contact": "patrick@example.com",
                                                             "type": "WORK",
                                                             "primary": true
                                                         }
                                                     ],
                                                     "customerId": 1
                                                 }\
                                                 """;

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
                                               }\
                                               """;

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
                                                  }
                                                  """;

  @MockBean
  ExtendedEntityService<Representative> representativeService;

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
    Mockito.when(representativeService.findAll()).thenReturn(List.of(new Representative(), new Representative()));
    this.mockMvc
        .perform(get("/representatives/list")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", Matchers.is(2)))
        .andDo(print())
        .andReturn();
    Mockito.verify(representativeService).findAll();
  }

  @Test
  void testFindByCustomerId() throws Exception {
    Mockito.when(representativeService.findByCustomerId(Mockito.anyInt())).thenReturn(List.of(new Representative()));
    this.mockMvc
        .perform(get("/representatives/search?customerId=1")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", Matchers.is(1)))
        .andDo(print())
        .andReturn();
    Mockito.verify(representativeService).findByCustomerId(Mockito.anyInt());
  }

  @Test
  void testFindScheduledForNextNDays() throws Exception {
    Mockito.when(representativeService.findScheduledForNextNDays(Mockito.anyInt())).thenReturn(List.of(new Representative()));
    this.mockMvc
        .perform(get("/representatives/scheduled?days=7")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", Matchers.is(1)))
        .andDo(print())
        .andReturn();
    Mockito.verify(representativeService).findScheduledForNextNDays(Mockito.anyInt());
  }

  @ParameterizedTest
  @ValueSource(strings = {ADD_BODY_SHORT, ADD_BODY_FULL, "{}"})
  void testAdd(String requestBody) throws Exception {
    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(new Customer());
    Mockito.when(mapper.toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class))).thenReturn(new Representative());
    Representative representative = Mockito.mock(Representative.class);
    Mockito.when(representativeService.save(Mockito.any(Representative.class))).thenReturn(representative);
    Mockito.when(representative.getId()).thenReturn(1);

    this.mockMvc
        .perform(post("/representatives/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated());

    Mockito.verify(customerService).findById(Mockito.anyInt());
    Mockito.verify(mapper).toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class));
    Mockito.verify(representativeService).save(Mockito.any(Representative.class));
    Mockito.verify(representative).getId();
  }

  @Test
  @DisplayName("Test whether adding new representative fails due to constraint violation")
  void testFailingAddDueConstraintViolation() throws Exception {
    String body = """
                    "firstName": "Roger",
                    "lastName": "Patrick",
                    "position": "CTO",
                    "region": "ASEAN",
                    "notice": "anything special",
                    "status": "ACTIVE",
                    "lastVisit": "2021-01-17",
                    "scheduledVisit": "2021-05-05"
                  """;
    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(new Customer());
    Mockito.when(mapper.toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class))).thenReturn(new Representative());
    Mockito.when(representativeService.save(Mockito.any(Representative.class))).thenThrow(ConstraintViolationException.class);

    this.mockMvc
        .perform(post("/representatives/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isBadRequest());

    Mockito.verify(customerService, Mockito.never()).findById(Mockito.anyInt());
    Mockito.verify(mapper, Mockito.never()).toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class));
    Mockito.verify(representativeService, Mockito.never()).save(Mockito.any(Representative.class));
  }

  @ParameterizedTest
  @ValueSource(strings = {UPDATE_BODY_SHORT, UPDATE_BODY_FULL, "{}"})
  void testUpdate(String requestBody) throws Exception {
    Mockito.when(representativeService.existsById(Mockito.anyInt())).thenReturn(true);
    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(new Customer());
    Mockito.when(mapper.toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class))).thenReturn(new Representative());
    Mockito.when(representativeService.save(Mockito.any(Representative.class))).thenReturn(Mockito.any(Representative.class));

    this.mockMvc
        .perform(put("/representatives/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk());

    Mockito.verify(representativeService).existsById(Mockito.anyInt());
    Mockito.verify(customerService).findById(Mockito.anyInt());
    Mockito.verify(mapper).toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class));
    Mockito.verify(representativeService).save(Mockito.any(Representative.class));
  }

  @Test
  void testFailingUpdate() throws Exception {
    Mockito.when(representativeService.existsById(Mockito.anyInt())).thenReturn(false);
    Mockito.when(customerService.findById(Mockito.anyInt())).thenReturn(new Customer());
    Mockito.when(mapper.toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class))).thenReturn(new Representative());
    Mockito.when(representativeService.save(Mockito.any(Representative.class))).thenReturn(Mockito.any(Representative.class));

    this.mockMvc
        .perform(put("/representatives/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(UPDATE_BODY_FULL))
        .andExpect(status().isNotFound());

    Mockito.verify(representativeService).existsById(Mockito.anyInt());
    Mockito.verify(customerService, Mockito.never()).findById(Mockito.anyInt());
    Mockito.verify(mapper, Mockito.never()).toEntity(Mockito.any(RepresentativeDto.class), Mockito.any(Customer.class));
    Mockito.verify(representativeService, Mockito.never()).save(Mockito.any(Representative.class));
  }

  @Test
  @DisplayName("Test whether deleting representative is successful")
  void testDelete() throws Exception {
    Mockito.when(representativeService.existsById(Mockito.anyInt())).thenReturn(true);
    representativeService.deleteById(Mockito.anyInt());

    this.mockMvc
        .perform(delete("/representatives/delete/1"))
        .andExpect(status().isNoContent());

    Mockito.verify(representativeService).existsById(Mockito.anyInt());
    Mockito.verify(representativeService, Mockito.times(2)).deleteById(Mockito.anyInt());
  }

  @Test
  @DisplayName("Test whether deleting representative fails due to not existing representative")
  void testFailingDelete() throws Exception {
    Mockito.when(representativeService.existsById(Mockito.anyInt())).thenReturn(false);
    representativeService.deleteById(Mockito.anyInt());

    this.mockMvc
        .perform(delete("/representatives/delete/1"))
        .andExpect(status().isNotFound());

    Mockito.verify(representativeService).existsById(Mockito.anyInt());
    Mockito.verify(representativeService).deleteById(Mockito.anyInt());
  }
}