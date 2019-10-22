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
    public List<Media> findAll() {
        List<Media> result = new LinkedList<>();
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM medias");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(SQLExtractor.extractMedia(rs));
            }

            ps.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(MediaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
