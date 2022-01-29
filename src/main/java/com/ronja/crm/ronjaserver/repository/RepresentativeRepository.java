package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.Representative;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepresentativeRepository extends CrudRepository<Representative, Integer> {

  List<Representative> findByCustomerId(int id);

  List<Representative> findAllByOrderByLastNameAsc();

  @Query(
      value = """
          SELECT * FROM representative
          WHERE scheduled_visit
          BETWEEN CURRENT_DATE()
          AND ADDDATE(CURRENT_DATE(), :offset);""",
      nativeQuery = true)
  List<Representative> findScheduledForNextNDays(@Param("offset") int offset);
}
