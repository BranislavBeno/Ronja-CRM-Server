package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

  public CustomerDto toDto(Customer customer) {
    int id = customer.getId();
    String name = customer.getCompanyName();
    String category = customer.getCategory();
    String focus = customer.getFocus();
    String status = customer.getStatus();

    return new CustomerDto(id, name, category, focus, status);
  }

  public Customer toEntity(CustomerDto dto) {
    var customer = new Customer();
    customer.setId(dto.id());
    customer.setCategory(dto.category());
    customer.setFocus(dto.focus());
    customer.setStatus(dto.status());
    customer.setCompanyName(dto.companyName());

    return customer;
  }
}
