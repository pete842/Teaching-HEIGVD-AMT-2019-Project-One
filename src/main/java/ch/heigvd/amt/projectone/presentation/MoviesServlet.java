package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/movies")
public class MoviesServlet extends BaseHttpServlet {
    @EJB
    MediaDAOLocal mediaDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pagination pagination = new Pagination().from(req);

        req.setAttribute("movies", mediaDAO.findAllWithJoinInfoPaged((Integer) req.getSession().getAttribute("user_id"), pagination.getNumber(), pagination.getSize()));
        Integer totalEntries = mediaDAO.countAll();
        req.setAttribute("totalEntries", totalEntries);

        pagination.setOn(req, totalEntries);

        req.getRequestDispatcher("/WEB-INF/pages/movies.jsp").forward(req, resp);
    }
}
