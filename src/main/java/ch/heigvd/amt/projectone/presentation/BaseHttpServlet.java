package ch.heigvd.amt.projectone.presentation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BaseHttpServlet extends HttpServlet {
    protected void forwardBack(HttpServletRequest req, HttpServletResponse resp, String onMissing) throws ServletException, IOException {
        String previousURL = onMissing;

        if (req.getParameter("back") != null) {
            previousURL = req.getParameter("back");
        }

        req.getRequestDispatcher(previousURL).forward(new HttpServletRequestWrapper(req) {
            @Override
            public String getMethod() {
                return "GET";
            }
        }, resp);
    }

    protected boolean doHTMLFormBetter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) return false;

        if (action.equalsIgnoreCase("DELETE")) {
            this.doDelete(req, resp);
            return true;
        }
        else if (action.equalsIgnoreCase("PUT")) {
            this.doPut(req, resp);
            return true;
        }

        return false;
    }

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
