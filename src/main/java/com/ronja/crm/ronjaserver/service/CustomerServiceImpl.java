package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dao.CustomerRepository;
import com.ronja.crm.ronjaserver.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

  private CustomerRepository customerRepository;

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
    Optional<Customer> result = customerRepository.findById(id);

    Customer customer = null;

    if (result.isPresent()) {
      customer = result.get();
    } else {
      // we didn't find the employee
      throw new RuntimeException("Did not find customer id - " + id);
    }

    return customer;
  }

  @Override
  public Customer save(Customer customer) {
    customerRepository.save(customer);
    return customer;
  }

  @Override
  public void deleteById(int id) {
    customerRepository.deleteById(id);
  }

  @Override
  public List<Customer> searchBy(String name) {

    List<Customer> results = null;

    if (name != null && (name.trim().length() > 0)) {
      results = customerRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(name, name);
    } else {
      results = findAll();
    }

    return results;
  }
}
