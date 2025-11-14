package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
abstract class CustomerControllerBaseTest {

    static final String ADD_BODY = """
                                   {
                                       "companyName": "IdaCorp",
                                       "category": "LEVEL_3",
                                       "focus": "TRADE",
                                       "status": "ACTIVE"
                                   }\
                                   """;

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
                                      }\
                                      """;

    @MockitoBean
    EntityService<Customer> service;

    @MockitoBean
    CustomerMapper mapper;

    @Autowired
    MockMvc mockMvc;
}
