package com.inghubs.casestudy;

import com.inghubs.casestudy.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SecurityConfigTest {

    @Test
    void contextLoads() {
        // This test ensures the application context loads without any issues
        assertNotNull(new SecurityConfig());
    }
}

