package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.MediaUser;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface MediaUserDAOLocal {
    public List<MediaUser> findAllToWatchByUserPaged(Integer userId, Integer pageNumber, Integer pageSize);
    public Integer countAllToWatchByUser(Integer userId);

    public List<MediaUser> findAllWatchedByUserPaged(Integer userId, Integer pageNumber, Integer pageSize);
    public Integer countAllWatchedByUser(Integer userId);

    public MediaUser get(Integer userId, Integer mediaId);
    public MediaUser create(MediaUser mediaUser) throws SQLException;
    public boolean update(MediaUser mediaUser);
    public boolean delete(MediaUser mediaUser);
}
