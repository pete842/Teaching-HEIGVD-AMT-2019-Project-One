package ch.heigvd.amt.projectone.model.entities;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class MediaUser {
    private Integer id;
    private User user;
    private Media media;
    private Integer rating;
    private Timestamp watched;
}

