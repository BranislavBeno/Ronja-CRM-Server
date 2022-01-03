package com.ronja.crm.ronjaserver.config;

import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
import com.ronja.crm.ronjaserver.service.CustomerService;
import com.ronja.crm.ronjaserver.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  @Bean
  public CustomerService customerService(@Autowired CustomerRepository repository) {
    return new CustomerService(repository);
  }

  @Bean
  public RepresentativeService representativeService(@Autowired RepresentativeRepository repository) {
    return new RepresentativeService(repository);
  }
}
