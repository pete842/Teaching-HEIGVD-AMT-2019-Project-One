package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends BaseHttpServlet {
  private final static String[] postParamsToReturn = new String[]{"username", "email", "firstname", "lastname"};
  private final static String[] postMandatoryParams = new String[]{"username", "password", "password_confirm", "email", "firstname", "lastname"};

  @EJB
  UserDAOLocal userDAO;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!checkMandatoryParameters(req, resp, postMandatoryParams, "/WEB-INF/pages/register.jsp", postParamsToReturn)) return;

    String password = req.getParameter("password");
    String passwordConfirme = req.getParameter("password_confirm");
    if (! password.equals(passwordConfirme)) {
      req.setAttribute("error", "Different password");
      responseToFailure(req, resp, postParamsToReturn, "/WEB-INF/pages/register.jsp");

      return;
    }

    User user = User.builder()
            .username(req.getParameter("username"))
            .email(req.getParameter("email"))
            .firstName(req.getParameter("firstname"))
            .lastName(req.getParameter("lastname"))
            .password(password)
            .build();

    try {
      userDAO.create(user);

      req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    } catch (SQLException e) {
      req.setAttribute("error", e.getMessage());

      responseToFailure(req, resp, postParamsToReturn, "/WEB-INF/pages/register.jsp");
    }
  }
}
