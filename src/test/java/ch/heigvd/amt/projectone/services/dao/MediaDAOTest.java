package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.entities.Media;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import java.sql.Timestamp;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class MediaDAOTest {

    @EJB
    private MediaDAOLocal mediaDAO;

    private Media it;

    @Before
    public void setup() {
        it = Media.builder()
                .title("it")
                .duration(135)
                .mainGenre("Horror|Mystery|Supernatural")
                .release(new Timestamp(1504569600))
                .rating(65)
                .build();
    }
}