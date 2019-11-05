package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends BaseHttpServlet {
    private final static String[] deleteParamsToReturn = new String[]{"user_id", "media_id"};
    private final static String[] deleteMandatoryParams = deleteParamsToReturn;

    @EJB
    private MediaUserDAOLocal mediaUserDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer user_id = (Integer) req.getSession().getAttribute("user_id");
        Integer pageSize = 5;
        Integer pageNumber = 1;

        if (req.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        }

        if (req.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        }

        req.setAttribute("toWatch", mediaUserDAO.findAllToWatchByUserPaged(user_id, pageNumber, pageSize));
        req.setAttribute("totalEntriesToWatch", mediaUserDAO.countAllToWatchByUser(user_id));
        req.setAttribute("totalEntriesWatched", mediaUserDAO.countAllWatchedByUser(user_id));
        req.setAttribute("pageSize", pageSize.toString());
        req.setAttribute("pageNumber", pageNumber.toString());

        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( ! checkMandatoryParameters(req, resp, deleteMandatoryParams, "/WEB-INF/pages/home.jsp", deleteParamsToReturn)) return;
        Integer userId = (Integer) req.getSession().getAttribute("user_id");
        Integer mediaId = (Integer) req.getAttribute("media_id");

        Integer pageSize = 5;
        Integer pageNumber = 1;

        if (req.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        }

        if (req.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        }

        MediaUser mediaUser = mediaUserDAO.get(userId, mediaId);

        if (mediaUser != null) {
            if (! mediaUserDAO.delete(mediaUser)) {
                req.setAttribute("error", "Impossible de supprimer cette élément");
            }
        } else {
            req.setAttribute("error", "Impossible de supprimer un élément inexistant");
        }

        req.setAttribute("pageSize", pageSize.toString());
        req.setAttribute("pageNumber", pageNumber.toString());

        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }
}
