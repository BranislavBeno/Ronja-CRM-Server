package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.client.api.MetalExchangeWebClient;
import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.entity.MetalData;
import com.ronja.crm.ronjaserver.repository.MetalDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetalDataServiceTest {

  @Mock
  private MetalDataRepository repository;
  @Mock
  private MetalExchangeWebClient webClient;
  @Mock
  private MetalDataMapper mapper;
  @Mock
  private MetalExchange metalExchange;
  @Mock
  private MetalData metalData;
  @InjectMocks
  private MetalDataService cut;

  @Test
  void testFindAllReturnsList() {
    when(repository.findAll()).thenReturn(List.of(new MetalData()));
    Iterable<MetalData> data = cut.findAll();
    verify(repository).findAll();
    assertThat(data).isNotEmpty();
  }

  @Test
  void testFailingFindAll() {
    when(repository.findAll()).thenThrow(RuntimeException.class);
    assertThrows(RuntimeException.class, () -> cut.findAll());
  }

  @Test
  void testFetchingExchangeDataThrowException() {
    when(webClient.fetchExchangeData()).thenThrow(RuntimeException.class);
    assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testFetchNullExchangeData() {
    when(webClient.fetchExchangeData()).thenReturn(null);
    when(mapper.toEntity(metalExchange)).thenReturn(metalData);
    assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testConvertToMetalDataThrowsException() {
    when(webClient.fetchExchangeData()).thenReturn(null);
    when(mapper.toEntity(metalExchange)).thenThrow(RuntimeException.class);
    assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testConvertToMetalDataReturnsNull() {
    when(webClient.fetchExchangeData()).thenReturn(null);
    when(mapper.toEntity(metalExchange)).thenReturn(null);
    assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testExchangeMetalDataThrowsException() {
    when(webClient.fetchExchangeData()).thenReturn(metalExchange);
    when(mapper.toEntity(metalExchange)).thenReturn(metalData);
    when(repository.save(metalData)).thenThrow(RuntimeException.class);
    assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testExchangeMetalDataFinishesSuccessfully() {
    // given
    when(webClient.fetchExchangeData()).thenReturn(metalExchange);
    when(mapper.toEntity(metalExchange)).thenReturn(metalData);
    when(repository.save(metalData)).thenReturn(new MetalData());
    // when
    cut.exchange();
    // then
    verify(webClient).fetchExchangeData();
    verify(mapper).toEntity(metalExchange);
    verify(repository).save(metalData);
  }
}