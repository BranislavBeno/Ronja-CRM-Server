package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.validation.ConstraintViolationException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.List;

final class CustomerControllerRestAssuredTest extends CustomerControllerBaseTest {

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @DisplayName("Test whether fetching all customers is successful")
    void testFindAll() {
        Mockito.when(mapper.toDto(Mockito.any(Customer.class))).thenReturn(provideDto());
        Mockito.when(service.findAll()).thenReturn(List.of(new Customer(), new Customer()));

        RestAssuredMockMvc
                .when()
                .get("/customers/list")
                .then()
                .status(HttpStatus.OK)
                .body("$.size()", Matchers.is(2))
                .body("[0].id", Matchers.is(1))
                .body("[0].companyName", Matchers.is("corp"))
                .body("[0].category", Matchers.is("LEVEL_1"))
                .body("[0].focus", Matchers.is("TRADE"))
                .body("[0].status", Matchers.is("ACTIVE"));

        Mockito.verify(service).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {ADD_BODY, "{}"})
    @DisplayName("Test whether adding new customer is successful")
    void testAdd(String body) {
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Customer customer = Mockito.mock(Customer.class);
        Mockito.when(service.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(customer.getId()).thenReturn(1);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/customers/add")
                .then()
                .status(HttpStatus.CREATED);

        Mockito.verify(customer).getId();
        Mockito.verify(service).save(Mockito.any(Customer.class));
        Mockito.verify(mapper).toEntity(Mockito.any(CustomerDto.class));
    }

    @Test
    @DisplayName("Test whether adding new customer fails due to constraint violation")
    void testFailingAddDueConstraintViolation() {
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Mockito.when(service.save(Mockito.any(Customer.class))).thenThrow(ConstraintViolationException.class);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(BAD_BODY)
                .when()
                .post("/customers/add")
                .then()
                .status(HttpStatus.BAD_REQUEST);

        Mockito.verify(mapper, Mockito.never()).toEntity(Mockito.any(CustomerDto.class));
        Mockito.verify(service, Mockito.never()).save(Mockito.any(Customer.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {UPDATE_BODY, "{}"})
    @DisplayName("Test whether updating an existing customer is successful")
    void testUpdate(String body) {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(true);
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Mockito.when(service.save(Mockito.any(Customer.class))).thenReturn(Mockito.any(Customer.class));

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/customers/update")
                .then()
                .status(HttpStatus.OK);

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(mapper).toEntity(Mockito.any(CustomerDto.class));
        Mockito.verify(service).save(Mockito.any(Customer.class));
    }

    @Test
    @DisplayName("Test whether updating customer fails due to not existing customer")
    void testFailingUpdate() {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(false);
        Mockito.when(mapper.toEntity(Mockito.any(CustomerDto.class))).thenReturn(new Customer());
        Mockito.when(service.save(Mockito.any(Customer.class))).thenReturn(Mockito.any(Customer.class));

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(UPDATE_BODY)
                .when()
                .put("/customers/update")
                .then()
                .status(HttpStatus.NOT_FOUND);

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(mapper, Mockito.never()).toEntity(Mockito.any(CustomerDto.class));
        Mockito.verify(service, Mockito.never()).save(Mockito.any(Customer.class));
    }

    @Test
    @DisplayName("Test whether deleting customer is successful")
    void testDelete() {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(true);
        service.deleteById(Mockito.anyInt());

        RestAssuredMockMvc
                .when()
                .delete("/customers/delete/1")
                .then()
                .status(HttpStatus.NO_CONTENT);

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(service, Mockito.times(2)).deleteById(Mockito.anyInt());
    }

    @Test
    @DisplayName("Test whether deleting customer fails due to not existing customer")
    void testFailingDelete() {
        Mockito.when(service.existsById(Mockito.anyInt())).thenReturn(false);
        service.deleteById(Mockito.anyInt());

        RestAssuredMockMvc
                .when()
                .delete("/customers/delete/1")
                .then()
                .status(HttpStatus.NOT_FOUND);

        Mockito.verify(service).existsById(Mockito.anyInt());
        Mockito.verify(service).deleteById(Mockito.anyInt());
    }

    private CustomerDto provideDto() {
        return new CustomerDto(1, "corp", "LEVEL_1", "TRADE", "ACTIVE");
    }
}
