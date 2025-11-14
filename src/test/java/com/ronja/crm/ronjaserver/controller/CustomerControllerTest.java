package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

final class CustomerControllerTest extends CustomerControllerBaseTest {

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

    @ParameterizedTest
    @ValueSource(strings = {ADD_BODY, "{}"})
    @DisplayName("Test whether adding new customer is successful")
    void testAdd(String body) throws Exception {
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        Customer customer = Mockito.mock(Customer.class);
        when(service.save(any(Customer.class))).thenReturn(customer);
        when(customer.getId()).thenReturn(1);

        this.mockMvc
                .perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        verify(customer).getId();
        verify(service).save(any(Customer.class));
        verify(mapper).toEntity(any(CustomerDto.class));
    }

    @Test
    @DisplayName("Test whether adding new customer fails due to constraint violation")
    void testFailingAddDueConstraintViolation() throws Exception {
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        when(service.save(any(Customer.class))).thenThrow(ConstraintViolationException.class);

        this.mockMvc
                .perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BAD_BODY))
                .andExpect(status().isBadRequest());

        verify(mapper, never()).toEntity(any(CustomerDto.class));
        verify(service, never()).save(any(Customer.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {UPDATE_BODY, "{}"})
    @DisplayName("Test whether updating an existing customer is successful")
    void testUpdate(String body) throws Exception {
        when(service.existsById(anyInt())).thenReturn(true);
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        when(service.save(any(Customer.class))).thenReturn(any(Customer.class));

        this.mockMvc
                .perform(put("/customers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(service).existsById(anyInt());
        verify(mapper).toEntity(any(CustomerDto.class));
        verify(service).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test whether updating customer fails due to not existing customer")
    void testFailingUpdate() throws Exception {
        when(service.existsById(anyInt())).thenReturn(false);
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        when(service.save(any(Customer.class))).thenReturn(any(Customer.class));

        this.mockMvc
                .perform(put("/customers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_BODY))
                .andExpect(status().isNotFound());

        verify(service).existsById(anyInt());
        verify(mapper, never()).toEntity(any(CustomerDto.class));
        verify(service, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test whether deleting customer is successful")
    void testDelete() throws Exception {
        when(service.existsById(anyInt())).thenReturn(true);
        service.deleteById(anyInt());

        this.mockMvc
                .perform(delete("/customers/delete/1"))
                .andExpect(status().isNoContent());

        verify(service).existsById(anyInt());
        verify(service, times(2)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Test whether deleting customer fails due to not existing customer")
    void testFailingDelete() throws Exception {
        when(service.existsById(anyInt())).thenReturn(false);
        service.deleteById(anyInt());

        this.mockMvc
                .perform(delete("/customers/delete/1"))
                .andExpect(status().isNotFound());

        verify(service).existsById(anyInt());
        verify(service).deleteById(anyInt());
    }
}