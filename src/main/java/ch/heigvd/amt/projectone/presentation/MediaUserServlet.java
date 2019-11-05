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
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

@WebServlet(urlPatterns = "/media_user")
public class MediaUserServlet extends BaseHttpServlet {
    private final static String[] postParamsToReturn = new String[]{"media_id"};
    private final static String[] postMandatoryParams = postParamsToReturn;

    private final static String[] putParamsToReturn = new String[]{"media_id", "rating", "watched"};
    private final static String[] putMandatoryParams = putParamsToReturn;

    private final static String[] deleteParamsToReturn = new String[]{"media_id"};
    private final static String[] deleteMandatoryParams = deleteParamsToReturn;

    @EJB
    private MediaUserDAOLocal mediaUserDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (doHTMLFormBetter(req, resp)) return;

        if(! checkMandatoryParameters(req, resp, postMandatoryParams, "/WEB-INF/pages/home.jsp", postParamsToReturn)) return;

        MediaUser mediaUser = MediaUser.builder()
                .media(
                    Media.builder().id(Integer.parseInt(req.getParameter("media_id"))).build()
                )
                .user(
                        User.builder().id((Integer) req.getSession().getAttribute("user_id")).build()
                )
                .rating((req.getParameter("rating") != null) ? Integer.parseInt(req.getParameter("rating")) : null)
                .watched((req.getParameter("watched") != null) ? Timestamp.valueOf(req.getParameter("watched")) : null).build();

        try {
            mediaUserDAO.create(mediaUser);
        } catch (SQLException e) {
            req.setAttribute("error", e.getMessage());
        }

        forwardBack(req, resp, "/home");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( ! checkMandatoryParameters(req, resp, putMandatoryParams, "/WEB-INF/pages/home.jsp", putParamsToReturn)) return;

        Integer userId = (Integer) req.getSession().getAttribute("user_id");
        Integer mediaId = Integer.parseInt(req.getParameter("media_id"));
        Integer rating = Integer.parseInt(req.getParameter("rating"));
        Timestamp watched = Timestamp.valueOf(req.getParameter("watched"));

        System.out.println(watched);

        MediaUser mediaUser = mediaUserDAO.get(userId, mediaId);

        if (mediaUser != null) {
            mediaUser = mediaUser.toBuilder().rating(rating).build();

            if(! mediaUserDAO.update(mediaUser)) {
                req.setAttribute("error", "Impossible de mettre à jour cette élément");
            }
        } else {
            req.setAttribute("error", "Impossible de mettre à jour un élément inexistant");
        }

        forwardBack(req, resp, "/home");
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

        forwardBack(req, resp, "/home");

    }
}
