package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dao.CustomerRepository;
import com.ronja.crm.ronjaserver.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAllByOrderByLastNameAsc();
  }

  @Override
  public Customer findById(int id) {
    return customerRepository
        .findById(id)
        .orElseThrow(()->new RuntimeException("Did not find customer id - " + id));
  }

  @Override
  public Customer save(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void deleteById(int id) {
    customerRepository.deleteById(id);
  }

  @Override
  public List<Customer> searchBy(String name) {
    List<Customer> results;

    if (name != null && (name.trim().length() > 0)) {
      results = customerRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(name, name);
    } else {
      results = findAll();
    }

    return results;
  }
}
