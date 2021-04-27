package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.CustomerDto;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private CustomerRepository repository;
  @Mock
  private CustomerDto dto;
  @Mock
  private Customer entity;

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
  void testFindByIdThrowException() {
    when(repository.findById(anyInt())).thenThrow(new RuntimeException());
    assertThatThrownBy(() -> cut.findById(1)).hasMessage(null);
  }

  @Test
  void testFindByIdRegular() {
    when(repository.findById(anyInt())).thenReturn(Optional.of(new Customer()));
    Customer customer = cut.findById(1);
    verify(repository).findById(anyInt());
    assertThat(customer.toString()).hasToString("Customer[companyName=null, category=null, focus=null, status=null]");
  }

  @Test
  void testSaveThrowException() {
    when(repository.save(any(Customer.class))).thenThrow(new IllegalArgumentException());
    assertThrows(IllegalArgumentException.class, () -> cut.save(dto));
  }

  @Test
  void testSaveRegular() {
    when(repository.save(any(Customer.class))).thenReturn(new Customer());
    Customer customer = cut.save(dto);
    verify(repository).save(any(Customer.class));
    assertThat(customer).isNotNull();
  }

  @Test
  void testUpdateRegular() {
    when(repository.findById(anyInt())).thenReturn(Optional.of(entity));
    when(repository.save(any(Customer.class))).thenReturn(entity);
    Customer customer = cut.update(dto);
    verify(repository).save(entity);
    assertThat(customer).isNotNull();
  }

  @Test
  void testDeleteByIdRegular() {
    doNothing().when(repository).deleteById(anyInt());
    cut.deleteById(1);
    verify(repository).deleteById(anyInt());
  }

  @Test
  void testSearchByReturnNull() {
    when(repository.findByCompanyNameContainsAllIgnoreCase(anyString()))
        .thenReturn(null);
    List<Customer> customers = cut.searchBy("Mike", "Stephens");
    verify(repository).findByCompanyNameContainsAllIgnoreCase(anyString());
    assertThat(customers).isNull();
  }

  @Test
  void testSearchByReturnList() {
    when(repository.findByCompanyNameContainsAllIgnoreCase(anyString()))
        .thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.searchBy("Mike", "Collins");
    verify(repository).findByCompanyNameContainsAllIgnoreCase(anyString());
    assertThat(customers).hasSize(1);
  }

  @Test
  void testSearchEmptyByReturnList() {
    when(repository.findAllByOrderByCompanyNameAsc())
        .thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.searchBy("", "");
    verify(repository).findAllByOrderByCompanyNameAsc();
    assertThat(customers).hasSize(1);
  }

  @Test
  void testSearchNullByReturnList() {
    when(repository.findAllByOrderByCompanyNameAsc())
        .thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.searchBy(null, null);
    verify(repository).findAllByOrderByCompanyNameAsc();
    assertThat(customers).hasSize(1);
  }
}