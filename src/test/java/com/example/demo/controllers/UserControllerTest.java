package com.example.demo.controllers;

import com.example.demo.AbstractRestTest;
import com.example.demo.TestUtil;
import com.example.demo.model.requests.CreateUserRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.given;


public class UserControllerTest extends AbstractRestTest {
    @Override
    protected String getPath() {
        return "/api/user";
    }

    @Test
    void shouldCreateAndFetchUserSuccessfully() {
        CreateUserRequest user = TestUtil.createUser();

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(withPath("create"))
                .then()
                .statusCode(200)
                .log()
                .all();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath("id/2"))
                .then()
                .statusCode(200)
                .log()
                .all();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(withPath(user.getUsername()))
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    void shouldFailToCreateUserWhenUserAlreadyExists() {
        CreateUserRequest user = TestUtil.createUser();
        user.setUsername("userExist");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(withPath("create"))
                .then()
                .statusCode(200)
                .log()
                .all();
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(withPath("create"))
                .then()
                .statusCode(400)
                .log()
                .all();

    }

    @Test
    void shouldFailToCreateUserWhenPasswordsMismatch() {
        CreateUserRequest user = TestUtil.createUser();
        user.setUsername("pmm");
        user.setConfirmPassword("1234abcd");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(withPath("create"))
                .then()
                .statusCode(400)
                .log()
                .all();
    }

    @Test
    void shouldFailToCreateUserWhenPasswordIsInvalid() {
        CreateUserRequest user = TestUtil.createUser();
        user.setUsername("pmm");
        user.setPassword("123");
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(withPath("create"))
                .then()
                .statusCode(400)
                .log()
                .all();
    }


}
