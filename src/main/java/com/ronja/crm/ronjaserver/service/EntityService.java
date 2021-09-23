package com.ronja.crm.ronjaserver.service;

import java.util.List;

public interface EntityService<T, U> {

  List<T> findAll();

  T findById(int theId);

  T add(T entity);

  T addDto(U dto);

  T update(U dto);

  void deleteById(int theId);

  List<T> searchBy(String name);
}
