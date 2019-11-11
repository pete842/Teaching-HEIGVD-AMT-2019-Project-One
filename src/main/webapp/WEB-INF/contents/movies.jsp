<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    let pageSize = '${pageSize}';
    console.log(pageSize);
</script>

<div class="page-header page-header-small">
    <div class="page-header-image" data-parallax="true"
         style="background-image: url('${pageContext.request.contextPath}/assets/img/bg6.jpg');">
    </div>
    <div class="content-center">
        <div class="container">
            <h1 class="title">Media library</h1>
            <div class="content">
                <a href="home" class="btn btn-primary disabled">
                    <div class="social-description">
                        <h2>${totalEntries}</h2>
                        <p>Movies</p>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <h2 id="movies">Movies</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Release date</th>
                <th scope="col" class="text-right">Duration</th>
                <th scope="col">Genre</th>
                <th scope="col" class="text-center">IMBG</th>
                <th scope="col" class="text-center">Seen?</th>
                <th scope="col" class="text-center"><i class="fa fa-search"></i></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="current" items="${movies}">
                <tr>
                    <td><c:out value="${current.getTitle()}"/></td>
                    <td><fmt:formatDate value="${current.getRelease()}"
                                        pattern="MM.dd.yyyy"/>
                    </td>
                    <td class="text-right"><c:out value="${current.getDuration()}"/>'</td>
                    <td>
                        <c:forTokens var="tag" items="${current.getMainGenre()}" delims="|">
                        <span class="badge badge-primary">
                            <c:out value="${tag}"/>
                        </span>
                        </c:forTokens>
                    </td>
                    <td class="text-center">
                        <c:out value="${current.getRating()}"/>
                        <i class="fa fa-star"></i>
                    </td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${current.getWatched()}">
                                <form method="post" action="media_user" id="delete${current.getId()}">
                                    <input type="hidden" name="media_id" value="${current.getId()}"/>
                                    <input type="hidden" name="action" value="delete"/>
                                    <input type="hidden" name="back"
                                           value="movies?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"/>
                                </form>
                                <a class="btn btn-sm btn-neutral m-0 p-1 text-black" title="Remove from watched list?"
                                   onclick='document.getElementById("delete${current.getId()}").submit()'><i
                                        class="fa fa-eye"></i></a>
                            </c:when>
                            <c:when test="${current.getInserted()}">
                                <a class="btn btn-sm btn-neutral m-0 p-1 text-black setWatched" title="You watched it?"
                                   data-toggle="modal"
                                   data-target="#watchedModal" data-id="${current.getId()}" data-action="put"><i
                                        class="fa fa-eye-slash"></i></a>
                                <a class="btn btn-sm btn-neutral m-0 p-1 text-black disabled" title="You already want to watch it.">
                                    <i class="fa fa-calendar-check "></i>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <form method="post" action="media_user" id="toWatch${current.getId()}">
                                    <input type="hidden" name="media_id" value="${current.getId()}"/>
                                    <input type="hidden" name="back"
                                           value="movies?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"/>
                                </form>
                                <a class="btn btn-sm btn-neutral m-0 p-1 text-black setWatched" title="You watched it?"
                                   data-toggle="modal"
                                   data-target="#watchedModal" data-id="${current.getId()}" data-action="post"><i
                                        class="fa fa-eye-slash"></i>
                                </a>
                                <a class="btn btn-sm btn-neutral m-0 p-1 text-black" title="You want to watch it?"
                                   onclick='document.getElementById("toWatch${current.getId()}").submit()'>
                                    <i class="fa fa-calendar-alt "></i>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td scope="col" class="text-center">
                        <a href="movie?media_id=${current.getId()}&amp;back=movies?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"
                           class="btn btn-sm btn-neutral m-0 p-1 text-black setWatched" title="More info">
                            <i class="fa fa-search"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/WEB-INF/components/pagination.jsp">
            <jsp:param name="nbPages" value="${nbPages}"/>
            <jsp:param name="pageSize" value="${pageSize}"/>
            <jsp:param name="pageNumber" value="${pageNumber}"/>
            <jsp:param name="totalEntries" value="${totalEntries}"/>
            <jsp:param name="preUrl" value="movies?"/>
            <jsp:param name="postUrl" value=""/>
        </jsp:include>
    </div>
</div>

<div class="modal fade" id="watchedModal" tabindex="-1" role="dialog" aria-labelledby="watchedModalLabel"
     aria-modal="true"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header justify-content-center">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <i class="now-ui-icons ui-1_simple-remove"></i>
                </button>
                <h4 class="title title-up">When did you watched it?<br>Was it good?</h4>
            </div>
            <form action="media_user" method="post">
                <div class="modal-body">
                    <input type="hidden" value="" name="media_id" id="watchedModalMediaId"/>
                    <input type="hidden" value="" name="action" id="watchedModalAction"/>
                    <input type="hidden" name="back" value="movies?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"/>

                    <div class="form-group">
                        <label for="watchedDate">Date of seen</label>
                        <input type="text" id="watchedDate" class="form-control date-picker"
                               data-datepicker-color="primary" name="watched">
                    </div>
                    <div class="form-group">
                        <label for="watchedRating">Rating: <i class="fa fa-star"></i> <span id="ratingValue"
                                                                                            class="w-25">50</span></label>
                        <input type="range" class="form-control-range custom-range" id="watchedRating" min="0" max="100"
                               value="50" name="rating" oninput='$("#ratingValue").text(this.value);'>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('.setWatched').click(function () {
            let id = $(this).data('id');
            let action = $(this).data('action');
            $("#watchedModalMediaId").attr("value", id);
            $("#watchedModalAction").attr("value", action);
        });
    });
</script>
