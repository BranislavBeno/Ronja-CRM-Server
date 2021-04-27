package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.dto.RepresentativeDto;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepresentativeServiceTest {

  @Mock
  private RepresentativeRepository repository;
  @Mock
  private RepresentativeDto dto;
  @Mock
  private Representative entity;

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
  void testFindByIdThrowException() {
    when(repository.findById(anyInt())).thenThrow(new RuntimeException());
    assertThatThrownBy(() -> cut.findById(1)).hasMessage(null);
  }

  @Test
  void testFindByIdRegular() {
    when(repository.findById(anyInt())).thenReturn(Optional.of(new Representative()));
    Representative representative = cut.findById(1);
    verify(repository).findById(anyInt());
    assertThat(representative.toString()).hasToString("Representative[firstName=null, lastName=null, position=null, "
        + "region=null, notice=null, status=null, lastVisit=null, scheduledVisit=null]");
  }

  @Test
  void testSaveThrowException() {
    when(repository.save(any(Representative.class))).thenThrow(new IllegalArgumentException());
    assertThrows(IllegalArgumentException.class, () -> cut.save(dto));
  }

  @Test
  void testSaveRegular() {
    when(repository.save(any(Representative.class))).thenReturn(new Representative());
    Representative representative = cut.save(dto);
    verify(repository).save(any(Representative.class));
    assertThat(representative).isNotNull();
  }

  @Test
  void testUpdateRegular() {
    when(repository.findById(anyInt())).thenReturn(Optional.of(entity));
    when(repository.save(any(Representative.class))).thenReturn(entity);
    Representative representative = cut.update(dto);
    verify(repository).save(entity);
    assertThat(representative).isNotNull();
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
    List<Representative> customers = cut.searchBy("Mike", "Stephens");
    verify(repository).findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString());
    assertThat(customers).isNull();
  }

  @Test
  void testSearchByReturnList() {
    when(repository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString()))
        .thenReturn(List.of(new Representative()));
    List<Representative> representatives = cut.searchBy("Mike", "Collins");
    verify(repository).findByFirstNameContainsOrLastNameContainsAllIgnoreCase(anyString(), anyString());
    assertThat(representatives).hasSize(1);
  }

  @Test
  void testSearchEmptyByReturnList() {
    when(repository.findAllByOrderByLastNameAsc())
        .thenReturn(List.of(new Representative()));
    List<Representative> customers = cut.searchBy("", "");
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(customers).hasSize(1);
  }

  @Test
  void testSearchNullByReturnList() {
    when(repository.findAllByOrderByLastNameAsc())
        .thenReturn(List.of(new Representative()));
    List<Representative> customers = cut.searchBy(null, null);
    verify(repository).findAllByOrderByLastNameAsc();
    assertThat(customers).hasSize(1);
  }
}