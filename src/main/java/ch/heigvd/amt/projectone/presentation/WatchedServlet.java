package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/watched")
public class WatchedServlet extends BaseHttpServlet {
    @EJB
    MediaUserDAOLocal mediaUserDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pagination pagination = new Pagination().from(req);

        Integer user_id = (Integer) req.getSession().getAttribute("user_id");

        req.setAttribute("watched", mediaUserDAO.findAllWatchedByUserPaged(user_id, pagination.getNumber(), pagination.getSize()));
        req.setAttribute("totalEntriesToWatch", mediaUserDAO.countAllToWatchByUser(user_id));
        req.setAttribute("totalEntriesWatched", mediaUserDAO.countAllWatchedByUser(user_id));

        pagination.setOn(req);

        req.getRequestDispatcher("/WEB-INF/pages/watched.jsp").forward(req, resp);
    }
}
