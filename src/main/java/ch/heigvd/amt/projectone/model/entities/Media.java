package ch.heigvd.amt.projectone.model.entities;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Media {
    private long id;
    private String title;
    private Timestamp release;
    private Integer duration;
    private String mainGenre;
    private Integer rating;
}
