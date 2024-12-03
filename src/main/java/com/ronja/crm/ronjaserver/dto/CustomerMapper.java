package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Customer;

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
    customer.setId(dto.getId());
    customer.setCategory(dto.getCategory());
    customer.setFocus(dto.getFocus());
    customer.setStatus(dto.getStatus());
    customer.setCompanyName(dto.getCompanyName());

    return customer;
  }
}
