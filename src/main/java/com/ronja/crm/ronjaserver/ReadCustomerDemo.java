package com.ronja.crm.ronjaserver;

import com.ronja.crm.ronjaserver.entity.Category;
import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.entity.Focus;
import com.ronja.crm.ronjaserver.entity.Status;
import org.hibernate.cfg.Configuration;

public class ReadCustomerDemo {

  public static void main(String[] args) {
    // create session factory
    try (var factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Customer.class)
        .buildSessionFactory()) {

      // get session and start transaction
      var session = factory.getCurrentSession();
      session.beginTransaction();

      var id = 1;
      // retrieve customer based on the id: primary key
      System.out.println("\nGetting customer with id: " + id);

      var customer = session.get(Customer.class, id);
      customer.setCategory(Category.LEVEL_1);
      customer.setFocus(Focus.BUILDER);
      customer.setStatus(Status.ACTIVE);

      System.out.println("Get complete: " + customer);

      // commit the transaction
      session.getTransaction().commit();

      System.out.println("Done!");
    }
  }

}





