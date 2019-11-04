package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/movies")
public class MoviesServlet extends HttpServlet {
    @EJB
    private MediaDAOLocal mediaDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer pageSize = 5;
        Integer pageNumber = 1;

        if (req.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        }

        if (req.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
        }

        req.setAttribute("watched", mediaDAO.findAllPaged(pageNumber, pageSize));
        req.setAttribute("totalEntries", mediaDAO.countAll());
        req.setAttribute("pageSize", pageSize.toString());
        req.setAttribute("pageNumber", pageNumber.toString());

        req.getRequestDispatcher("/WEB-INF/pages/watched.jsp").forward(req, resp);
    }
}
