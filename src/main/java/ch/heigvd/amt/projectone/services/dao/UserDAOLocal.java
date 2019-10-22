package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDAOLocal {
    public List<User> findAll();
}
