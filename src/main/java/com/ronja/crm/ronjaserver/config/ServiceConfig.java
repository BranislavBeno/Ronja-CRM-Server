package com.ronja.crm.ronjaserver.config;

import com.ronja.crm.ronjaserver.client.api.MetalExchangeWebClient;
import com.ronja.crm.ronjaserver.dto.CustomerMapper;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.dto.RepresentativeMapper;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import com.ronja.crm.ronjaserver.repository.MetalDataRepository;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
import com.ronja.crm.ronjaserver.service.CustomerService;
import com.ronja.crm.ronjaserver.service.MetalDataService;
import com.ronja.crm.ronjaserver.service.RepresentativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public CustomerMapper customerMapper() {
        return new CustomerMapper();
    }

    @Bean
    public RepresentativeMapper representativeMapper() {
        return new RepresentativeMapper();
    }

    @Bean
    public MetalExchangeWebClient metalExchangeWebClient(@Value("${client.metal.api.url}") String url,
                                                         @Value("${client.metal.api.access-key}") String accessKey) {
        return new MetalExchangeWebClient(url, accessKey);
    }

    @Bean
    public MetalDataMapper metalDataMapper() {
        return new MetalDataMapper();
    }

    @Bean
    public MetalDataService metalDataService(@Autowired MetalDataRepository repository,
                                             @Autowired MetalExchangeWebClient webClient,
                                             @Autowired MetalDataMapper mapper) {
        return new MetalDataService(repository, webClient, mapper);
    }
}
