package com.ronja.crm.ronjaserver.client.api;

import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import mockwebserver3.MockResponse;
import mockwebserver3.MockWebServer;
import mockwebserver3.RecordedRequest;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

class MetalExchangeWebClientTest implements WithAssertions {

    private static String validResponse;

    static {
        try {
            validResponse = new String(Objects.requireNonNull(
                            MetalExchangeWebClientTest.class
                                    .getClassLoader()
                                    .getResourceAsStream("payload/response.json"))
                    .readAllBytes());
        } catch (IOException e) {
            Logger logger = LoggerFactory.getLogger(MetalExchangeWebClientTest.class);
            logger.error(e.getMessage());
        }
    }

    private MockWebServer mockWebServer;
    private MetalExchangeWebClient webClient;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        webClient = new MetalExchangeWebClient(mockWebServer.url("/").toString(), "");
    }

    @Test
    void testSuccessfulResponse() throws InterruptedException {
        MockResponse mockResponse = new MockResponse(200, new Headers(new String[]{"Content-Type", "application/json"}), validResponse);
        mockWebServer.enqueue(mockResponse);

        MetalExchange metalExchange = webClient.fetchExchangeData();

        assertValidData(metalExchange);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        HttpUrl url = recordedRequest.getUrl();
        assertThat(url.pathSegments()).containsExactly("");
        assertThat(url.encodedQuery()).isNull();
    }

    @Test
    void testIncompleteResponse() {
        Assertions.assertThrows(RuntimeException.class, this::mockIncompleteResponse);
    }

    @Test
    void testFailingResponse() {
        Assertions.assertThrows(RuntimeException.class, this::mockWrongResponse);
    }

    @Test
    void testSuccessfulResponseWhenRemoteSystemIsSlowOrFailing() {
        mockWebServer.enqueue(new MockResponse(500, new Headers(new String[]{}), "System failure!"));
        MockResponse response = new MockResponse.Builder()
                .code(200)
                .headers(new Headers.Builder()
                        .add("Content-Type", "application/json")
                        .build())
                .body(validResponse)
                .bodyDelay(2, TimeUnit.SECONDS)
                .build();
        mockWebServer.enqueue(response);
        mockWebServer.enqueue(new MockResponse(200, new Headers(new String[]{"Content-Type", "application/json"}), validResponse));

        MetalExchange metalExchange = webClient.fetchExchangeData();

        assertThat(metalExchange).isNotNull();
        assertThat(metalExchange.success()).isTrue();
    }

    private void assertValidData(MetalExchange metalExchange) {
        assertThat(metalExchange).isNotNull();
        assertThat(metalExchange.success()).isTrue();
        assertThat(metalExchange.rates().aluminum()).isEqualTo(new BigDecimal("10.573385811699"));
        assertThat(metalExchange.rates().copper()).isEqualTo(new BigDecimal("3.256136987247"));
        assertThat(metalExchange.rates().lead()).isEqualTo(new BigDecimal("14.319008911883"));
        assertThat(metalExchange.currency()).isEqualTo("USD");
        assertThat(metalExchange.date()).isBeforeOrEqualTo(LocalDate.now());
    }

    private void mockIncompleteResponse() {
        String response = """
                {
                  "success": true,
                  "rates": {
                    "LME-ALU": 10.573385811699,
                    "LME-XCU": 3.256136987247
                  }
                }""";

        mockWebServer.enqueue(
                new MockResponse(500, new Headers(new String[]{"Content-Type", "application/json"}), response));
        webClient.fetchExchangeData();
    }

    private void mockWrongResponse() {
        mockWebServer.enqueue(new MockResponse(500, new Headers(new String[]{}), "System failure!"));
        webClient.fetchExchangeData();
    }
}
