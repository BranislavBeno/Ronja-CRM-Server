package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;

import java.util.List;

public interface CustomerService {

  List<Customer> findAll();

  Customer findById(int theId);

  Customer add(CustomerDto dto);

  Customer update(CustomerDto dto);

  void deleteById(int theId);

  List<Customer> searchBy(String name);
}
