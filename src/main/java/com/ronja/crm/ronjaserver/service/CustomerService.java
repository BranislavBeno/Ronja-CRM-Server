package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Customer;

import java.util.List;

public interface CustomerService {

  public List<Customer> findAll();

  public Customer findById(int theId);

  public void save(Customer customer);

  public void deleteById(int theId);

  public List<Customer> searchBy(String name);
}
