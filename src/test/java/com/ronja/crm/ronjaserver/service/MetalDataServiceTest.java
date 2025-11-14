package com.ronja.crm.ronjaserver.service;

import com.ronja.crm.ronjaserver.client.api.MetalExchangeWebClient;
import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import com.ronja.crm.ronjaserver.dto.MetalDataMapper;
import com.ronja.crm.ronjaserver.entity.MetalData;
import com.ronja.crm.ronjaserver.repository.MetalDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
    Mockito.when(repository.findAll()).thenReturn(List.of(new MetalData()));
    Iterable<MetalData> data = cut.findAll();
    Mockito.verify(repository).findAll();
    org.assertj.core.api.Assertions.assertThat(data).isNotEmpty();
  }

  @Test
  void testFailingFindAll() {
    Mockito.when(repository.findAll()).thenThrow(RuntimeException.class);
    Assertions.assertThrows(RuntimeException.class, () -> cut.findAll());
  }

  @Test
  void testFetchingExchangeDataThrowException() {
    Mockito.when(webClient.fetchExchangeData()).thenThrow(RuntimeException.class);
    Assertions.assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testFetchNullExchangeData() {
    Mockito.when(webClient.fetchExchangeData()).thenReturn(null);
    Mockito.when(mapper.toEntity(metalExchange)).thenReturn(metalData);
    Assertions.assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testConvertToMetalDataThrowsException() {
    Mockito.when(webClient.fetchExchangeData()).thenReturn(null);
    Mockito.when(mapper.toEntity(metalExchange)).thenThrow(RuntimeException.class);
    Assertions.assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testConvertToMetalDataReturnsNull() {
    Mockito.when(webClient.fetchExchangeData()).thenReturn(null);
    Mockito.when(mapper.toEntity(metalExchange)).thenReturn(null);
    Assertions.assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testExchangeMetalDataThrowsException() {
    Mockito.when(webClient.fetchExchangeData()).thenReturn(metalExchange);
    Mockito.when(mapper.toEntity(metalExchange)).thenReturn(metalData);
    Mockito.when(repository.save(metalData)).thenThrow(RuntimeException.class);
    Assertions.assertThrows(RuntimeException.class, () -> cut.exchange());
  }

  @Test
  void testExchangeMetalDataFinishesSuccessfully() {
    // given
    Mockito.when(webClient.fetchExchangeData()).thenReturn(metalExchange);
    Mockito.when(mapper.toEntity(metalExchange)).thenReturn(metalData);
    Mockito.when(repository.save(metalData)).thenReturn(new MetalData());
    // when
    cut.exchange();
    // then
    Mockito.verify(webClient).fetchExchangeData();
    Mockito.verify(mapper).toEntity(metalExchange);
    Mockito.verify(repository).save(metalData);
  }
}