package com.ronja.crm.ronjaserver.config;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public
class ContainersConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    public MySQLContainer<?> mySqlContainer() {
        return new MySQLContainer<>("mysql:8.0.34");
    }

    @Bean
    @RestartScope
    public MockServerContainer mockServerContainer(DynamicPropertyRegistry registry) {
        MockServerContainer container = new MockServerContainer(DockerImageName.parse("mockserver/mockserver"));
        container.start();

        String url = "http://%s:%s/api/latest?base=USD&symbols=LME-ALU,LME-XCU,LME-LEAD&access_key="
                .formatted(container.getHost(), container.getServerPort());
        registry.add("client.metal.api.url", () -> url);
        registry.add("client.metal.api.access-key", () -> "private-token");

        return container;
    }
}
