package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest implements WithAssertions {

  @Mock
  private CustomerRepository repository;

  @InjectMocks
  private CustomerService cut;

  @Test
  void testFindAllReturnNull() {
    Mockito.when(repository.findAllByOrderByCompanyNameAsc()).thenReturn(null);
    List<Customer> customers = cut.findAll();
    Mockito.verify(repository).findAllByOrderByCompanyNameAsc();
    assertThat(customers).isNull();
  }

  @Test
  void testFindAllReturnList() {
    Mockito.when(repository.findAllByOrderByCompanyNameAsc()).thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.findAll();
    Mockito.verify(repository).findAllByOrderByCompanyNameAsc();
    assertThat(customers).hasSize(1);
  }

  @Test
  void testFindByIdRegular() {
    Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Customer()));
    Customer customer = cut.findById(1);
    Mockito.verify(repository).findById(Mockito.anyInt());
    assertThat(customer).isNotNull();
  }

  @Test
  void testFindByIdNull() {
    Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    Customer customer = cut.findById(1);
    Mockito.verify(repository).findById(Mockito.anyInt());
    assertThat(customer).isNull();
  }

  @Test
  void testExistsById() {
    Mockito.when(repository.existsById(Mockito.anyInt())).thenReturn(true);
    boolean isPresent = cut.existsById(1);
    Mockito.verify(repository).existsById(Mockito.anyInt());
    assertThat(isPresent).isTrue();
  }

  @Test
  void testSaveThrowException() {
    Mockito.when(repository.save(Mockito.any(Customer.class))).thenThrow(new IllegalArgumentException());
    Customer customer = new Customer();
    Assertions.assertThrows(IllegalArgumentException.class, () -> cut.save(customer));
  }

  @Test
  void testSaveRegular() {
    Mockito.when(repository.save(Mockito.any(Customer.class))).thenReturn(new Customer());
    Customer customer = cut.save(new Customer());
    Mockito.verify(repository).save(Mockito.any(Customer.class));
    assertThat(customer).isNotNull();
  }

  @Test
  void testDeleteByIdRegular() {
    Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
    cut.deleteById(1);
    Mockito.verify(repository).deleteById(Mockito.anyInt());
  }
}