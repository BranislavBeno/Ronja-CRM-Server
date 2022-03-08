package com.ronja.crm.ronjaserver.client.api;

import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MetalExchangeWebClientTest {

    private static String VALID_RESPONSE;

    static {
        try {
            VALID_RESPONSE = new String(Objects.requireNonNull(
                    MetalExchangeWebClientTest.class
                        .getClassLoader()
                        .getResourceAsStream("payload/response.json"))
                .readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
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
                .setBody(VALID_RESPONSE);
        this.mockWebServer.enqueue(mockResponse);

        MetalExchange metalExchange = webClient.fetchExchangeData();

        assertValidData(metalExchange);

        RecordedRequest recordedRequest = this.mockWebServer.takeRequest();
        assertEquals("/", recordedRequest.getPath());
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
        assertThrows(RuntimeException.class, this::fetchMockedData);
    }

    @Test
    void testSuccessfulResponseWhenRemoteSystemIsSlowOrFailing() {
        this.mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("System failure!"));
        this.mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(VALID_RESPONSE)
                .setBodyDelay(2, TimeUnit.SECONDS));
        this.mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(VALID_RESPONSE));

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
