package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    assertThat(customer.getCategory()).isEqualTo("LEVEL_1");
    assertThat(customer.getFocus()).isEqualTo("BUILDER");
  }

  @Test
  void testMappingToDto() {
    Customer customer = new Customer();
    CustomerDto dto = mapper.toDto(customer);
    assertThat(dto.id()).isZero();
    assertThat(dto.companyName()).isNull();
  }
}