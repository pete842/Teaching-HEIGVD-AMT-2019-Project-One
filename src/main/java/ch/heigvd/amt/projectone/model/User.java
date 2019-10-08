package ch.heigvd.amt.projectone.model;

import java.util.Date;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date memberSince;
}
