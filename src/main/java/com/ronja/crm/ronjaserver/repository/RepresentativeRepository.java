package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Representative;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepresentativeRepository extends CrudRepository<Representative, Integer> {

  List<Representative> findAllByOrderByLastNameAsc();

  List<Representative> findByLastNameContainsAllIgnoreCase(String name);
}
