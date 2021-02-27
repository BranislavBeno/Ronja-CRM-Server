package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.entity.Employee;
import com.ronja.crm.ronjaserver.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  public EmployeeController(@Autowired EmployeeService theEmployeeService) {
    employeeService = theEmployeeService;
  }

  @GetMapping("/list")
  public List<Employee> listEmployees() {
    return employeeService.findAll();
  }

  @GetMapping("/search")
  public List<Employee> search(@RequestParam("employeeName") String theName) {
    return employeeService.searchBy(theName);
  }

  @PostMapping("/save")
  public ResponseEntity<Employee> save(@RequestBody Employee theEmployee) {
    Employee employee = employeeService.save(theEmployee);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(employee.getId())
        .toUri();

    return ResponseEntity.created(uri).body(employee);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    employeeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
