package com.example.demo.controllers;

import com.example.demo.AbstractRestTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ItemControllerTest extends AbstractRestTest {
    @Override
    protected String getPath() {
        return "/api/item";
    }

    @Test
    void shouldGetAllItemsSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath())
                .then()
                .statusCode(200)
                .body(not(empty()))
                .log()
                .all();
    }

    @Test
    void shouldGetItemByIdSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath("1"))
                .then()
                .statusCode(200)
                .body(not(empty()))
                .log()
                .all();
    }

    @Test
    void shouldFailToGetItemById() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath("99"))
                .then()
                .statusCode(404)
                .log()
                .all();
    }

    @Test
    void shouldGetItemsByNameSuccessfully() {
        String path = "name/" + "Round Widget";
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
    void shouldFailToGetItemsByName() {
        String path = "name/" + "abcd";
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
