package com.example.demo;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestConfig.class,
        properties = {"spring.profiles.active=test"}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public abstract class AbstractRestTest extends AbstractTest {


    protected abstract String getPath();

    protected String withPath() {
        return withPath(null);
    }

    protected String withPath(String path) {
        if (path != null)
            return getPath().concat("/").concat(path);
        return getPath();
    }
}
