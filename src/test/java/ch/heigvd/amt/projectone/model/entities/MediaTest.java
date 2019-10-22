package ch.heigvd.amt.projectone.model.entities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MediaTest {

    @Test
    void itShouldBePossibleToCreateMedia() {
        Media joker = Media.builder()
                .title("Joker")
                .release(new Timestamp(0))
                .duration(120)
                .mainGenre("Drama")
                .rating(10)
                .build();

        assertNotNull(joker);
        assertEquals("Joker", joker.getTitle());
    }
}