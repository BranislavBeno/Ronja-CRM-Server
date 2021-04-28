package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements EntityService<Customer, CustomerDto> {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAllByOrderByCompanyNameAsc();
  }

  @Override
  public Customer findById(int id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Did not find customer id - " + id));
  }

  @Override
  public Customer save(CustomerDto dto) {
    return customerRepository.save(convertToEntity(dto));
  }

  @Override
  public Customer update(CustomerDto dto) {
    Customer entity = findById(dto.getId());
    entity.setCategory(dto.getCategory());
    entity.setFocus(dto.getFocus());
    entity.setStatus(dto.getStatus());
    entity.setCompanyName(dto.getCompanyName());
    return customerRepository.save(entity);
  }

  @Override
  public void deleteById(int id) {
    customerRepository.deleteById(id);
  }

  @Override
  public List<Customer> searchBy(String name) {
    List<Customer> results;

    if (name != null && (name.trim().length() > 0)) {
      results = customerRepository.findByCompanyNameContainsAllIgnoreCase(name);
    } else {
      results = findAll();
    }

    return results;
  }

  private Customer convertToEntity(CustomerDto dto) {
    var customer = new Customer();
    customer.setId(dto.getId());
    customer.setCategory(dto.getCategory());
    customer.setFocus(dto.getFocus());
    customer.setStatus(dto.getStatus());
    customer.setCompanyName(dto.getCompanyName());

    return customer;
  }
}
