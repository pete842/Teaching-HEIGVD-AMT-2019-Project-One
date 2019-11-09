package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.User;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class UserDAOTest {

    @EJB
    private UserDAOLocal usersDao;

    private User john;

    @Before
    public void setup() {
        john = User.builder()
                .username("Bob")
                .firstName("Moran")
                .lastName("Bob")
                .email("bob@moran.org")
                .password("secret")
                .memberSince(new Timestamp(System.currentTimeMillis() % 1000 * 1000)).build();
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void createdUserShouldHaveAnId() throws SQLException {
        User johnCreated = usersDao.create(john);
        assertNotNull(johnCreated.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void createdUserShouldHaveAMemberSinceRow() throws SQLException {
        User johnCreated = usersDao.create(john);
        assertNotNull(johnCreated.getId());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void createdUserShouldHaveTheRightContent() throws SQLException {
        User johnCreated = usersDao.create(john);

        assertEquals(john.getUsername(), johnCreated.getUsername());
        assertEquals(john.getFirstName(), johnCreated.getFirstName());
        assertEquals(john.getLastName(), johnCreated.getLastName());
        assertEquals(john.getEmail(), johnCreated.getEmail());
        assertEquals(john.getPassword(), johnCreated.getPassword());
        assertEquals(john.getMemberSince(), johnCreated.getMemberSince());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void getUserByUsernameUserShouldBeEquals() throws SQLException {
        User johnCreated = usersDao.create(john);
        User johnLoaded = usersDao.getUserByUsername(john.getUsername());

        assertEquals(johnCreated, johnLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void findAllShouldContainsANewlyCreatedUser() throws SQLException {
        User johnCreated = usersDao.create(john);
        List<User> users = usersDao.findAll();

        assertTrue(users.contains(johnCreated));
    }
}
