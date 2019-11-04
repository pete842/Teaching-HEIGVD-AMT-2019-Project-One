package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.extractor.SQLExtractor;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    "SELECT * FROM media_user INNER JOIN medias ON media_id = medias.id INNER JOIN users ON user_id = users.id WHERE user_id = ? AND watched ? NULL OFFSET ? LIMIT ?");
            ps.setInt(1, userId);
            ps.setString(2, (watched)? "!=" : "=");
            ps.setInt(3, pageNumber * pageSize);
            ps.setInt(4, pageSize);

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

    private List<MediaUser> countAllByUserTyped(Integer userId, boolean watched) {
        List<MediaUser> result = new LinkedList<>();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT count(*) FROM media_user WHERE user_id = ? AND watched ? NULL");
            ps.setInt(1, userId);
            ps.setString(2, (watched)? "!=" : "=");

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

    @Override
    public List<MediaUser> totalAllWatchedByUser(Integer userId) {
        return countAllByUserTyped(userId, true);
    }

    @Override
    public List<MediaUser> totalAllToWatchByUser(Integer userId) {
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
}
