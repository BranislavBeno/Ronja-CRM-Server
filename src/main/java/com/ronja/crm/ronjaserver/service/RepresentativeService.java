package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RepresentativeService implements EntityService<Representative> {

  private final RepresentativeRepository repository;

  @Autowired
  public RepresentativeService(RepresentativeRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Representative> findAll() {
    return repository.findAllByOrderByLastNameAsc();
  }

  @Override
  public Representative findById(int id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public boolean existsById(int id) {
    return repository.existsById(id);
  }

  @Override
  public Representative save(Representative representative) {
    Objects.requireNonNull(representative);
    return repository.save(representative);
  }

  @Override
  public void deleteById(int id) {
    repository.deleteById(id);
  }
}
