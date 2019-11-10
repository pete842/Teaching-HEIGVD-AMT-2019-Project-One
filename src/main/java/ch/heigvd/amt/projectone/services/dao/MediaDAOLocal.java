package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.Media;
import javax.ejb.Local;
import java.util.List;

@Local
public interface MediaDAOLocal {
    public List<Media> findAllWithJoinInfoPaged(Integer user_id, Integer pageNumber, Integer pageSize);
    public Integer countAll();

    public Media findById(int media_id);
}
