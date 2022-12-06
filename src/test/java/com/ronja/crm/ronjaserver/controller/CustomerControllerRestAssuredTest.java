package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

final class CustomerControllerRestAssuredTest extends CustomerControllerBaseTest {

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @DisplayName("Test whether fetching all customers is successful")
    void testFindAll() {
        when(mapper.toDto(any(Customer.class))).thenReturn(provideDto());
        when(service.findAll()).thenReturn(List.of(new Customer(), new Customer()));

        RestAssuredMockMvc
                .when()
                .get("/customers/list")
                .then()
                .status(HttpStatus.OK)
                .body("$.size()", is(2))
                .body("[0].id", is(1))
                .body("[0].companyName", is("corp"))
                .body("[0].category", is("LEVEL_1"))
                .body("[0].focus", is("TRADE"))
                .body("[0].status", is("ACTIVE"));

        verify(service).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {ADD_BODY, "{}"})
    @DisplayName("Test whether adding new customer is successful")
    void testAdd(String body) {
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        Customer customer = Mockito.mock(Customer.class);
        when(service.save(any(Customer.class))).thenReturn(customer);
        when(customer.getId()).thenReturn(1);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/customers/add")
                .then()
                .status(HttpStatus.CREATED);

        verify(customer).getId();
        verify(service).save(any(Customer.class));
        verify(mapper).toEntity(any(CustomerDto.class));
    }

    @Test
    @DisplayName("Test whether adding new customer fails due to constraint violation")
    void testFailingAddDueConstraintViolation() {
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        when(service.save(any(Customer.class))).thenThrow(ConstraintViolationException.class);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(BAD_BODY)
                .when()
                .post("/customers/add")
                .then()
                .status(HttpStatus.BAD_REQUEST);

        verify(mapper, never()).toEntity(any(CustomerDto.class));
        verify(service, never()).save(any(Customer.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {UPDATE_BODY, "{}"})
    @DisplayName("Test whether updating an existing customer is successful")
    void testUpdate(String body) {
        when(service.existsById(anyInt())).thenReturn(true);
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        when(service.save(any(Customer.class))).thenReturn(any(Customer.class));

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/customers/update")
                .then()
                .status(HttpStatus.OK);

        verify(service).existsById(anyInt());
        verify(mapper).toEntity(any(CustomerDto.class));
        verify(service).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test whether updating customer fails due to not existing customer")
    void testFailingUpdate() {
        when(service.existsById(anyInt())).thenReturn(false);
        when(mapper.toEntity(any(CustomerDto.class))).thenReturn(new Customer());
        when(service.save(any(Customer.class))).thenReturn(any(Customer.class));

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(UPDATE_BODY)
                .when()
                .put("/customers/update")
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(service).existsById(anyInt());
        verify(mapper, never()).toEntity(any(CustomerDto.class));
        verify(service, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test whether deleting customer is successful")
    void testDelete() {
        when(service.existsById(anyInt())).thenReturn(true);
        service.deleteById(anyInt());

        RestAssuredMockMvc
                .when()
                .delete("/customers/delete/1")
                .then()
                .status(HttpStatus.NO_CONTENT);

        verify(service).existsById(anyInt());
        verify(service, times(2)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Test whether deleting customer fails due to not existing customer")
    void testFailingDelete() {
        when(service.existsById(anyInt())).thenReturn(false);
        service.deleteById(anyInt());

        RestAssuredMockMvc
                .when()
                .delete("/customers/delete/1")
                .then()
                .status(HttpStatus.NOT_FOUND);

        verify(service).existsById(anyInt());
        verify(service).deleteById(anyInt());
    }

    private CustomerDto provideDto() {
        return new CustomerDto(1, "corp", "LEVEL_1", "TRADE", "ACTIVE");
    }
}
