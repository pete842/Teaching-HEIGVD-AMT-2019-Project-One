package ch.heigvd.amt.projectone.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BaseHttpServletTest {
    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    RequestDispatcher requestDispatcher;

    BaseHttpServlet servlet;

    @BeforeEach
    public void setup() {
        servlet = new BaseHttpServlet();
    }

    @Test
    void itShouldBeAbleToForwardRequestPostToGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/test")).thenReturn(requestDispatcher);
        when(request.getParameter("back")).thenReturn("/test");

        servlet.forwardBack(request, response, "/error");

        verify(requestDispatcher, atLeastOnce()).forward(any(HttpServletRequestWrapper.class), eq(response));
    }

    @Test
    void itShouldBeAbleToForwardRequestWithoutBack() throws ServletException, IOException {
        when(request.getRequestDispatcher("/miss")).thenReturn(requestDispatcher);
        when(request.getParameter("back")).thenReturn(null);

        servlet.forwardBack(request, response, "/miss");

        verify(requestDispatcher, atLeastOnce()).forward(any(HttpServletRequestWrapper.class), eq(response));
    }

    @Test
    void itShouldBeDetectAllParameters() throws ServletException, IOException {
        String[] params = new String[] {"param1", "param2"};

        when(request.getParameter("param1")).thenReturn("valid");
        when(request.getParameter("param2")).thenReturn("valid");

        assertTrue(servlet.checkMandatoryParameters(request, response, params, "/error"));
    }

    @Test
    void itShouldBeDetectMissingArgument() throws ServletException, IOException {
        String[] params = new String[] {"param1", "param2"};

        when(request.getParameter("param1")).thenReturn("valid");
        when(request.getParameter("param2")).thenReturn(null);

        servlet = spy(servlet);
        doNothing().when(servlet).responseToFailure(any(HttpServletRequest.class), any(HttpServletResponse.class), any(String[].class), anyString());

        assertFalse(servlet.checkMandatoryParameters(request, response, params, "/error"));
        verify(servlet, atLeastOnce()).responseToFailure(eq(request), eq(response), eq(params), eq("/error"));
    }
}