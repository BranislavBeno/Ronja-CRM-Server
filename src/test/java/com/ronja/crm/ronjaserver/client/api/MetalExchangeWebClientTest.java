package com.ronja.crm.ronjaserver.client.api;

import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
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
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        this.webClient = new MetalExchangeWebClient(mockWebServer.url("/").toString(), "");
    }

    @Test
    void testSuccessfulResponse() throws InterruptedException {
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(validResponse);
        this.mockWebServer.enqueue(mockResponse);

        MetalExchange metalExchange = webClient.fetchExchangeData();

        assertValidData(metalExchange);

        RecordedRequest recordedRequest = this.mockWebServer.takeRequest();
        assertThat(recordedRequest.getPath()).isEqualTo("/");
    }

    @Test
    void testIncompleteSuccessfulResponse() {
        String response = """
                {
                  "success": true,
                  "rates": {
                    "LME-ALU": 10.573385811699,
                    "LME-XCU": 3.256136987247
                  }
                }""";

        this.mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(response));

        MetalExchange metalExchange = webClient.fetchExchangeData();

        assertIncompleteValidData(metalExchange);
    }

    @Test
    void testFailingResponse() {
        Assertions.assertThrows(RuntimeException.class, this::fetchMockedData);
    }

    @Test
    void testSuccessfulResponseWhenRemoteSystemIsSlowOrFailing() {
        this.mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("System failure!"));
        this.mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(validResponse)
                .setBodyDelay(2, TimeUnit.SECONDS));
        this.mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(validResponse));

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

    private void assertIncompleteValidData(MetalExchange metalExchange) {
        assertThat(metalExchange).isNotNull();
        assertThat(metalExchange.success()).isTrue();
        assertThat(metalExchange.rates().aluminum()).isEqualTo(new BigDecimal("10.573385811699"));
        assertThat(metalExchange.rates().copper()).isEqualTo(new BigDecimal("3.256136987247"));
        assertThat(metalExchange.rates().lead()).isNull();
        assertThat(metalExchange.currency()).isNull();
        assertThat(metalExchange.date()).isNull();
    }

    private void fetchMockedData() {
        this.mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("System failure!"));
        webClient.fetchExchangeData();
    }
}
