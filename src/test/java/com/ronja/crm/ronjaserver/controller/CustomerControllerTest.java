package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import jakarta.validation.ConstraintViolationException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

final class CustomerControllerTest extends CustomerControllerBaseTest {

    @Test
    @DisplayName("Test that response is not in xml format")
    void testShouldNotReturnXML() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/list")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    @DisplayName("Test whether fetching all customers is successful")
    void testFindAll() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(new Customer(), new Customer()));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/list")
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(service).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {ADD_BODY, "{}"})
    @DisplayName("Test whether adding new customer is successful")
    void testAdd(String body) throws Exception {
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Customer customer = Mockito.mock(Customer.class);
        Mockito.when(service.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(customer.getId()).thenReturn(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(customer).getId();
        Mockito.verify(service).save(Mockito.any(Customer.class));
        Mockito.verify(mapper).toEntity(Mockito.any(CustomerDto.class));
    }

    @Test
    @DisplayName("Test whether adding new customer fails due to constraint violation")
    void testFailingAddDueConstraintViolation() throws Exception {
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Mockito.when(service.save(Mockito.any(Customer.class))).thenThrow(ConstraintViolationException.class);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BAD_BODY))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(mapper, Mockito.never()).toEntity(Mockito.any(CustomerDto.class));
        Mockito.verify(service, Mockito.never()).save(Mockito.any(Customer.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {UPDATE_BODY, "{}"})
    @DisplayName("Test whether updating an existing customer is successful")
    void testUpdate(String body) throws Exception {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Mockito.when(service.save(Mockito.any(Customer.class))).thenReturn(Mockito.any(Customer.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/customers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(mapper).toEntity(Mockito.any(CustomerDto.class));
        Mockito.verify(service).save(Mockito.any(Customer.class));
    }

    @Test
    @DisplayName("Test whether updating customer fails due to not existing customer")
    void testFailingUpdate() throws Exception {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(false);
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Mockito.when(service.save(Mockito.any(Customer.class))).thenReturn(Mockito.any(Customer.class));

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/customers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_BODY))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(mapper, Mockito.never()).toEntity(Mockito.any(CustomerDto.class));
        Mockito.verify(service, Mockito.never()).save(Mockito.any(Customer.class));
    }

    @Test
    @DisplayName("Test whether deleting customer is successful")
    void testDelete() throws Exception {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(true);
        service.deleteById(Mockito.anyInt());

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/customers/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(service, Mockito.times(2)).deleteById(Mockito.anyInt());
    }

    @Test
    @DisplayName("Test whether deleting customer fails due to not existing customer")
    void testFailingDelete() throws Exception {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(false);
        service.deleteById(Mockito.anyInt());

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/customers/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(service).deleteById(Mockito.anyInt());
    }
}