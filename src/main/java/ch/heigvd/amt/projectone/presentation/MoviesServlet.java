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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet(urlPatterns = "/movies")
public class MoviesServlet extends BaseHttpServlet {
    private final static String[] postParamsToReturn = new String[]{"media_id"};
    private final static String[] postMandatoryParams = postParamsToReturn;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( ! checkMandatoryParameters(req, resp, postMandatoryParams, "/WEB-INF/pages/home.jsp", postParamsToReturn)) return;

        Pagination pagination = new Pagination().from(req);

        MediaUser mediaUser = MediaUser.builder()
                .media(
                    Media.builder().id(Integer.parseInt(req.getParameter("media_id"))).build()
                )
                .user(
                        User.builder().id((Integer) req.getSession().getAttribute("user_id")).build()
                )
                .rating(Integer.parseInt(req.getParameter("rating")))
                .watched(Timestamp.valueOf(req.getParameter("watched"))).build();

        try {
            mediaUserDAO.create(mediaUser);
        } catch (SQLException e) {
            req.setAttribute("error", e.getMessage());
        }

        pagination.setOn(req);

        req.getRequestDispatcher("/WEB-INF/pages/movies.jsp").forward(req, resp);
    }
}
