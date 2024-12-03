package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
abstract class CustomerControllerBaseTest {

    static final String ADD_BODY = "{\n" +
                                   "    \"companyName\": \"IdaCorp\",\n" +
                                   "    \"category\": \"LEVEL_3\",\n" +
                                   "    \"focus\": \"TRADE\",\n" +
                                   "    \"status\": \"ACTIVE\"\n" +
                                   "}";

    static final String BAD_BODY = "  \"companyName\": \"TestCorp\",\n" +
                                   "  \"category\": \"LEVEL\",\n" +
                                   "  \"focus\": \"MAN\",\n" +
                                   "  \"status\": \"ACT\"\n";

    static final String UPDATE_BODY = "{\n" +
                                      "    \"id\": 1,\n" +
                                      "    \"companyName\": \"IdaCorp\",\n" +
                                      "    \"category\": \"LEVEL_3\",\n" +
                                      "    \"focus\": \"TRADE\",\n" +
                                      "    \"status\": \"ACTIVE\"\n" +
                                      "}";

    @MockBean
    EntityService<Customer> service;

    @MockBean
    CustomerMapper mapper;

    @Autowired
    MockMvc mockMvc;
}
