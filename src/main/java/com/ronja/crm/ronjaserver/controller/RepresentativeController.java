package com.ronja.crm.ronjaserver.controller;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/representatives")
public class RepresentativeController {

  private final EntityService<Representative, RepresentativeDto> representativeService;

  public RepresentativeController(@Autowired EntityService<Representative, RepresentativeDto> representativeService) {
    this.representativeService = representativeService;
  }

  @GetMapping("/list")
  public List<Representative> representativeList() {
    return representativeService.findAll();
  }

  @GetMapping("/search")
  public List<Representative> search(@RequestParam("lastName") String lastName) {
    return representativeService.searchBy(lastName);
  }

  @PostMapping("/add")
  public ResponseEntity<Representative> add(@RequestBody RepresentativeDto dto) {
    var representative = representativeService.add(dto);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(representative.getId())
        .toUri();

    return ResponseEntity.created(uri).body(representative);
  }

  @PostMapping("/update")
  public ResponseEntity<Representative> update(@RequestBody RepresentativeDto dto) {
    var representative = representativeService.update(dto);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(representative.getId())
        .toUri();

    return ResponseEntity.created(uri).body(representative);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    representativeService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
