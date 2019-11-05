package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.Media;
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
public class MediaDAO implements MediaDAOLocal {
    @Resource(lookup = "java:/jdbc/libmovie")
    private DataSource dataSource;

    @Override
    public List<Media> findAllWithJoinInfoPaged(Integer userId, Integer pageNumber, Integer pageSize) {
        List<Media> result = new LinkedList<>();
        try {
            Connection con = dataSource.getConnection();

            PreparedStatement ps = con.prepareStatement("SELECT medias.*, CASE WHEN media_user.id IS NULL THEN false ELSE true END AS inserted, CASE WHEN media_user.watched IS NULL THEN false ELSE true END AS watched FROM medias LEFT JOIN media_user ON media.id = media_user.media_id WHERE user_id IS NULL OR user_id = ? LIMIT ? OFFSET ?");
            ps.setInt(1, userId);
            ps.setInt(2, pageSize);
            ps.setInt(3, (pageNumber - 1) * pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Media media = SQLExtractor.extractMedia(rs);
                result.add(media.toBuilder()
                        .inserted(rs.getBoolean("inserted"))
                        .watched(rs.getBoolean("watched")).build());
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Integer countAll() {
        int result = 0;
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT count(*) FROM medias");

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
}
