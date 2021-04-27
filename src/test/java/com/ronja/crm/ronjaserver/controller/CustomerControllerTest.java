package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @MockBean
  EntityService<Customer, CustomerDto> service;

  @Autowired
  MockMvc mockMvc;

  @Test
  void testShouldNotReturnXML() throws Exception {
    this.mockMvc
        .perform(get("/customers/list")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
        .andExpect(status().isNotAcceptable());
  }

  @Test
  void testFindAll() throws Exception {
    when(service.findAll()).thenReturn(List.of(new Customer(), new Customer()));
    this.mockMvc
        .perform(get("/customers/list")
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
    when(service.searchBy(anyString(), anyString())).thenReturn(List.of(new Customer()));
    this.mockMvc
        .perform(get("/customers/search?customerName=Emma")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(1)));
    verify(service).searchBy(anyString(), anyString());
  }

  @Test
  void testSave() throws Exception {
    when(service.save(any(CustomerDto.class))).thenReturn(new Customer());
    this.mockMvc
        .perform(post("/customers/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\": \"Emma\",\"lastName\": \"Button\",\"companyName\": \"SpiceCorp\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Content-Type"))
        .andExpect(header().string("Content-Type", Matchers.equalTo("application/json")));
    verify(service).save(any(CustomerDto.class));
  }

  @Test
  void testUpdate() throws Exception {
    when(service.update(any(CustomerDto.class))).thenReturn(new Customer());
    this.mockMvc
        .perform(post("/customers/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"firstName\": \"Emma\",\"lastName\": \"Button\",\"companyName\": \"SpiceCorp\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Content-Type"))
        .andExpect(header().string("Content-Type", Matchers.equalTo("application/json")));
    verify(service).update(any(CustomerDto.class));
  }

  @Test
  void testDelete() throws Exception {
    service.deleteById(anyInt());
    this.mockMvc
        .perform(delete("/customers/delete/1"))
        .andExpect(status().is(204));
    verify(service, times(2)).deleteById(anyInt());
  }
}