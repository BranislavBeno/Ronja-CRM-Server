package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dao.CustomerRepository;
import com.ronja.crm.ronjaserver.dto.CustomerDto;
import com.ronja.crm.ronjaserver.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

  @Mock
  private CustomerRepository repository;
  @InjectMocks
  private CustomerServiceImpl cut;

  @Test
  void testFindAllReturnNull() {
    when(repository.findAllByOrderByLastNameAsc()).thenReturn(null);
    List<Customer> customers = cut.findAll();
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(customers).isNull();
  }

  @Test
  void testFindAllReturnList() {
    when(repository.findAllByOrderByLastNameAsc()).thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.findAll();
    verify(repository).findAllByOrderByLastNameAsc();
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
    assertThat(customer.toString()).hasToString("Customer[firstName='null', lastName='null', companyName='null']");
  }

  @Test
  void testAddRegular() {
    CustomerDto customer = new CustomerDto(1, "John", "Doe", "JohnDoeCorp");
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
    when(repository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString()))
        .thenReturn(null);
    List<Customer> customers = cut.searchBy("Mike");
    verify(repository).findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString());
    assertThat(customers).isNull();
  }

  @Test
  void testSearchByReturnList() {
    when(repository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString()))
        .thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.searchBy("Mike");
    verify(repository).findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString());
    assertThat(customers).hasSize(1);
  }

  @Test
  void testSearchEmptyByReturnList() {
    when(repository.findAllByOrderByLastNameAsc())
        .thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.searchBy("");
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(customers).hasSize(1);
  }

  @Test
  void testSearchNullByReturnList() {
    when(repository.findAllByOrderByLastNameAsc())
        .thenReturn(List.of(new Customer()));
    List<Customer> customers = cut.searchBy(null);
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(customers).hasSize(1);
  }
}