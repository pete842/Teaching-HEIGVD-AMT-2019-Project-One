package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.User;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface UserDAOLocal {
    public List<User> findAll();
    public User findByUsername(String username);

    public User create(User user) throws SQLException;
}
