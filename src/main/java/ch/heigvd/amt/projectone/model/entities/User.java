package ch.heigvd.amt.projectone.model.entities;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder=true)
@Getter
@EqualsAndHashCode
public class User {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Timestamp memberSince;
}
