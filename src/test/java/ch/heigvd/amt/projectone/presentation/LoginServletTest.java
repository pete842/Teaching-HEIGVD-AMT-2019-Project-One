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
class LoginServletTest {
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
    RequestDispatcher requestDispatcher;

    LoginServlet servlet;

    @BeforeEach
    public void setup() throws IOException {
        servlet = new LoginServlet();
        servlet.userDAO = usersDAO;
    }

    @Test
    void doGetSouldCallLoginView() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);
        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }

    public void beforePost() {
        when(usersDAO.getUserByUsername("pete842")).thenReturn(user);
    }

    @Test
    void doPostSouldCallGetUserByUsername() throws ServletException, IOException {
        beforePost();
        useUserModel();
        useSession();
        useGoodUsername();
        useGoodPassword();

        servlet.doPost(request, response);
        verify(usersDAO, atLeastOnce()).getUserByUsername("pete842");
    }

    @Test
    void doPostSouldAcceptLogin() throws ServletException, IOException, SQLException {
        beforePost();
        useUserModel();
        useSession();
        useGoodUsername();
        useGoodPassword();

        servlet.doPost(request, response);
        verify(usersDAO, atLeastOnce()).getUserByUsername("pete842");
        verify(response, atLeastOnce()).sendRedirect(any());
    }

    @Test
    void doPostSouldRefusLoginWrongPassword() throws ServletException, IOException {
        beforePost();
        useUserModel();
        useGoodUsername();
        useBadPassword();

        blockResponseToFailure();

        servlet.doPost(request, response);
        verify(usersDAO, atLeastOnce()).getUserByUsername("pete842");
        verify(request, atLeastOnce()).setAttribute(eq("error"), eq("Wrong username or password"));
        verify(servlet, atLeastOnce()).responseToFailure(any(request.getClass()), any(response.getClass()), AdditionalMatchers.aryEq(new String[]{"username"}), eq("/WEB-INF/pages/login.jsp"));
    }

    @Test
    void doPostSouldRefusLoginWrongUsername() throws ServletException, IOException {
        when(usersDAO.getUserByUsername(AdditionalMatchers.not(eq("pete842")))).thenReturn(null);

        useBadUsername();
        useGoodPassword();

        blockResponseToFailure();

        servlet.doPost(request, response);
        verify(usersDAO, atLeastOnce()).getUserByUsername("pete");
        verify(request, atLeastOnce()).setAttribute(eq("error"), eq("Wrong username or password"));
        verify(servlet, atLeastOnce()).responseToFailure(any(request.getClass()), any(response.getClass()), AdditionalMatchers.aryEq(new String[]{"username"}), eq("/WEB-INF/pages/login.jsp"));
    }

    void useGoodPassword() { when(request.getParameter("password")).thenReturn("totem"); }
    void useBadPassword() { when(request.getParameter("password")).thenReturn("tata"); }
    void useGoodUsername() { when(request.getParameter("username")).thenReturn("pete842"); }
    void useBadUsername() { when(request.getParameter("username")).thenReturn("pete"); }

    void useUserModel() { when(user.getPassword()).thenReturn("totem"); }

    void useSession() {
        when(request.getSession()).thenReturn(httpSession);
    }

    void blockResponseToFailure() throws ServletException, IOException {
        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).responseToFailure(any(HttpServletRequest.class), any(HttpServletResponse.class), any(String[].class), anyString());
    }
}