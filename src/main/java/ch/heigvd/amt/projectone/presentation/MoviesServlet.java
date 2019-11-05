package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(urlPatterns = "/movies")
public class MoviesServlet extends BaseHttpServlet {
    private final static String[] postParamsToReturn = new String[]{"media_id"};
    private final static String[] postMandatoryParams = postParamsToReturn;

    private final static String[] putParamsToReturn = new String[]{"media_id", "rating", "watched"};
    private final static String[] putMandatoryParams = putParamsToReturn;

    private final static String[] deleteParamsToReturn = new String[]{"media_id"};
    private final static String[] deleteMandatoryParams = deleteParamsToReturn;

    @EJB
    private MediaUserDAOLocal mediaUserDAO;
    @EJB
    private MediaDAOLocal mediaDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pagination pagination = new Pagination().from(req);

        req.setAttribute("movies", mediaDAO.findAllWithJoinInfoPaged((Integer) req.getSession().getAttribute("user_id"), pagination.getNumber(), pagination.getSize()));
        req.setAttribute("totalEntries", mediaDAO.countAll());

        pagination.setOn(req);

        req.getRequestDispatcher("/WEB-INF/pages/movies.jsp").forward(req, resp);
    }
}
