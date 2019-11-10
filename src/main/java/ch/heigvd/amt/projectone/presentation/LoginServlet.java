package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends BaseHttpServlet {
  private final static String[] postParamsToReturn = new String[]{"username"};
  private final static String[] postMandatoryParams = new String[]{"username", "password"};

  @EJB
  UserDAOLocal userDAO;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!checkMandatoryParameters(req, resp, postMandatoryParams, "/WEB-INF/pages/login.jsp", postParamsToReturn)) return;

    String username = req.getParameter("username");
    String password = req.getParameter("password");


    User user = userDAO.findByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
      req.setAttribute("error", "Wrong username or password");
      responseToFailure(req, resp, postParamsToReturn, "/WEB-INF/pages/login.jsp");

      return;
    }

    req.getSession().setAttribute("user_id", user.getId());
    req.getSession().setAttribute("username", user.getUsername());
    req.getSession().setAttribute("firstname", user.getFirstName());
    req.getSession().setAttribute("lastname", user.getLastName());
    req.getSession().setAttribute("email", user.getEmail());
    resp.sendRedirect(req.getContextPath() + "/home");
  }
}
