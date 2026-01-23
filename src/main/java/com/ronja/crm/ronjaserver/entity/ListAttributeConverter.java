package com.ronja.crm.ronjaserver.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

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
        } catch (final JacksonException e) {
            logger.error("JSON writing error", e);
        }
        return dbData;
    }

    @Override
    public List<Contact> convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, new TypeReference<>() {});
    }
}
