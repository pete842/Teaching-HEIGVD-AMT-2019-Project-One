package ch.heigvd.amt.projectone.model;

import java.util.Date;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class MediaUser {
    private User user;
    private Media media;
    private Float rating;
    private Date watched;
}
