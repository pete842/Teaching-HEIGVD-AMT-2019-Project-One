package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/watched")
public class WatchedServlet extends BaseHttpServlet {
    private final static String[] deleteParamsToReturn = new String[]{"media_id"};
    private final static String[] deleteMandatoryParams = deleteParamsToReturn;

    @EJB
    private MediaUserDAOLocal mediaUserDAO;

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (doHTMLFormBetter(req, resp)) return;

        req.setAttribute("error", "Unimplemented Method !");
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( ! checkMandatoryParameters(req, resp, deleteMandatoryParams, "/WEB-INF/pages/watched.jsp", deleteParamsToReturn)) return;

        Integer userId = (Integer) req.getSession().getAttribute("user_id");
        Integer mediaId = Integer.parseInt(req.getParameter("media_id"));

        MediaUser mediaUser = mediaUserDAO.get(userId, mediaId);

        if (mediaUser != null) {
            if (! mediaUserDAO.delete(mediaUser)) {
                req.setAttribute("error", "Impossible de supprimer cette élément");
            }
        } else {
            req.setAttribute("error", "Impossible de supprimer un élément inexistant");
        }

        req.getRequestDispatcher("/home"). forward(new HttpServletRequestWrapper(req) {
            @Override
            public String getMethod() {
                return "GET";
            }
        }, resp);
    }
}
