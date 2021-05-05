package com.ronja.crm.ronjaserver;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Representative;
import org.hibernate.cfg.Configuration;

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

      var customer = session.get(Customer.class, 1);
      var representative = session.get(Representative.class, 2);
      System.out.println(customer.toString());
      System.out.println(representative.toString());

      // commit the transaction
      session.getTransaction().commit();
    }
  }

}





