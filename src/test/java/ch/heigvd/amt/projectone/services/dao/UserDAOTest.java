package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@DeploymentParameters(testable = true)
public class UserDAOTest {

    @EJB
    private UserDAOLocal usersDao;

    /*@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(UserDAO.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/

    @Test
    public void findAll() throws SQLException {
        User john = User.builder()
                .username("jdoe")
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.org")
                .password("secret").build();

        User johnCreated = usersDao.create(john);
        User johnLoaded = usersDao.getUserByUsername(john.getUsername());

        assertEquals(john, johnCreated);
        assertEquals(john, johnLoaded);
        assertSame(john, johnCreated);
        assertNotSame(john, johnLoaded);
    }

    @Test
    public void getUserByUsername() {
    }

    @Test
    public void create() {
    }
}
