package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class RepresentativeMapperTest {

  private RepresentativeMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new RepresentativeMapper();
  }

  @Test
  void testMappingToEntity() {
    RepresentativeDto dto = new RepresentativeDto(1, "Joe", "Doe", "CEO", "EMEA",
        "notice", Status.ACTIVE, LocalDate.now(), LocalDate.now(), Collections.emptyList(),
        Collections.emptyList(), 1);
    Representative representative = mapper.toEntity(dto, new Customer());

    assertThat(representative.getCustomer().getCompanyName()).isNull();
    assertThat(representative.getStatus().getLabel()).isEqualTo("Aktívny");
  }

  @Test
  void testMappingToDto() {
    Representative representative = new Representative();
    RepresentativeDto dto = mapper.toDto(representative);
    assertThat(dto.customerId()).isZero();
  }
}