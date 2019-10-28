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
import java.sql.SQLException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
  @EJB
  UserDAOLocal userDAO;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");
    String passwordConfirme = req.getParameter("password_confirm");
    String email = req.getParameter("email");
    String firstname = req.getParameter("firstname");
    String lastname = req.getParameter("lastname");

    if (username == null || password == null || passwordConfirme == null ||
            email == null || firstname == null || lastname == null) {
      req.setAttribute("error", "Missing Parameter");
      req.setAttribute("username", username);
      req.setAttribute("email", email);
      req.setAttribute("firstname", firstname);
      req.setAttribute("lastname", lastname);

      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    if (username.equals("") || password.equals("") || passwordConfirme.equals("") ||
            email.equals("") || firstname.equals("") || lastname.equals("")) {
      req.setAttribute("error", "Missing Required parameter");
      req.setAttribute("username", username);
      req.setAttribute("email", email);
      req.setAttribute("firstname", firstname);
      req.setAttribute("lastname", lastname);

      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    if (! password.equals(passwordConfirme)) {
      req.setAttribute("error", "Different password");
      req.setAttribute("username", username);
      req.setAttribute("email", email);
      req.setAttribute("firstname", firstname);
      req.setAttribute("lastname", lastname);

      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    User user = User.builder()
            .username(username)
            .email(email)
            .firstName(firstname)
            .lastName(lastname)
            .password(password)
            .build();
    try {
      userDAO.create(user);

      req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
    } catch (SQLException e) {
      req.setAttribute("error", e.getMessage());
      req.setAttribute("username", username);
      req.setAttribute("email", email);
      req.setAttribute("firstname", firstname);
      req.setAttribute("lastname", lastname);

      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
  }
}
