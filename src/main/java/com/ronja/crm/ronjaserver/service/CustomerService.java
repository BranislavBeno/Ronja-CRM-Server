package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import com.ronja.crm.ronjaserver.utils.CustomerUtils;
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
  public Customer add(CustomerDto dto) {
    return customerRepository.save(CustomerUtils.convertToEntity(dto));
  }

  @Override
  public Customer update(CustomerDto dto) {
    Customer entity = findById(dto.id());
    entity.setCategory(dto.category());
    entity.setFocus(dto.focus());
    entity.setStatus(dto.status());
    entity.setCompanyName(dto.companyName());
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
}
