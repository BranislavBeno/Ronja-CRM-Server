package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
abstract sealed class CustomerControllerBaseTest permits CustomerControllerTest, CustomerControllerRestAssuredTest {

    static final String ADD_BODY = """
            {
                "companyName": "IdaCorp",
                "category": "LEVEL_3",
                "focus": "TRADE",
                "status": "ACTIVE"
            }""";

    static final String BAD_BODY = """
              "companyName": "TestCorp",
              "category": "LEVEL",
              "focus": "MAN",
              "status": "ACT"
            """;

    static final String UPDATE_BODY = """
            {
                "id": 1,
                "companyName": "IdaCorp",
                "category": "LEVEL_3",
                "focus": "TRADE",
                "status": "ACTIVE"
            }""";

    @MockBean
    EntityService<Customer> service;

    @MockBean
    CustomerMapper mapper;

    @Autowired
    MockMvc mockMvc;
}
