package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.UserDAOLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
  @EJB
  private UserDAOLocal userDAO;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    if (username == null || password == null) {
      req.setAttribute("error", "Missing Parameter");

      req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
      return;
    }

    if (username.equals("") || password.equals("")) {
      req.setAttribute("error", "Missing Required parameter");
      req.setAttribute("username", username);

      req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
      return;
    }

    User user = userDAO.getUserByUsername(username);
    if (user == null || !user.getPassword().equals(password)) {
      req.setAttribute("error", "Wrong username or password");
      req.setAttribute("username", username);

      req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
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
