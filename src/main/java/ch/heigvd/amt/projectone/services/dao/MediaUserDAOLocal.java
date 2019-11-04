package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.MediaUser;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MediaUserDAOLocal {
    public List<MediaUser> findAllToWatchByUserPaged(Integer userId, Integer pageNumber, Integer pageSize);
    public Integer countAllToWatchByUser(Integer userId);

    public List<MediaUser> findAllWatchedByUserPaged(Integer userId, Integer pageNumber, Integer pageSize);
    public Integer countAllWatchedByUser(Integer userId);

}
