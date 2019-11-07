package ch.heigvd.amt.projectone.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaginationTest {
    @Mock
    HttpServletRequest request;

    Pagination pagination;

    @BeforeEach
    public void setup() {
        pagination = new Pagination();
    }

    @Test
    public void itShouldHaveDefaultIfMissingParam() {
        when(request.getParameter("pageSize")).thenReturn(null);
        when(request.getParameter("pageNumber")).thenReturn(null);

        pagination.from(request);

        assertEquals(pagination.getNumber(), Pagination.DEFAULT_NUMBER);
        assertEquals(pagination.getSize(), Pagination.DEFAULT_SIZE);
    }

    @Test
    public void itShouldGetExistingParam() {
        Integer size = 11;
        Integer number = 666;
        when(request.getParameter("pageSize")).thenReturn(size.toString());
        when(request.getParameter("pageNumber")).thenReturn(number.toString());

        pagination.from(request);

        assertEquals(number, pagination.getNumber());
        assertEquals(size, pagination.getSize());
    }

    @Test
    public void itShouldPushPaginationConfiguration() {
        Integer size = 11;
        Integer number = 666;
        when(request.getParameter("pageSize")).thenReturn(size.toString());
        when(request.getParameter("pageNumber")).thenReturn(number.toString());

        pagination.from(request);
        pagination.setOn(request);

        verify(request, atLeastOnce()).setAttribute(eq("pageSize"), eq(size.toString()));
        verify(request, atLeastOnce()).setAttribute(eq("pageNumber"), eq(number.toString()));
    }
}