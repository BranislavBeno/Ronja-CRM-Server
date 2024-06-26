package com.ronja.crm.ronjaserver.repository;

import com.ronja.crm.ronjaserver.entity.MetalData;
import jakarta.validation.ValidationException;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class MetalDataRepositoryTest extends BaseRepositoryTest implements WithAssertions {

    @Autowired
    private MetalDataRepository repository;

    @Test
    @Sql(scripts = "/scripts/INIT_METAL_DATA.sql")
    void testFindAll() {
        Iterable<MetalData> result = repository.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    @Sql(scripts = "/scripts/INIT_METAL_DATA.sql")
    void testSave() {
        MetalData data = new MetalData();
        data.setCurrency("EUR");

        MetalData responseData = repository.save(data);
        Iterable<MetalData> result = repository.findAll();

        assertThat(responseData.getId()).isGreaterThan(1);
        assertThat(result).hasSize(2);
    }

    @Test
    void testFailingSave() {
        MetalData data = new MetalData();
        Assertions.assertThrows(ValidationException.class, () -> repository.save(data));
    }
}