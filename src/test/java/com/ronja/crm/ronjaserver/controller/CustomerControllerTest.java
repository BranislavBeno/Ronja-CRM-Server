package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerCreationDto;
import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.dto.CustomerMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @MockBean
  EntityService<Customer, CustomerDto> service;

  @MockBean
  CustomerMapper mapper;

  @Autowired
  MockMvc mockMvc;

  @Test
  @DisplayName("Test that response is not in xml format")
  void testShouldNotReturnXML() throws Exception {
    this.mockMvc
        .perform(get("/customers/list")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
        .andExpect(status().isNotAcceptable());
  }

  @Test
  @DisplayName("Test whether fetching all customers is successful")
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
  @DisplayName("Test whether adding new customer is successful")
  void testAdd() throws Exception {
    when(mapper.toEntity(any(CustomerCreationDto.class))).thenReturn(new Customer());
    Customer customer = Mockito.mock(Customer.class);
    when(service.save(any(Customer.class))).thenReturn(customer);
    when(customer.getId()).thenReturn(1);

    this.mockMvc
        .perform(post("/customers/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                     "companyName": "IdaCorp",
                     "category": "LEVEL_3",
                     "focus": "TRADE",
                     "status": "ACTIVE"
                }
                """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Content-Type"))
        .andExpect(header().string("Content-Type", Matchers.equalTo("application/json")));

    verify(customer).getId();
    verify(service).save(any(Customer.class));
    verify(mapper).toEntity(any(CustomerCreationDto.class));
  }

  @Test
  @DisplayName("Test whether updating an existing customer is successful")
  void testUpdate() throws Exception {
    when(service.existsById(anyInt())).thenReturn(true);
    when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
    Customer customer = Mockito.mock(Customer.class);
    when(service.save(any(Customer.class))).thenReturn(customer);
    when(customer.getId()).thenReturn(anyInt());

    this.mockMvc
        .perform(post("/customers/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                     "id": 1,
                     "companyName": "IdaCorp",
                     "category": "LEVEL_3",
                     "focus": "TRADE",
                     "status": "ACTIVE"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(header().exists("Content-Type"))
        .andExpect(header().string("Content-Type", Matchers.equalTo("application/json")));

    verify(service).existsById(anyInt());
    verify(mapper).toEntity(any(CustomerDto.class));
    verify(service).save(any(Customer.class));
    verify(customer).getId();
  }

  @Test
  @DisplayName("Test whether updating customer fails due to not existing customer")
  void testFailingUpdate() throws Exception {
    when(service.existsById(anyInt())).thenReturn(false);
    when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
    Customer customer = Mockito.mock(Customer.class);
    when(service.save(any(Customer.class))).thenReturn(customer);
    when(customer.getId()).thenReturn(anyInt());

    this.mockMvc
        .perform(post("/customers/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                     "id": 1,
                     "companyName": "IdaCorp",
                     "category": "LEVEL_3",
                     "focus": "TRADE",
                     "status": "ACTIVE"
                }
                """))
        .andExpect(status().isNotFound());

    verify(service).existsById(anyInt());
    verify(mapper, never()).toEntity(any(CustomerDto.class));
    verify(service, never()).save(any(Customer.class));
    verify(customer, never()).getId();
  }

  @Test
  @DisplayName("Test whether deleting customer is successful")
  void testDelete() throws Exception {
    service.deleteById(anyInt());

    this.mockMvc
        .perform(delete("/customers/delete/1"))
        .andExpect(status().is(204));

    verify(service, times(2)).deleteById(anyInt());
  }
}