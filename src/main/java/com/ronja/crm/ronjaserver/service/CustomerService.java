package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService implements EntityService<Customer> {

  private final CustomerRepository repository;

  @Autowired
  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Customer> findAll() {
    return repository.findAllByOrderByCompanyNameAsc();
  }

  @Override
  public Customer findById(int id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public boolean existsById(int id) {
    return repository.existsById(id);
  }

  @Override
  public Customer save(Customer customer) {
    Objects.requireNonNull(customer);
    return repository.save(customer);
  }

  @Override
  public void deleteById(int id) {
    repository.deleteById(id);
  }
}
