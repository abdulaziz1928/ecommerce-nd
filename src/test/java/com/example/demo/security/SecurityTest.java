package com.example.demo.security;

import com.example.demo.AbstractTest;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.profiles.active=test", "spring.sql.init.mode=never"}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class SecurityTest extends AbstractTest {

    @Autowired
    private UserRepository userRepository;
    private static final String userName = "abcd1234";

    private Map<String, String> body;

    @BeforeAll
    protected void setup() {
        super.setup();

        User user = new User();
        user.setUsername(userName);
        user.setPassword("$2a$10$w8rng4kcJU6VUwOngH4.nuJXl6RDOXUtCC9cdCYA3JSpagbQILKYi");
        user.setCart(new Cart());

        userRepository.save(user);
    }

    @BeforeEach
    protected void setupUser() {
        body = new HashMap<>();
        body.put("username", userName);
        body.put("password", "somepassword");
    }

    @Test
    void shouldLoginSuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    void shouldUnAuthorizeUnAuthenticatedUsers() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/user/1")
                .then()
                .statusCode(401)
                .log()
                .all();
    }

    @Test
    void shouldAuthorizeAuthenticatedUsers() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/login");

        String token = response.getHeader("Authorization");
        assertNotNull(token);

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("/api/user/id/1")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    void shouldFailToLogin() {
        body.put("password", "aaaaaaaaa");

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/login")
                .then()
                .statusCode(401)
                .log()
                .all();
    }
}
