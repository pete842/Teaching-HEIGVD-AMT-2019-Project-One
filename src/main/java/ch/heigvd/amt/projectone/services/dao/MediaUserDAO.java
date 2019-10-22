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

    @Override
    public List<MediaUser> findAllByUser(Integer userId) {
        List<MediaUser> result = new LinkedList<>();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM media_user INNER JOIN medias ON media_id = medias.id INNER JOIN users ON user_id = users.id WHERE user_id = ?");
            ps.setInt(1, userId);

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
}
