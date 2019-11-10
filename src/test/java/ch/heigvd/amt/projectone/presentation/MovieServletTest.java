package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaDAOLocal;
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
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletResponse response;

    @Mock
    MediaDAOLocal mediaDAO;

    @Mock
    Media media;

    @Mock
    RequestDispatcher requestDispatcher;

    MovieServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new MovieServlet();
        servlet.mediaDAO = mediaDAO;

        when(request.getParameter("media_id")).thenReturn("1");
        when(request.getRequestDispatcher("/WEB-INF/pages/movie.jsp")).thenReturn(requestDispatcher);

        when(mediaDAO.findById(1)).thenReturn(media);
    }

    @Test
    public void itShouldProvideMovieInRequest() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute(eq("movie"), eq(media));
    }

    @Test
    public void itShouldProvideHomePage() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}