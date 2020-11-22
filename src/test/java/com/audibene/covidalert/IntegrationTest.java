package com.audibene.covidalert;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@TestInstance(PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    protected final ClientAndServer mockServerClient = startClientAndServer(1080);

    @LocalServerPort
    Integer applicationPort;

    @BeforeEach
    void setup() {
        RestAssured.port = applicationPort;
    }

    @AfterEach
    void cleanup() {
        mockServerClient.reset();
    }

    @AfterAll
    void afterAll() {
        mockServerClient.stop();
    }
}
