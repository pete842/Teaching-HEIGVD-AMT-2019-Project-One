package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.services.dao.extractor.SQLExtractor;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class MediaUserDAO implements MediaUserDAOLocal {
    @Resource(lookup = "java:/jdbc/libmovie")
    private DataSource dataSource;

    private List<MediaUser> findAllByUserTypedPaged(Integer userId, boolean watched, Integer pageNumber, Integer pageSize) {
        List<MediaUser> result = new LinkedList<>();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT media_user.id, media_user.user_id, media_user.media_id, media_user.`rating` AS `personnal_rating`, media_user.watched, users.*, medias.* FROM media_user INNER JOIN medias ON media_id = medias.id INNER JOIN users ON user_id = users.id WHERE user_id = ? AND watched " + ((watched)? "IS NOT" : "IS") + " NULL LIMIT ? OFFSET ?");

            ps.setInt(1, userId);
            ps.setInt(2, pageSize);
            ps.setInt(3, (pageNumber-1) * pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(SQLExtractor.extractMediaUser(rs));
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private Integer countAllByUserTyped(Integer userId, boolean watched) {
        Integer result = 0;
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT count(*) FROM media_user WHERE user_id = ? AND watched " + ((watched)? "IS NOT" : "IS") + " NULL");
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public Integer countAllWatchedByUser(Integer userId) {
        return countAllByUserTyped(userId, true);
    }

    @Override
    public Integer countAllToWatchByUser(Integer userId) {
        return countAllByUserTyped(userId, false);
    }

    @Override
    public List<MediaUser> findAllWatchedByUserPaged(Integer userId, Integer pageNumber, Integer pageSize) {
        return findAllByUserTypedPaged(userId, true, pageNumber, pageSize);
    }

    @Override
    public List<MediaUser> findAllToWatchByUserPaged(Integer userId, Integer pageNumber, Integer pageSize) {
        return findAllByUserTypedPaged(userId, false, pageNumber, pageSize);

    }

    @Override
    public MediaUser get(Integer userId, Integer mediaId) {
        MediaUser result = null;
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT media_user.id, media_user.user_id, media_user.media_id, media_user.`rating` AS `personnal_rating`, media_user.watched, users.*, medias.* FROM media_user INNER JOIN medias ON media_id = medias.id INNER JOIN users ON user_id = users.id WHERE user_id = ? AND media_id = ?");

            ps.setInt(1, userId);
            ps.setInt(2, mediaId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result = SQLExtractor.extractMediaUser(rs);
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public MediaUser create(MediaUser mediaUser) throws SQLException {
        MediaUser result = null;

        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO media_user (media_id, user_id, `rating`, watched) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, mediaUser.getMedia().getId());
            ps.setInt(2, mediaUser.getUser().getId());

            if (mediaUser.getRating() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, mediaUser.getRating());
            }

            if (mediaUser.getWatched() == null) {
                ps.setNull(4, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(4, mediaUser.getWatched());
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next())
            {
                result = mediaUser.toBuilder().id(rs.getInt(1)).build();
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public boolean update(MediaUser mediaUser) {
        int row = 0;
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE media_user SET `rating` = ?, `watched` = ? WHERE id = ?");
            ps.setInt(1, mediaUser.getRating());
            ps.setTimestamp(2, mediaUser.getWatched());
            ps.setInt(3, mediaUser.getId());

            row = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (row > 0);
    }

    @Override
    public boolean delete(MediaUser mediaUser) {
        int row = 0;
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM media_user WHERE id = ?");
            ps.setInt(1, mediaUser.getId());

            row = ps.executeUpdate();

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaUserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (row > 0);
    }
}
