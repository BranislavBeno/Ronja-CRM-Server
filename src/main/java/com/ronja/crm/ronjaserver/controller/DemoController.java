package com.ronja.crm.ronjaserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DemoController {

  @GetMapping("/hello")
  public LocalDateTime sayHello() {
    return LocalDateTime.now();
  }
}
