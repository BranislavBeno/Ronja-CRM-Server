package com.ronja.crm.ronjaserver.service;

import java.util.List;

public interface ExtendedEntityService<T> extends EntityService<T> {

  List<T> findByCustomerId(int id);

  List<T> findScheduledForNextNDays(int id);
}
