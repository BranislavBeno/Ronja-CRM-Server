package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Category;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Focus;
import com.ronja.crm.ronjaserver.entity.Status;
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
    CustomerDto dto = new CustomerDto(1, "company", Category.LEVEL_1, Focus.BUILDER, Status.ACTIVE);
    Customer customer = mapper.toEntity(dto);
    assertThat(customer.getCategory().getLabel()).isEqualTo("1");
    assertThat(customer.getFocus().getLabel()).isEqualTo("Stavebník");
  }

  @Test
  void testMappingToDto() {
    Customer customer = new Customer();
    CustomerDto dto = mapper.toDto(customer);
    assertThat(dto.id()).isZero();
    assertThat(dto.companyName()).isNull();
  }
}