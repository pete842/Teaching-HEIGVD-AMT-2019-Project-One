package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Pagination;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;
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
class HomeServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletResponse response;

    @Mock
    MediaUserDAOLocal mediaUserDAO;

    @Mock
    RequestDispatcher requestDispatcher;

    HomeServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new HomeServlet();
        servlet.mediaUserDAO = mediaUserDAO;

        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getRequestDispatcher("/WEB-INF/pages/home.jsp")).thenReturn(requestDispatcher);

        when(mediaUserDAO.findAllToWatchByUserPaged(1, Pagination.DEFAULT_NUMBER, Pagination.DEFAULT_SIZE)).thenReturn(new ArrayList<>());
        when(mediaUserDAO.countAllToWatchByUser(1)).thenReturn(0);
        when(mediaUserDAO.countAllWatchedByUser(1)).thenReturn(0);
    }

    @Test
    public void itShouldProvideWatchedAndToWatchInRequest() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request, atLeastOnce()).setAttribute(eq("toWatch"), eq(new ArrayList<>()));
        verify(request, atLeastOnce()).setAttribute(eq("totalEntriesToWatch"), eq(0));
        verify(request, atLeastOnce()).setAttribute(eq("totalEntriesWatched"), eq(0));
    }

    @Test
    public void itShouldProvideHomePage() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(requestDispatcher, atLeastOnce()).forward(request, response);
    }
}