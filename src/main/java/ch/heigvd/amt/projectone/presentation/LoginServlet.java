package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;
import ch.heigvd.amt.projectone.services.dao.UserDAO;
import ch.heigvd.amt.projectone.services.dao.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
  @EJB
  private MediaDAOLocal mediaDAO;
  @EJB
  private UserDAOLocal userDAO;
  @EJB
  private MediaUserDAOLocal mediaUserDAO;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    userDAO.findAll();
    mediaDAO.findAll();
    mediaUserDAO.findAllByUser(2);

    req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}
