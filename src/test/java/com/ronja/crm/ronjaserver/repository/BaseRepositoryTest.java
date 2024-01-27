package com.ronja.crm.ronjaserver.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class BaseRepositoryTest {

    @ServiceConnection
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"));

    static {
        MY_SQL_CONTAINER.start();
    }
}
