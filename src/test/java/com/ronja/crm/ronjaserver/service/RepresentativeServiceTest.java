package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
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
class RepresentativeServiceTest {

  @Mock
  private RepresentativeRepository repository;

  @InjectMocks
  private RepresentativeService cut;

  @Test
  void testFindAllReturnNull() {
    when(repository.findAllByOrderByLastNameAsc()).thenReturn(null);
    List<Representative> representatives = cut.findAll();
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(representatives).isNull();
  }

  @Test
  void testFindAllReturnList() {
    when(repository.findAllByOrderByLastNameAsc()).thenReturn(List.of(new Representative()));
    List<Representative> representatives = cut.findAll();
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(representatives).hasSize(1);
  }

  @Test
  void testFindByIdRegular() {
    when(repository.findById(anyInt())).thenReturn(Optional.of(new Representative()));
    Representative representative = cut.findById(1);
    verify(repository).findById(anyInt());
    assertThat(representative).isNotNull();
  }

  @Test
  void testFindByIdNull() {
    when(repository.findById(anyInt())).thenReturn(Optional.empty());
    Representative representative = cut.findById(1);
    verify(repository).findById(anyInt());
    assertThat(representative).isNull();
  }

  @Test
  void testFindByCustomerIdRegular() {
    when(repository.findByCustomerId(anyInt())).thenReturn(List.of(new Representative()));
    List<Representative> representatives = cut.findByCustomerId(1);
    verify(repository).findByCustomerId(anyInt());
    assertThat(representatives).hasSize(1);
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
    when(repository.save(any(Representative.class))).thenThrow(new IllegalArgumentException());
    Representative representative = new Representative();
    assertThrows(IllegalArgumentException.class, () -> cut.save(representative));
  }

  @Test
  void testSaveRegular() {
    when(repository.save(any(Representative.class))).thenReturn(new Representative());
    Representative representative = cut.save(new Representative());
    verify(repository).save(any(Representative.class));
    assertThat(representative).isNotNull();
  }

  @Test
  void testDeleteByIdRegular() {
    doNothing().when(repository).deleteById(anyInt());
    cut.deleteById(1);
    verify(repository).deleteById(anyInt());
  }
}