package com.ronja.crm.ronjaserver.client.api;

import com.ronja.crm.ronjaserver.client.domain.MetalExchange;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

class MetalExchangeWebClientTest {

    private static String validResponse;

    static {
        try {
            validResponse = new String(Objects.requireNonNull(
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
                .setBody(validResponse);
        this.mockWebServer.enqueue(mockResponse);

        MetalExchange metalExchange = webClient.fetchExchangeData();

        assertValidData(metalExchange);

        RecordedRequest recordedRequest = this.mockWebServer.takeRequest();
        Assertions.assertEquals("/", recordedRequest.getPath());
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
                          }\
                          """;

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

        org.assertj.core.api.Assertions.assertThat(metalExchange).isNotNull();
        org.assertj.core.api.Assertions.assertThat(metalExchange.isSuccess()).isTrue();
    }

    private void assertValidData(MetalExchange metalExchange) {
        org.assertj.core.api.Assertions.assertThat(metalExchange).isNotNull();
        org.assertj.core.api.Assertions.assertThat(metalExchange.isSuccess()).isTrue();
        org.assertj.core.api.Assertions.assertThat(metalExchange.getRates().getAluminum()).isEqualTo(new BigDecimal("10.573385811699"));
        org.assertj.core.api.Assertions.assertThat(metalExchange.getRates().getCopper()).isEqualTo(new BigDecimal("3.256136987247"));
        org.assertj.core.api.Assertions.assertThat(metalExchange.getRates().getLead()).isEqualTo(new BigDecimal("14.319008911883"));
        org.assertj.core.api.Assertions.assertThat(metalExchange.getCurrency()).isEqualTo("USD");
        org.assertj.core.api.Assertions.assertThat(metalExchange.getDate()).isBeforeOrEqualTo(LocalDate.now());
    }

    private void assertIncompleteValidData(MetalExchange metalExchange) {
        org.assertj.core.api.Assertions.assertThat(metalExchange).isNotNull();
        org.assertj.core.api.Assertions.assertThat(metalExchange.isSuccess()).isTrue();
        org.assertj.core.api.Assertions.assertThat(metalExchange.getRates().getAluminum()).isEqualTo(new BigDecimal("10.573385811699"));
        org.assertj.core.api.Assertions.assertThat(metalExchange.getRates().getCopper()).isEqualTo(new BigDecimal("3.256136987247"));
        org.assertj.core.api.Assertions.assertThat(metalExchange.getRates().getLead()).isNull();
        org.assertj.core.api.Assertions.assertThat(metalExchange.getCurrency()).isNull();
        org.assertj.core.api.Assertions.assertThat(metalExchange.getDate()).isNull();
    }

    private void fetchMockedData() {
        this.mockWebServer.enqueue(new MockResponse()
            .setResponseCode(500)
            .setBody("System failure!"));
        webClient.fetchExchangeData();
    }
}
