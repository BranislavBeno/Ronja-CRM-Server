package com.ronja.crm.ronjaserver.service;

import java.util.List;

public interface EntityService<T> {

  List<T> findAll();

  T findById(int theId);

  boolean existsById(int theId);

  T save(T entity);

  void deleteById(int theId);
}
