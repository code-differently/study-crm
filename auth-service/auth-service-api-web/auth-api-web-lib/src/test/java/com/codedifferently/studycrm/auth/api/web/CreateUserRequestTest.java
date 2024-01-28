package com.codedifferently.studycrm.auth.api.web;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestTest {
    @Test
    void someLibraryMethodReturnsTrue() {
        CreateUserRequest classUnderTest = CreateUserRequest.builder()
                .username("someUsername")
                .build();
        assertEquals("someUsername", classUnderTest.getUsername(), "Username should return match");
    }
}
