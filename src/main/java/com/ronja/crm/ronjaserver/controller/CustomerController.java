package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/list")
  public List<Customer> customerList() {
    return customerService.findAll();
  }
}
