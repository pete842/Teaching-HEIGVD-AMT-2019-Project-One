package ch.heigvd.amt.projectone.model.entities;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class MediaUser {
    private long id;
    private User user;
    private Media media;
    private Integer rating;
    private Timestamp watched;
}
