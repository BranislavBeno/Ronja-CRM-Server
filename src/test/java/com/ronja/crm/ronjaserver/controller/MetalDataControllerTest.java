package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.MetalDataDto;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.entity.MetalData;
import com.ronja.crm.ronjaserver.service.MetalDataService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebMvcTest(MetalDataController.class)
class MetalDataControllerTest {

    @MockitoBean
    private MetalDataService service;
    @MockitoBean
    private MetalDataMapper mapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    @DisplayName("Test whether fetching all metal data is successful")
    void testFindAllIsSuccessful() {
        MetalData data = Mockito.mock(MetalData.class);
        Mockito.when(mapper.toDto(data)).thenReturn(provideDto());
        Mockito.when(service.findAll()).thenReturn(List.of(data));

        RestAssuredMockMvc
                .when()
                .get("/metals/list")
                .then()
                .status(HttpStatus.OK)
                .body("$.size()", Matchers.is(1))
                .body("[0].id", Matchers.is(1))
                .body("[0].fetched", Matchers.is("2022-03-07"))
                .body("[0].currency", Matchers.is("EUR"))
                .body("[0].aluminum", Matchers.is(10.573386f))
                .body("[0].copper", Matchers.is(3.25613698f))
                .body("[0].lead", Matchers.is(14.319009f));

        Mockito.verify(service).findAll();
    }

    @Test
    @DisplayName("Test whether exchanging metal data is successful")
    void testExchangeIsSuccessful() {
        MetalData metalData = Mockito.mock(MetalData.class);
        Mockito.when(service.exchange()).thenReturn(metalData);
        Mockito.when(metalData.getId()).thenReturn(1);

        RestAssuredMockMvc
                .when()
                .get("/metals/exchange")
                .then()
                .status(HttpStatus.CREATED);

        Mockito.verify(metalData).getId();
        Mockito.verify(service).exchange();
    }

    private MetalDataDto provideDto() {
        return new MetalDataDto(
                1,
                LocalDate.of(2022, 3, 7),
                "EUR",
                new BigDecimal("10.573385811699"),
                new BigDecimal("3.256136987247"),
                new BigDecimal("14.319008911883"));
    }
}