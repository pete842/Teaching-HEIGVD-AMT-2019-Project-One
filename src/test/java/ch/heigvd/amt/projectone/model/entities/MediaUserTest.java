package ch.heigvd.amt.projectone.model.entities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MediaUserTest {
    @Test
    void itShouldBePossibleToCreateMediaUser() {
        MediaUser jj = MediaUser.builder()
                .user(
                    User.builder()
                        .username("jzaehrin")
                        .firstName("Jonathan")
                        .lastName("Zaehringer")
                        .email("jonathan.zaehringer@gmail.com")
                        .memberSince(new Timestamp(0))
                        .build()
                )
                .media(
                    Media.builder()
                        .title("Joker")
                        .release(new Timestamp(0))
                        .duration(120)
                        .mainGenre("Drama")
                        .rating(10)
                        .build()
                )
                .rating(10)
                .watched(new Timestamp(0))
                .build();

        assertNotNull(jj);
        assertEquals(10, jj.getRating());
    }
}