package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Customer;
import com.ronja.crm.ronjaserver.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private CustomerRepository repository;

  @InjectMocks
  private CustomerService cut;

  @Test
  void testFindAllReturnNull() {
    when(repository.findAllByOrderByCompanyNameAsc()).thenReturn(null);
    List<Customer> customers = cut.findAll();
    verify(repository).findAllByOrderByCompanyNameAsc();
    assertThat(customers).isNull();
  }

  @Test
  void testFindAllReturnList() {
    when(repository.findAllByOrderByCompanyNameAsc()).thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.findAll();
    verify(repository).findAllByOrderByCompanyNameAsc();
    assertThat(customers).hasSize(1);
  }

  @Test
  void testFindByIdRegular() {
    when(repository.findById(anyInt())).thenReturn(Optional.of(new Customer()));
    Customer customer = cut.findById(1);
    verify(repository).findById(anyInt());
    assertThat(customer).isNotNull();
  }

  @Test
  void testFindByIdNull() {
    when(repository.findById(anyInt())).thenReturn(Optional.empty());
    Customer customer = cut.findById(1);
    verify(repository).findById(anyInt());
    assertThat(customer).isNull();
  }

  @Test
  void testExistsById() {
    when(repository.existsById(anyInt())).thenReturn(true);
    boolean isPresent = cut.existsById(1);
    verify(repository).existsById(anyInt());
    assertThat(isPresent).isTrue();
  }

  @Test
  void testSaveThrowException() {
    when(repository.save(any(Customer.class))).thenThrow(new IllegalArgumentException());
    Customer customer = new Customer();
    assertThrows(IllegalArgumentException.class, () -> cut.save(customer));
  }

  @Test
  void testSaveRegular() {
    when(repository.save(any(Customer.class))).thenReturn(new Customer());
    Customer customer = cut.save(new Customer());
    verify(repository).save(any(Customer.class));
    assertThat(customer).isNotNull();
  }

  @Test
  void testDeleteByIdRegular() {
    doNothing().when(repository).deleteById(anyInt());
    cut.deleteById(1);
    verify(repository).deleteById(anyInt());
  }
}