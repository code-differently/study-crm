package com.codedifferently.studycrm.auth.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void someLibraryMethodReturnsTrue() {
        User classUnderTest = new User();
        assertTrue(classUnderTest.isAccountEnabled(), "getIsAccountEnabled should return 'true'");
    }
}
