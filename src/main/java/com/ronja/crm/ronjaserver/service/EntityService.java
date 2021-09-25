package com.ronja.crm.ronjaserver.service;

import java.util.List;

public interface EntityService<T, U> {

  List<T> findAll();

  T findById(int theId);

  boolean existsById(int theId);

  T save(T entity);

  T addDto(U dto);

  T updateDto(U dto);

  void deleteById(int theId);

  List<T> searchBy(String name);
}
