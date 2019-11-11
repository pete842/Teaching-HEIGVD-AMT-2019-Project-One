package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/movie")
public class MovieServlet extends BaseHttpServlet {
    private final static String[] getParamsToReturn = new String[0];
    private final static String[] getMandatoryParams = new String[]{"media_id"};

    @EJB
    MediaDAOLocal mediaDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(! checkMandatoryParameters(req, resp, getMandatoryParams, "/home", getParamsToReturn)) return;

        Pagination pagination = new Pagination().from(req);

        req.setAttribute("movie", mediaDAO.findById(Integer.parseInt(req.getParameter("media_id"))));
        req.setAttribute("back", req.getParameter("back"));

        pagination.setOn(req);

        req.getRequestDispatcher("/WEB-INF/pages/movie.jsp").forward(req, resp);
    }
}
