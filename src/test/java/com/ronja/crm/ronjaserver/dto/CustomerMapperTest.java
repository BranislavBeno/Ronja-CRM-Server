package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {

  private CustomerMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new CustomerMapper();
  }

  @Test
  void testMappingToEntity() {
    CustomerDto dto = new CustomerDto(1, "company", "LEVEL_1", "BUILDER", "ACTIVE");
    Customer customer = mapper.toEntity(dto);
    Assertions.assertThat(customer.getCategory()).isEqualTo("LEVEL_1");
    Assertions.assertThat(customer.getFocus()).isEqualTo("BUILDER");
  }

  @Test
  void testMappingToDto() {
    Customer customer = new Customer();
    CustomerDto dto = mapper.toDto(customer);
    Assertions.assertThat(dto.getId()).isZero();
    Assertions.assertThat(dto.getCompanyName()).isNull();
  }
}