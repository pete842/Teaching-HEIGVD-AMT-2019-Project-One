package ch.heigvd.amt.projectone.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseHttpServlet extends HttpServlet {
    protected boolean checkMandatoryParameters(HttpServletRequest req, HttpServletResponse resp, String[] params, String onFailureUrl) throws ServletException, IOException {
        return this.checkMandatoryParameters(req, resp, params, onFailureUrl, params);
    }

    protected boolean checkMandatoryParameters(HttpServletRequest req, HttpServletResponse resp, String[] params, String onFailureUrl, String[] toReturn) throws ServletException, IOException {
        for (String param : params) {
            String value = req.getParameter(param);

            if (value == null || value.equals("")) {
                req.setAttribute("error", "Missing required parameter");
                responseToFailure(req, resp, toReturn, onFailureUrl);
                return false;
            }
        }

        return true;
    }

    protected void responseToFailure(HttpServletRequest req, HttpServletResponse resp, String[] toReturn, String onFailureUrl) throws ServletException, IOException {
        for (String param : toReturn) {
            req.setAttribute(param, req.getParameter(param));
        }

        req.getRequestDispatcher(onFailureUrl).forward(req, resp);
    }
}
