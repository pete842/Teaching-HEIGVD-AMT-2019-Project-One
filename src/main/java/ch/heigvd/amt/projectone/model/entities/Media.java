package ch.heigvd.amt.projectone.model.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Builder(toBuilder=true)
@Getter
@EqualsAndHashCode
public class Media {
    private Integer id;
    private String title;
    private Timestamp release;
    private Integer duration;
    private String mainGenre;
    private Integer rating;

    private Boolean inserted;
    private Boolean watched;
}
