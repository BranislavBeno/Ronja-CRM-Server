package com.ronja.crm.ronjaserver.dto;

import com.ronja.crm.ronjaserver.entity.Category;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Focus;
import com.ronja.crm.ronjaserver.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

  public CustomerDto toDto(Customer customer) {
    int id = customer.getId();
    String name = customer.getCompanyName();
    Category category = customer.getCategory();
    Focus focus = customer.getFocus();
    Status status = customer.getStatus();

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
