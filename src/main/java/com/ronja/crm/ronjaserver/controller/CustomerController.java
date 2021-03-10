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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(@Autowired CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/list")
  public List<CustomerDto> customerList() {
    List<Customer> entities = customerService.findAll();
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/search")
  public List<CustomerDto> search(@RequestParam("customerName") String theName) {
    List<Customer> entities = customerService.searchBy(theName);
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @PostMapping("/add")
  public ResponseEntity<Customer> add(@RequestBody CustomerDto newCustomer) {
    Customer customer = customerService.add(newCustomer);
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

  private CustomerDto convertToDto(Customer entity) {
    return new CustomerDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getCompanyName());
  }
}
