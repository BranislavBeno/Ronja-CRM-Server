package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

  List<Customer> findAllByOrderByCompanyNameAsc();

  List<Customer> findByCompanyNameContainsAllIgnoreCase(String name);
}
