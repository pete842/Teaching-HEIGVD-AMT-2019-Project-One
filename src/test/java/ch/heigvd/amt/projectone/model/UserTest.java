package ch.heigvd.amt.projectone.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void itShouldBePossibleToCreateUser() {
        User jzaehrin = User.builder()
                .username("jzaehrin")
                .firstName("Jonathan")
                .lastName("Zaehringer")
                .email("jonathan.zaehringer@gmail.com")
                .memberSince(new Date())
                .build();

        assertNull(jzaehrin);
        assertEquals("jzaehrin", jzaehrin.getUsername());
    }
}