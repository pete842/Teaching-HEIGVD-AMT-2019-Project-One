package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
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
    @Transactional(TransactionMode.ROLLBACK)
    public void findAll() throws SQLException {
        User john = User.builder()
                .username("Bob")
                .firstName("Moran")
                .lastName("Bob")
                .email("bob@moran.org")
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
        assertEquals(1, 1);
    }

    @Test
    public void create() {
    }
}
