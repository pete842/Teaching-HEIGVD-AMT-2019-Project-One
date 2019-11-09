package ch.heigvd.amt.projectone.presentation;

import ch.heigvd.amt.projectone.model.entities.Media;
import ch.heigvd.amt.projectone.model.entities.MediaUser;
import ch.heigvd.amt.projectone.model.entities.User;
import ch.heigvd.amt.projectone.services.dao.MediaUserDAOLocal;
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
class MediaUserServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletResponse response;

    @Mock
    MediaUserDAOLocal mediaUserDAO;

    @Mock
    MediaUser.MediaUserBuilder mediaUserBuilder;

    @Mock
    MediaUser mediaUser;

    @Mock
    RequestDispatcher requestDispatcher;

    MediaUserServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new MediaUserServlet();
        servlet.mediaUserDAO = mediaUserDAO;
    }

    @Test
    void itShouldCreateWatched() throws SQLException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getParameter("media_id")).thenReturn("1");
        when(request.getParameter("rating")).thenReturn("50");
        when(request.getParameter("watched")).thenReturn("11/26/2019");

        when(mediaUserDAO.create(any(MediaUser.class))).thenReturn(mediaUser);

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
        doReturn(false).when((BaseHttpServlet) servlet).doHTMLFormBetter(any(HttpServletRequest.class), any(HttpServletResponse.class));

        servlet.doPost(request, response);

        verify(mediaUserDAO, atLeastOnce()).create(any(MediaUser.class));
        verify(servlet, atLeastOnce()).forwardBack(eq(request), eq(response), anyString());
    }

    @Test
    void itShouldCreateToWatch() throws SQLException, ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getParameter("media_id")).thenReturn("1");
        when(request.getParameter("rating")).thenReturn(null);
        when(request.getParameter("watched")).thenReturn(null);

        when(mediaUserDAO.create(any(MediaUser.class))).thenReturn(mediaUser);

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
        doReturn(false).when((BaseHttpServlet) servlet).doHTMLFormBetter(any(HttpServletRequest.class), any(HttpServletResponse.class));

        servlet.doPost(request, response);

        verify(mediaUserDAO, atLeastOnce()).create(any(MediaUser.class));
        verify(servlet, atLeastOnce()).forwardBack(eq(request), eq(response), anyString());
    }

    @Test
    void itSouldRedirectPostToDelete() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("delete");

        servlet = Mockito.spy(servlet);
        doNothing().when(servlet).doDelete(any(HttpServletRequest.class), any(HttpServletResponse.class));

        servlet.doPost(request, response);

        verify(servlet, atLeastOnce()).doDelete(any(request.getClass()), any(response.getClass()));
    }

    @Test
    void itSouldRedirectPostToPut() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("put");

        servlet = Mockito.spy(servlet);
        doNothing().when(servlet).doPut(any(HttpServletRequest.class), any(HttpServletResponse.class));

        servlet.doPost(request, response);

        verify(servlet, atLeastOnce()).doPut(any(request.getClass()), any(response.getClass()));
    }

    @Test
    void itShouldRejectIfThrowSQLException() throws ServletException, IOException, SQLException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getParameter("media_id")).thenReturn("1");
        when(request.getParameter("rating")).thenReturn(null);
        when(request.getParameter("watched")).thenReturn(null);

        when(mediaUserDAO.create(any(MediaUser.class))).thenThrow(new SQLException("Error SQL"));

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
        doReturn(false).when((BaseHttpServlet) servlet).doHTMLFormBetter(any(HttpServletRequest.class), any(HttpServletResponse.class));

        servlet.doPost(request, response);

        verify(request, atLeastOnce()).setAttribute(eq("error"), eq("Error SQL"));
        verify(servlet, atLeastOnce()).forwardBack(eq(request), eq(response), anyString());
    }

    @Test
    void itSouldDelete() throws ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getParameter("media_id")).thenReturn("1");
        when(request.getParameter("action")).thenReturn("delete");

        when(mediaUserDAO.delete(any(MediaUser.class))).thenReturn(true);
        when(mediaUserDAO.get(1, 1)).thenReturn(mediaUser);

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());

        servlet.doPost(request, response);

        verify(mediaUserDAO, atLeastOnce()).get(1, 1);
        verify(mediaUserDAO, atLeastOnce()).delete(mediaUser);
        verify(servlet, atLeastOnce()).forwardBack(eq(request), eq(response), anyString());
    }

    @Test
    void itSouldUpdate() throws ServletException, IOException {
        when(request.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user_id")).thenReturn(1);
        when(request.getParameter("media_id")).thenReturn("1");
        when(request.getParameter("action")).thenReturn("put");
        when(request.getParameter("rating")).thenReturn("50");
        when(request.getParameter("watched")).thenReturn("11/26/2019");

        when(mediaUser.toBuilder()).thenReturn(mediaUserBuilder);
        when(mediaUserBuilder.build()).thenReturn(mediaUser);
        when(mediaUserBuilder.watched(any())).thenReturn(mediaUserBuilder);
        when(mediaUserBuilder.rating(any())).thenReturn(mediaUserBuilder);

        when(mediaUserDAO.update(any(MediaUser.class))).thenReturn(true);
        when(mediaUserDAO.get(1, 1)).thenReturn(mediaUser);

        servlet = Mockito.spy(servlet);
        doNothing().when((BaseHttpServlet) servlet).forwardBack(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());

        servlet.doPost(request, response);

        verify(mediaUserDAO, atLeastOnce()).get(1, 1);
        verify(mediaUserDAO, atLeastOnce()).update(mediaUser);
        verify(servlet, atLeastOnce()).forwardBack(eq(request), eq(response), anyString());
    }
}