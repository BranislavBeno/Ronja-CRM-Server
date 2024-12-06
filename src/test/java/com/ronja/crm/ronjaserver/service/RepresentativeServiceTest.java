package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.entity.Representative;
import com.ronja.crm.ronjaserver.repository.RepresentativeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    Mockito.when(repository.findAllByOrderByLastNameAsc()).thenReturn(null);
    List<Representative> representatives = cut.findAll();
    Mockito.verify(repository).findAllByOrderByLastNameAsc();
    Assertions.assertThat(representatives).isNull();
  }

  @Test
  void testFindAllReturnList() {
    Mockito.when(repository.findAllByOrderByLastNameAsc()).thenReturn(List.of(new Representative()));
    List<Representative> representatives = cut.findAll();
    Mockito.verify(repository).findAllByOrderByLastNameAsc();
    Assertions.assertThat(representatives).hasSize(1);
  }

  @Test
  void testFindByIdRegular() {
    Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Representative()));
    Representative representative = cut.findById(1);
    Mockito.verify(repository).findById(Mockito.anyInt());
    Assertions.assertThat(representative).isNotNull();
  }

  @Test
  void testFindByIdNull() {
    Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
    Representative representative = cut.findById(1);
    Mockito.verify(repository).findById(Mockito.anyInt());
    Assertions.assertThat(representative).isNull();
  }

  @Test
  void testFindByCustomerIdRegular() {
    Mockito.when(repository.findByCustomerId(Mockito.anyInt())).thenReturn(List.of(new Representative()));
    List<Representative> representatives = cut.findByCustomerId(1);
    Mockito.verify(repository).findByCustomerId(Mockito.anyInt());
    Assertions.assertThat(representatives).hasSize(1);
  }

  @Test
  void testFindScheduledForNextNDays() {
    Mockito.when(repository.findScheduledForNextNDays(Mockito.anyInt())).thenReturn(List.of(new Representative()));
    List<Representative> representatives = cut.findScheduledForNextNDays(1);
    Mockito.verify(repository).findScheduledForNextNDays(Mockito.anyInt());
    Assertions.assertThat(representatives).hasSize(1);
  }

  @Test
  void testExistsById() {
    Mockito.when(repository.existsById(Mockito.anyInt())).thenReturn(true);
    boolean isPresent = cut.existsById(1);
    Mockito.verify(repository).existsById(Mockito.anyInt());
    Assertions.assertThat(isPresent).isTrue();
  }

  @Test
  void testSaveThrowException() {
    Mockito.when(repository.save(Mockito.any(Representative.class))).thenThrow(new IllegalArgumentException());
    Representative representative = new Representative();
    assertThrows(IllegalArgumentException.class, () -> cut.save(representative));
  }

  @Test
  void testSaveRegular() {
    Mockito.when(repository.save(Mockito.any(Representative.class))).thenReturn(new Representative());
    Representative representative = cut.save(new Representative());
    Mockito.verify(repository).save(Mockito.any(Representative.class));
    Assertions.assertThat(representative).isNotNull();
  }

  @Test
  void testDeleteByIdRegular() {
    Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());
    cut.deleteById(1);
    Mockito.verify(repository).deleteById(Mockito.anyInt());
  }
}