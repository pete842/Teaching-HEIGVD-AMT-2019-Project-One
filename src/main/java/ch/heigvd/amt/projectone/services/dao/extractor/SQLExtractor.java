package ch.heigvd.amt.projectone.services.dao.extractor;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Wrapper;

public class SQLExtractor {
    public static User extractUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .username(rs.getString("username"))
                .lastName(rs.getString("lastname"))
                .firstName(rs.getString("firstname"))
                .email(rs.getString("email"))
                .memberSince(rs.getTimestamp("member_since")).build();
    }

    public static Media extractMedia(ResultSet rs) throws SQLException {
        return Media.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .release(rs.getTimestamp("release"))
                .duration(rs.getInt("duration"))
                .mainGenre(rs.getString("main_genre"))
                .rating(rs.getInt("rating")).build();
    }

    public static MediaUser extractMediaUser(ResultSet rs) throws SQLException {
        return MediaUser.builder()
                .id(rs.getLong("user_id"))
                .user(extractUser(rs))
                .media(extractMedia(rs))
                .rating(rs.getInt("rating"))
                .watched(rs.getTimestamp("watched")).build();
    }
}
