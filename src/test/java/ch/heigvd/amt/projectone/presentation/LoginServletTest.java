package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.services.dao.UserDAOLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ejb.DuplicateKeyException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    UserDAOLocal usersDAO;

    @Mock
    PrintWriter responseWriter;

    LoginServlet servlet;

    @BeforeEach
    public void setup() throws IOException {
        servlet = new LoginServlet();
        servlet.userDAO = usersDAO;
        when(response.getWriter()).thenReturn(responseWriter);
    }

    @Test
    void doGet() throws ServletException, IOException, SQLException {
        servlet.doGet(request, response);
        verify(usersDAO, atLeastOnce()).create(any());
    }
}