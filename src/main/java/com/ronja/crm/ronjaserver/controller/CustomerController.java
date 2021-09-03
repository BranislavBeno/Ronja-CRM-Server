package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final EntityService<Customer, CustomerDto> customerService;

  public CustomerController(@Autowired EntityService<Customer, CustomerDto> customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/list")
  public List<Customer> list() {
    return customerService.findAll();
  }

  @GetMapping("/search")
  public List<Customer> search(@RequestParam("companyName") String theName) {
    return customerService.searchBy(theName);
  }

  @PostMapping("/add")
  public ResponseEntity<Customer> add(@RequestBody CustomerDto dto) {
    var customer = customerService.add(dto);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(customer.getId())
        .toUri();

    return ResponseEntity.created(uri).body(customer);
  }

  @PostMapping("/update")
  public ResponseEntity<Customer> update(@RequestBody CustomerDto dto) {
    var customer = customerService.update(dto);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(customer.getId())
        .toUri();

    return ResponseEntity.created(uri).body(customer);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    customerService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
