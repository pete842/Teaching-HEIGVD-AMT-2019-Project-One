package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoviesServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletResponse response;

    @Mock
    MediaDAOLocal mediaDAO;

    @Mock
    RequestDispatcher requestDispatcher;

    MoviesServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new MoviesServlet();
        servlet.mediaDAO = mediaDAO;

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getRequestDispatcher("/WEB-INF/pages/movies.jsp")).thenReturn(requestDispatcher);

        when(mediaDAO.findAllWithJoinInfoPaged(1, Pagination.DEFAULT_NUMBER, Pagination.DEFAULT_SIZE)).thenReturn(new ArrayList<>());
        when(mediaDAO.countAll()).thenReturn(0);
    }

    @Test
    public void itShouldProvideMoviesInRequest() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute(eq("movies"), eq(new ArrayList<>()));
        verify(request, atLeastOnce()).setAttribute(eq("totalEntries"), eq(0));
    }

    @Test
    public void itShouldProvideHomePage() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}