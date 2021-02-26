package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

  private List<Customer> customers;

  public CustomerServiceImpl() {
    customers = new ArrayList<>();
    customers.add(new Customer("Jozo", "Jozo","JozoCorp"));
    customers.add(new Customer("Fero", "Fero","FeroCorp"));
    customers.add(new Customer("Juro", "Juro","JuroCorp"));
  }

  @Override
  public List<Customer> findAll() {
    return customers;
  }

  @Override
  public Customer findById(int theId) {
    return null;
  }

  @Override
  public void save(Customer customer) {

  }

  @Override
  public void deleteById(int theId) {

  }

  @Override
  public List<Customer> searchBy(String name) {
    return Collections.emptyList();
  }
}
