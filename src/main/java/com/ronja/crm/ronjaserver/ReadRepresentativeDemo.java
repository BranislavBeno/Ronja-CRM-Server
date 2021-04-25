package com.ronja.crm.ronjaserver;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.entity.Status;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class ReadRepresentativeDemo {

  public static void main(String[] args) {
    // create session factory
    try (var factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Customer.class)
        .addAnnotatedClass(Representative.class)
        .buildSessionFactory()) {

      // get session and start transaction
      var session = factory.getCurrentSession();
      session.beginTransaction();

      var representative = new Representative();
      representative.setFirstName("Jane");
      representative.setLastName("Smith");
      representative.setPosition("CFO");
      representative.setRegion("EMEA");
      representative.setNotice("anything special");
      representative.setStatus(Status.INACTIVE);
      representative.setLastVisit(LocalDate.of(2020, 10, 7));
      representative.setScheduledVisit(LocalDate.now());

      session.save(representative);

      var id = 1;
      var repr = session.get(Representative.class, id);
      repr.setRegion("V4");

      // commit the transaction
      session.getTransaction().commit();
    }
  }

}





