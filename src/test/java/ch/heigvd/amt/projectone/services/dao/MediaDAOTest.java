package ch.heigvd.amt.projectone.services.dao;

import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class MediaDAOTest {

    @EJB
    private MediaDAOLocal mediaDAO;

    @Test
    public void PaginationShouldBeWellFormed() {
        int count = mediaDAO.countAll();

        int pageSize = 100;
        int pageNumber = 0;
        int lastPage = (int) Math.ceil(count / pageSize);
        int lastPageSize = count % pageSize;
        if(lastPageSize == 0)
            lastPageSize = pageSize;

        assertEquals(0, mediaDAO.findAllWithJoinInfoPaged(1, pageNumber, pageSize).size()); // page 0 should be empty

        /*
        for (++pageNumber; pageNumber < lastPage; ++pageNumber) // page 1 to lastPage - 1
            assertEquals(pageSize, mediaDAO.findAllWithJoinInfoPaged(1, pageNumber, pageSize).size());
        */

        // last page (partial)
        assertEquals(lastPageSize, mediaDAO.findAllWithJoinInfoPaged(1, lastPage, pageSize).size());

        // after last page
        assertEquals(0, mediaDAO.findAllWithJoinInfoPaged(1, lastPage + 1, pageSize).size());
    }
}