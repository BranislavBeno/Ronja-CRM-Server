package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @MockBean
  CustomerService service;

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
  void shouldGetEmptyArrayWhenNoBooksExists() throws Exception {
    this.mockMvc
        .perform(get("/customers/list")
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
        .andExpect(status().is(200))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", is(0)))
        .andDo(print())
        .andReturn();
  }
}