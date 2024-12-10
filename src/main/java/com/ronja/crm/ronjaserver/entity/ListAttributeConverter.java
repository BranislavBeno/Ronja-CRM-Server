package com.ronja.crm.ronjaserver.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class ListAttributeConverter implements AttributeConverter<List<Contact>, String> {

  private static final Logger logger = LoggerFactory.getLogger(ListAttributeConverter.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<Contact> attribute) {
    String dbData = null;
    try {
      dbData = objectMapper.writeValueAsString(attribute);
    } catch (final JsonProcessingException e) {
      logger.error("JSON writing error", e);
    }
    return dbData;
  }

  @Override
  public List<Contact> convertToEntityAttribute(String dbData) {
    List<Contact> attribute = null;
    try {
      attribute = objectMapper.readValue(dbData, new TypeReference<>() {
      });
    } catch (final IOException e) {
      logger.error("JSON reading error", e);
    }
    return attribute;
  }
}
