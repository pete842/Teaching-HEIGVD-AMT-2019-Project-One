package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends BaseHttpServlet {
    private final static String[] putParamsToReturn = new String[]{"media_id", "rating", "watched"};
    private final static String[] putMandatoryParams = putParamsToReturn;

    private final static String[] deleteParamsToReturn = new String[]{"media_id"};
    private final static String[] deleteMandatoryParams = deleteParamsToReturn;

    @EJB
    private MediaUserDAOLocal mediaUserDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pagination pagination = new Pagination().from(req);

        Integer user_id = (Integer) req.getSession().getAttribute("user_id");

        req.setAttribute("toWatch", mediaUserDAO.findAllToWatchByUserPaged(user_id, pagination.getNumber(), pagination.getSize()));
        req.setAttribute("totalEntriesToWatch", mediaUserDAO.countAllToWatchByUser(user_id));
        req.setAttribute("totalEntriesWatched", mediaUserDAO.countAllWatchedByUser(user_id));

        pagination.setOn(req);

        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (doHTMLFormBetter(req, resp)) return;

        req.setAttribute("error", "Unimplemented Method !");
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( ! checkMandatoryParameters(req, resp, putMandatoryParams, "/WEB-INF/pages/home.jsp", putParamsToReturn)) return;

        Integer userId = (Integer) req.getSession().getAttribute("user_id");
        Integer mediaId = Integer.parseInt(req.getParameter("media_id"));
        Integer rating = Integer.parseInt(req.getParameter("rating"));
        Integer watched = Integer.parseInt(req.getParameter("watched"));

        MediaUser mediaUser = mediaUserDAO.get(userId, mediaId);

        if (mediaUser != null) {
            mediaUser = mediaUser.toBuilder().rating(rating).build();

            if(! mediaUserDAO.update(mediaUser)) {
                req.setAttribute("error", "Impossible de mettre à jour cette élément");
            }
        } else {
            req.setAttribute("error", "Impossible de mettre à jour un élément inexistant");
        }

        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( ! checkMandatoryParameters(req, resp, deleteMandatoryParams, "/WEB-INF/pages/home.jsp", deleteParamsToReturn)) return;

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

        doGet(req, resp);
    }
}
