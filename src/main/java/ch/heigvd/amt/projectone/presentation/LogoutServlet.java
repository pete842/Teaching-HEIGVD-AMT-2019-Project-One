package ch.heigvd.amt.projectone.presentation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends BaseHttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getSession().invalidate();

    req.setAttribute("success", "Successful logout !");
    req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
  }
}
