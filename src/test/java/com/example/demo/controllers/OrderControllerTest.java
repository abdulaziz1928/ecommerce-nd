package com.example.demo.controllers;

import com.example.demo.AbstractRestTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderControllerTest extends AbstractRestTest {

    @Override
    protected String getPath() {
        return "/api/order";
    }

    @Test
    void shouldSubmitAndGetOrderSuccessfully() {
        String username = "test";
        String path = "submit/" + username;
        given()
                .contentType(ContentType.JSON)
                .when()
                .post(withPath(path))
                .then()
                .statusCode(200)
                .body(not(empty()))
                .log()
                .all();

        path = "history/" + username;
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath(path))
                .then()
                .statusCode(200)
                .body(not(empty()))
                .log()
                .all();
    }

    @Test
    void shouldFailToSubmitOrder() {
        String path = "submit/" + "asdasdasd";
        given()
                .contentType(ContentType.JSON)
                .when()
                .post(withPath(path))
                .then()
                .statusCode(404)
                .log()
                .all();
    }

    @Test
    void shouldFailToGetUserOrder() {
        String path = "history/" + "asdasdasd";
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath(path))
                .then()
                .statusCode(404)
                .log()
                .all();
    }
}
