package com.ronja.crm.ronjaserver.dao;

import com.ronja.crm.ronjaserver.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

  List<Employee> findAllByOrderByLastNameAsc();

  List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);
}
