package com.ronja.crm.ronjaserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RonjaServerApplicationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void contextLoads() {
    ResponseEntity<Object> actuatorResult =
        this.testRestTemplate.getForEntity("/actuator/health", Object.class);
    assertThat(actuatorResult.getStatusCodeValue()).isEqualTo(200);
  }
}