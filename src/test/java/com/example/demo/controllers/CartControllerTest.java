package com.example.demo.controllers;

import com.example.demo.AbstractRestTest;
import com.example.demo.TestUtil;
import com.example.demo.model.requests.ModifyCartRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class CartControllerTest extends AbstractRestTest {
    @Override
    protected String getPath() {
        return "/api/cart";
    }

    @Test
    void shouldAddAndRemoveItemFromCartSuccessfully() {
        ModifyCartRequest cartRequest = TestUtil.createModifyCartRequest();
        given()
                .contentType(ContentType.JSON)
                .body(cartRequest)
                .when()
                .post(withPath("addToCart"))
                .then()
                .statusCode(200)
                .body(not(empty()))
                .log()
                .all();
        given()
                .contentType(ContentType.JSON)
                .body(cartRequest)
                .when()
                .post(withPath("removeFromCart"))
                .then()
                .statusCode(200)
                .body(not(empty()))
                .log()
                .all();
    }
    @Test
    void shouldFailToAddToCartWhenUserNotExists(){
        ModifyCartRequest cartRequest = TestUtil.createModifyCartRequest();
        cartRequest.setUsername("asdasda");
        given()
                .contentType(ContentType.JSON)
                .body(cartRequest)
                .when()
                .post(withPath("removeFromCart"))
                .then()
                .statusCode(404)
                .body(not(empty()))
                .log()
                .all();
    }
    @Test
    void shouldFailToAddToCartWhenItemNotExists(){
        ModifyCartRequest cartRequest = TestUtil.createModifyCartRequest();
        cartRequest.setItemId(-1L);
        given()
                .contentType(ContentType.JSON)
                .body(cartRequest)
                .when()
                .post(withPath("removeFromCart"))
                .then()
                .statusCode(404)
                .body(not(empty()))
                .log()
                .all();
    }
}
