package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.UserDAOLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletResponse response;

    @Mock
    UserDAOLocal usersDAO;

    @Mock
    User user;

    @Mock
    User.UserBuilder userBuilder;

    @Mock
    RequestDispatcher requestDispatcher;

    RegistrationServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new RegistrationServlet();
        servlet.userDAO = usersDAO;
    }

    @Test
    void itShouldProvideRegistration() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/register.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldForwardToLoginIfSuccess() throws SQLException, ServletException, IOException {
        when(request.getParameter("username")).thenReturn("pete842");
        when(request.getParameter("firstname")).thenReturn("Pierre");
        when(request.getParameter("lastname")).thenReturn("Kohler");
        when(request.getParameter("email")).thenReturn("pierre.kohler@heig-vd.ch");
        when(request.getParameter("password")).thenReturn("totem");
        when(request.getParameter("password_confirm")).thenReturn("totem");

        when(usersDAO.create(any(User.class))).thenReturn(user);

        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    @Test
    void itShouldRejectIfPasswordIsNotDifferent() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn("pete842");
        when(request.getParameter("firstname")).thenReturn("Pierre");
        when(request.getParameter("lastname")).thenReturn("Kohler");
        when(request.getParameter("email")).thenReturn("pierre.kohler@heig-vd.ch");
        when(request.getParameter("password")).thenReturn("totem");
        when(request.getParameter("password_confirm")).thenReturn("tata");

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).responseToFailure(any(HttpServletRequest.class), any(HttpServletResponse.class), any(String[].class), anyString());

        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute(eq("error"), eq("Different password"));
        verify(servlet, atLeastOnce()).responseToFailure(any(request.getClass()), any(response.getClass()), AdditionalMatchers.aryEq(new String[]{"username", "email", "firstname", "lastname"}), eq("/WEB-INF/pages/register.jsp"));
    }
    @Test
    void itShouldRejectIfThrowSQLException() throws ServletException, IOException, SQLException {
        when(request.getParameter("username")).thenReturn("pete842");
        when(request.getParameter("firstname")).thenReturn("Pierre");
        when(request.getParameter("lastname")).thenReturn("Kohler");
        when(request.getParameter("email")).thenReturn("pierre.kohler@heig-vd.ch");
        when(request.getParameter("password")).thenReturn("totem");
        when(request.getParameter("password_confirm")).thenReturn("totem");

        when(usersDAO.create(any(User.class))).thenThrow(new SQLException("Error SQL"));

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).responseToFailure(any(HttpServletRequest.class), any(HttpServletResponse.class), any(String[].class), anyString());

        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute(eq("error"), eq("Error SQL"));
        verify(servlet, atLeastOnce()).responseToFailure(any(request.getClass()), any(response.getClass()), AdditionalMatchers.aryEq(new String[]{"username", "email", "firstname", "lastname"}), eq("/WEB-INF/pages/register.jsp"));
    }
}