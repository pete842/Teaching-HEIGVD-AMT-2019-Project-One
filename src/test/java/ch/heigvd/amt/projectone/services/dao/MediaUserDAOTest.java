package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.MediaUser;
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

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
@Transactional(TransactionMode.ROLLBACK)
public class MediaUserDAOTest {

    @EJB
    private MediaUserDAOLocal mediaUserDAO;
    @EJB
    private UserDAOLocal usersDao;
    @EJB
    private MediaDAOLocal mediaDAO;

    private User john;
    private Media media;
    private MediaUser johnWatched;
    private MediaUser johnToWatch;

    @Before
    public void setup() throws SQLException {
        john = User.builder()
                .username("Bob")
                .firstName("Moran")
                .lastName("Bob")
                .email("bob@moran.org")
                .password("secret")
                .memberSince(new Timestamp(System.currentTimeMillis() % 1000 * 1000)).build();
        john = usersDao.create(john);

        media = mediaDAO.findAllWithJoinInfoPaged(john.getId(), 1, 1).get(0);

        johnWatched = MediaUser.builder()
                .media(media)
                .user(john)
                .rating(100)
                .watched(new Timestamp(System.currentTimeMillis() % 1000 * 1000)).build();

        johnToWatch = MediaUser.builder()
                .media(media)
                .user(john).build();
    }

    @Test
    public void NewlyCreatedUserShouldHaveNoMediaToWatch() {
        assertEquals(0, mediaUserDAO.countAllToWatchByUser(john.getId()).intValue());
    }

    @Test
    public void NewlyCreatedUserShouldHaveNoMediaWatched() {
        assertEquals(0, mediaUserDAO.countAllWatchedByUser(john.getId()).intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void AddingToWatchShouldChangeCountAccordingly() throws SQLException {
        mediaUserDAO.create(johnToWatch);

        assertEquals(1, mediaUserDAO.countAllToWatchByUser(john.getId()).intValue());
        assertEquals(0, mediaUserDAO.countAllWatchedByUser(john.getId()).intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void AddingWatchedShouldChangeCountAccordingly() throws SQLException {
        mediaUserDAO.create(johnWatched);

        assertEquals(0, mediaUserDAO.countAllToWatchByUser(john.getId()).intValue());
        assertEquals(1, mediaUserDAO.countAllWatchedByUser(john.getId()).intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void DeletingToWatchShouldChangeCountAccordingly() throws SQLException {
        MediaUser johnToWatchCreated = mediaUserDAO.create(johnToWatch);
        mediaUserDAO.delete(johnToWatchCreated);

        assertEquals(0, mediaUserDAO.countAllToWatchByUser(john.getId()).intValue());
        assertEquals(0, mediaUserDAO.countAllWatchedByUser(john.getId()).intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void DeletingWatchedShouldChangeCountAccordingly() throws SQLException {
        MediaUser johnToWatchCreated = mediaUserDAO.create(johnToWatch);
        mediaUserDAO.delete(johnToWatchCreated);

        assertEquals(0, mediaUserDAO.countAllToWatchByUser(john.getId()).intValue());
        assertEquals(0, mediaUserDAO.countAllWatchedByUser(john.getId()).intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void LoadedToWatchMediaUserShouldBeEqualsToCreatedOne() throws SQLException {
        MediaUser johnToWatchCreated = mediaUserDAO.create(johnToWatch);
        MediaUser johnToWatchLoaded = mediaUserDAO.get(john.getId(), media.getId());

        assertEquals(johnToWatchCreated, johnToWatchLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void LoadedWatchedMediaUserShouldBeEqualsToCreatedOne() throws SQLException {
        MediaUser johnWatchedCreated = mediaUserDAO.create(johnWatched);
        MediaUser johnWatchedLoaded = mediaUserDAO.get(john.getId(), media.getId());

        assertEquals(johnWatchedCreated, johnWatchedLoaded);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void UpdateMediaUserShouldChangeCountsAccordingly() throws SQLException {
        MediaUser toWatch = mediaUserDAO.create(johnWatched);

        toWatch.toBuilder()
                .rating(100)
                .watched(new Timestamp(System.currentTimeMillis() % 1000 * 1000));

        mediaUserDAO.update(toWatch);

        assertEquals(0, mediaUserDAO.countAllToWatchByUser(john.getId()).intValue());
        assertEquals(1, mediaUserDAO.countAllWatchedByUser(john.getId()).intValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void ItShouldNotBePossibleToCreateTwoRelationBtwSameEntities() throws SQLException {
        mediaUserDAO.create(johnToWatch);

        try {
            mediaUserDAO.create(johnWatched);
        } catch(SQLException e) {
            assertNotNull(e);
            return;
        }

        fail("Creation should have throw a SQLException.");
    }

    @Test
    public void PaginationShouldBeWellFormed() {
        int count = mediaUserDAO.countAllToWatchByUser(1);

        int pageSize = 100;
        int pageNumber = 0;
        int lastPage = (int) Math.ceil(count / pageSize) + 1;
        int lastPageSize = count % pageSize;
        if(lastPageSize == 0)
            lastPageSize = pageSize;

        assertEquals(0, mediaUserDAO.findAllToWatchByUserPaged(1, pageNumber, pageSize).size()); // page 0 should be empty

        /*
        for(++pageNumber; pageNumber < lastPage; ++pageNumber) // page 1 to lastPage - 1
            assertEquals(pageSize, mediaUserDAO.findAllToWatchByUserPaged(1, pageNumber, pageSize).size());
        */

        // last page (partial)
        assertEquals(lastPageSize, mediaUserDAO.findAllToWatchByUserPaged(1, lastPage, pageSize).size());

        // after last page
        assertEquals(0, mediaUserDAO.findAllToWatchByUserPaged(1, lastPage + 1, pageSize).size());
    }
}