package com.ronja.crm.ronjaserver.utils;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;

public class CustomerUtils {

  private CustomerUtils() {
  }

  public static Customer convertToEntity(CustomerDto dto) {
    var customer = new Customer();
    customer.setId(dto.id());
    customer.setCategory(dto.category());
    customer.setFocus(dto.focus());
    customer.setStatus(dto.status());
    customer.setCompanyName(dto.companyName());

    return customer;
  }
}
