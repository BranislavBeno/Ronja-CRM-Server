package com.ronja.crm.ronjaserver.dao;

import com.ronja.crm.ronjaserver.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  List<Customer> findAllByOrderByLastNameAsc();

  List<Customer> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);
}
