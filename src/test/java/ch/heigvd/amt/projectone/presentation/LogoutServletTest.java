package ch.heigvd.amt.projectone.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogoutServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    LogoutServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new LogoutServlet();

        when(request.getRequestDispatcher("/WEB-INF/pages/login.jsp")).thenReturn(requestDispatcher);

        when(request.getSession()).thenReturn(httpSession);
        doNothing().when(httpSession).invalidate();
    }

    @Test
    void itShouldDestroyTheActiveSession() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(httpSession, atLeastOnce()).invalidate();
    }

    @Test
    void itShouldRedirectToLogin() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}