package ch.heigvd.amt.projectone.model.entities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void itShouldBePossibleToCreateUser() {
        User jzaehrin = User.builder()
                .username("jzaehrin")
                .firstName("Jonathan")
                .lastName("Zaehringer")
                .email("jonathan.zaehringer@gmail.com")
                .memberSince(new Timestamp(0))
                .build();

        assertNotNull(jzaehrin);
        assertEquals("jzaehrin", jzaehrin.getUsername());
    }
}