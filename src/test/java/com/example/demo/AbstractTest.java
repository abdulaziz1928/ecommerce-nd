package com.example.demo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractTest {
    @LocalServerPort
    protected int port;

    @BeforeAll
    protected void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

}
