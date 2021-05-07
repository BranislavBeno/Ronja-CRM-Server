package com.ronja.crm.ronjaserver.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.List;

public class ListAttributeConverter implements AttributeConverter<List<String>, String> {

  private static final Logger logger = LoggerFactory.getLogger(ListAttributeConverter.class);

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<String> attribute) {
    String dbData = null;
    try {
      dbData = objectMapper.writeValueAsString(attribute);
    } catch (final JsonProcessingException e) {
      logger.error("JSON writing error", e);
    }
    return dbData;
  }

  @Override
  public List<String> convertToEntityAttribute(String dbData) {
    List<String> attribute = null;
    try {
      //noinspection unchecked
      attribute = objectMapper.readValue(dbData, List.class);
    } catch (final IOException e) {
      logger.error("JSON reading error", e);
    }
    return attribute;
  }
}
