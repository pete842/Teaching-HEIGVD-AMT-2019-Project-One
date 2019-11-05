package ch.heigvd.amt.projectone.services.dao.extractor;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLExtractor {
    public static User extractUser(ResultSet rs) throws SQLException {
        return extractUser(rs, false);
    }

    public static User extractUser(ResultSet rs, boolean joined) throws SQLException {
        return User.builder()
                .id(rs.getInt((joined)? "user_id": "id"))
                .username(rs.getString("username"))
                .lastName(rs.getString("lastname"))
                .firstName(rs.getString("firstname"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .memberSince(rs.getTimestamp("member_since")).build();
    }

    public static Media extractMedia(ResultSet rs) throws SQLException {
        return extractMedia(rs, false);
    }

    public static Media extractMedia(ResultSet rs, boolean joined) throws SQLException {
        return Media.builder()
                .id(rs.getInt((joined)? "media_id": "id"))
                .title(rs.getString("title"))
                .release(rs.getTimestamp("release"))
                .duration(rs.getInt("duration"))
                .mainGenre(rs.getString("main_genre"))
                .rating(rs.getInt("rating")).build();
    }

    public static MediaUser extractMediaUser(ResultSet rs) throws SQLException {
        return MediaUser.builder()
                .id(rs.getInt("id"))
                .user(extractUser(rs, true))
                .media(extractMedia(rs, true))
                .rating(rs.getInt("personnal_rating"))
                .watched(rs.getTimestamp("watched")).build();
    }
}
