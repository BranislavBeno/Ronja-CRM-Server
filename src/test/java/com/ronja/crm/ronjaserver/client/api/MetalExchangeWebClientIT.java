package com.ronja.crm.ronjaserver.client.api;

import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.utility.DockerImageName;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@DisabledIfSystemProperty(named = "os.arch", matches = "aarch64", disabledReason = "No ARM64 support")
class MetalExchangeWebClientIT {

    private static final MockServerContainer MOCK_SERVER = new MockServerContainer(DockerImageName.parse("mockserver/mockserver"));
    private static final String URL;
    private static final String ACCESS_KEY = "private-token";
    private static MetalExchangeWebClient webClient;

    static {
        MOCK_SERVER.start();
        URL = "http://%s:%s/api/latest?base=USD&symbols=LME-ALU,LME-XCU,LME-LEAD&access_key="
                .formatted(MOCK_SERVER.getHost(), MOCK_SERVER.getServerPort());
    }

    @BeforeAll
    static void setUpAll() {
        webClient = new MetalExchangeWebClient(URL, ACCESS_KEY);
    }

    @Test
    void testFetchData() throws IOException {
        String json = readResourceFile();
        try (MockServerClient mockServerClient = new MockServerClient(MOCK_SERVER.getHost(), MOCK_SERVER.getServerPort())) {
            mockResponse(mockServerClient, json);

            assertThat(webClient).isNotNull();
            MetalExchange metalExchange = webClient.fetchExchangeData();

            assertThat(metalExchange).isNotNull();
            assertThat(metalExchange.success()).isTrue();
            assertThat(metalExchange.rates().aluminum()).isEqualTo(new BigDecimal("10.573385811699"));
            assertThat(metalExchange.rates().copper()).isEqualTo(new BigDecimal("3.256136987247"));
            assertThat(metalExchange.rates().lead()).isEqualTo(new BigDecimal("14.319008911883"));
            assertThat(metalExchange.currency()).isEqualTo("USD");
            assertThat(metalExchange.date()).isBeforeOrEqualTo(LocalDate.now());
        }
    }

    private void mockResponse(MockServerClient mockClient, String json) {
        mockClient
                .when(HttpRequest.request()
                        .withMethod("GET"))
                .respond(new HttpResponse()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(json));
    }

    private String readResourceFile() throws IOException {
        File epicsFile = new ClassPathResource("/payload/response.json").getFile();
        return FileUtils.readFileToString(epicsFile, StandardCharsets.UTF_8);
    }
}