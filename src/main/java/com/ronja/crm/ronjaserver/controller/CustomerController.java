package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/list")
  public List<Customer> customerList() {
    return customerService.findAll();
  }

  @GetMapping("/search")
  public List<Customer> search(@RequestParam("customerName") String theName) {
    return customerService.searchBy(theName);
  }

  @PostMapping("/save")
  public ResponseEntity<Customer> save(@RequestBody CustomerDto dto) {
    Customer customer = customerService.save(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(customer.getId())
        .toUri();

    return ResponseEntity.created(uri).body(customer);
  }

  @PostMapping("/update")
  public ResponseEntity<Customer> update(@RequestBody CustomerDto dto) {
    Customer customer = customerService.update(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
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
