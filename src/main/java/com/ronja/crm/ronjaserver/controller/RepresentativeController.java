package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.dto.RepresentativeMapper;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.exception.EntityNotFoundException;
import com.ronja.crm.ronjaserver.service.EntityService;
import com.ronja.crm.ronjaserver.service.ExtendedEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/representatives")
public class RepresentativeController {

  private final ExtendedEntityService<Representative> representativeService;
  private final EntityService<Customer> customerService;
  private final RepresentativeMapper mapper;

  public RepresentativeController(ExtendedEntityService<Representative> representativeService,
                                  EntityService<Customer> customerService,
                                  RepresentativeMapper mapper) {
    this.representativeService = representativeService;
    this.customerService = customerService;
    this.mapper = mapper;
  }

  @GetMapping("/list")
  public List<Representative> list() {
    return representativeService.findAll();
  }

  @GetMapping("/search")
  public List<Representative> search(@RequestParam("customerId") int id) {
    return representativeService.findByCustomerId(id);
  }

  @GetMapping("/scheduled")
  public List<Representative> fetchScheduled(@RequestParam("days") int offset) {
    return representativeService.findScheduledForNextNDays(offset);
  }

  @PostMapping("/add")
  public ResponseEntity<RepresentativeDto> add(@Valid @RequestBody RepresentativeDto dto) {
    Representative representative = provideRepresentative(dto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(representative.getId())
        .toUri();

    return ResponseEntity.created(uri).body(mapper.toDto(representative));
  }

  @PutMapping("/update")
  public ResponseEntity<RepresentativeDto> update(@Valid @RequestBody RepresentativeDto dto) {
    if (representativeService.existsById(dto.getId())) {
      Representative representative = provideRepresentative(dto);
      return ResponseEntity.ok(mapper.toDto(representative));
    } else {
      throw new EntityNotFoundException("Reprezentant", String.join(" ", dto.getFirstName(), dto.getLastName()));
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    if (representativeService.existsById(id)) {
      representativeService.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      throw new EntityNotFoundException("Reprezentant");
    }
  }

  private Representative provideRepresentative(RepresentativeDto dto) {
    Customer customer = customerService.findById(dto.getCustomerId());
    return representativeService.save(mapper.toEntity(dto, customer));
  }
}
