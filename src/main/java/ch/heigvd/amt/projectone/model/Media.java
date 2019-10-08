package ch.heigvd.amt.projectone.model;

import java.sql.Time;
import java.util.Date;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Media {
    private String title;
    private Date release;
    private Time duration;
    private String mainGenre;
    private Float rating;
}
