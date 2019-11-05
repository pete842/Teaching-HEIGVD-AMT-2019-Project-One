package ch.heigvd.amt.projectone.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Getter
@EqualsAndHashCode
public class Pagination {
    public static final Integer DEFAULT_SIZE = 5;
    public static final Integer DEFAULT_NUMBER = 1;

    private Integer size;
    private Integer number;

    public Pagination() {
        this(DEFAULT_SIZE, DEFAULT_NUMBER);
    }

    public Pagination(Integer size, Integer number) {
        this.size = size;
        this.number = number;
    }

    public Pagination from(HttpServletRequest req) {
        if (req.getParameter("pageSize") != null) {
            size = Integer.parseInt(req.getParameter("pageSize"));
        }

        if (req.getParameter("pageNumber") != null) {
            number = Integer.parseInt(req.getParameter("pageNumber"));
        }

        return this;
    }

    public void set(HttpServletRequest req) {
        req.setAttribute("pageSize", size.toString());
        req.setAttribute("pageNumber", number.toString());
    }
}
