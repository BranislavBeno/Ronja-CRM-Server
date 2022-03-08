package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.MetalDataDto;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.entity.MetalData;
import com.ronja.crm.ronjaserver.service.MetalDataService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(MetalDataController.class)
class MetalDataControllerTest {

  @MockBean
  private MetalDataService service;
  @MockBean
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
    when(mapper.toDto(data)).thenReturn(provideDto());
    when(service.findAll()).thenReturn(List.of(data));

    RestAssuredMockMvc
        .when()
        .get("/metals/list")
        .then()
        .status(HttpStatus.OK)
        .body("$.size()", is(1))
        .body("[0].id", is(1))
        .body("[0].fetched", is("2022-03-07"))
        .body("[0].currency", is("EUR"))
        .body("[0].aluminum", is(10.573386f))
        .body("[0].copper", is(3.25613698f))
        .body("[0].lead", is(14.319009f));

    verify(service).findAll();
  }

  @Test
  @DisplayName("Test whether exchanging metal data is successful")
  void testExchangeIsSuccessful() {
    MetalData metalData = Mockito.mock(MetalData.class);
    when(service.exchange()).thenReturn(metalData);
    when(metalData.getId()).thenReturn(1);

    RestAssuredMockMvc
        .when()
        .get("/metals/exchange")
        .then()
        .status(HttpStatus.CREATED);

    verify(metalData).getId();
    verify(service).exchange();
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