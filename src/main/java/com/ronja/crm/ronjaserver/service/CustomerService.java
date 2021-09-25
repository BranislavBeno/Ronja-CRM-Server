package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import com.ronja.crm.ronjaserver.utils.CustomerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
@Service
public class CustomerService implements EntityService<Customer, CustomerDto> {

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
    return repository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Did not find customer id - " + id));
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
  public Customer addDto(CustomerDto dto) {
    return repository.save(CustomerUtils.convertToEntity(dto));
  }

  @Override
  public Customer updateDto(CustomerDto dto) {
    Customer entity = findById(dto.id());
    entity.setCategory(dto.category());
    entity.setFocus(dto.focus());
    entity.setStatus(dto.status());
    entity.setCompanyName(dto.companyName());
    return repository.save(entity);
  }

  @Override
  public void deleteById(int id) {
    repository.deleteById(id);
  }

  @Override
  public List<Customer> searchBy(String name) {
    List<Customer> results;

    if (name != null && (name.trim().length() > 0)) {
      results = repository.findByCompanyNameContainsAllIgnoreCase(name);
    } else {
      results = findAll();
    }

    return results;
  }
}
