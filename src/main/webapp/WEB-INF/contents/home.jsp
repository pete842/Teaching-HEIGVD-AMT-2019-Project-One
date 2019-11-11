<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="page-header page-header-small">
    <div class="page-header-image" data-parallax="true"
         style="background-image: url('${pageContext.request.contextPath}/assets/img/bg6.jpg');">
    </div>
    <div class="content-center">
        <div class="container">
            <h1 class="title">Welcome to your media library</h1>
            <h3 class="title">${sessionScope.firstname} ${sessionScope.lastname}</h3>
            <div class="content">
                <a href="home" class="btn btn-primary disabled">
                    <div class="social-description">
                        <h2>${totalEntriesToWatch}</h2>
                        <p>To Watch</p>
                    </div>
                </a>

                <a href="watched" class="btn btn-outline-secondary">
                    <div class="social-description">
                        <h2>${totalEntriesWatched}</h2>
                        <p>Watched</p>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <button class="btn btn-wd btn-primary btn-round float-right" onclick="document.location.href='movies'"><i class="fa fa-plus"></i> Add</button>
        <h2 id="towatch">To watch</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Genre</th>
                <th scope="col">Release date</th>
                <th scope="col" class="text-right">Duration</th>
                <th scope="col" class="text-right">IMBD</th>
                <th scope="col"></th>
                <th scope="col" class="text-center"><i class="fa fa-search"></i></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="current" items="${toWatch}">
                <tr>
                    <td><c:out value="${current.getMedia().getTitle()}"/></td>
                    <td>
                        <c:forTokens var="tag" items="${current.getMedia().getMainGenre()}" delims="|">
                        <span class="badge badge-primary">
                            <c:out value="${tag}"/>
                        </span>
                        </c:forTokens>
                    </td>
                    <td><fmt:formatDate value="${current.getMedia().getRelease()}"
                                        pattern="MM.dd.yyyy"/>
                    </td>
                    <td class="text-right"><c:out value="${current.getMedia().getDuration()}"/>'</td>
                    <td class="text-right">
                        <c:out value="${current.getMedia().getRating()}"/>
                        <i class="fa fa-star"></i>
                    </td>
                    <td class="text-right">
                        <div class="dropdown">
                            <form method="post" action="media_user?action=delete" id="delete${current.getMedia().getId()}">
                                <input type="hidden" name="media_id" value="${current.getMedia().getId()}"/>
                                <input type="hidden" name="back" value="home?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"/>
                            </form>
                            <a class="text-black dropdown-toggle" id="dropdownMenuButton" href="#"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i
                                    class="fa fa-edit dropdown-toggle-split"></i></a>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a  href="#" class="dropdown-item setWatched" data-toggle="modal" data-target="#watchedModal"
                                   data-id="${current.getMedia().getId()}"><i
                                        class="fa fa-check"></i>Watched</a>
                                <a class="dropdown-item" style="cursor: pointer"
                                   onclick='document.getElementById("delete" + ${current.getMedia().getId()}).submit()'><i
                                        class="fa fa-trash "></i>Delete</a>
                            </div>
                        </div>
                    </td>
                    <td scope="col" class="text-center">
                        <a href="movie?media_id=${current.getMedia().getId()}&amp;back=home?pageNumber=${pageNumber}&amp;pageSize=${pageSize}" class="btn btn-sm btn-neutral m-0 p-1 text-black" title="More info">
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
            <jsp:param name="totalEntries" value="${totalEntriesWatched}"/>
            <jsp:param name="preUrl" value="home?"/>
            <jsp:param name="postUrl" value=""/>
        </jsp:include>
    </div>
</div>

<div class="modal fade" id="watchedModal" tabindex="-1" role="dialog" aria-labelledby="watchedModalLabel" aria-modal="true"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header justify-content-center">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    <i class="now-ui-icons ui-1_simple-remove"></i>
                </button>
                <h4 class="title title-up">When did you watched it?<br>Was it good?</h4>
            </div>
            <form method="post" action="media_user?action=put">
                <div class="modal-body">
                    <input type="hidden" value="" name="media_id" id="watchedModalMediaId"/>
                    <input type="hidden" name="back" value="home?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"/>

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
            $("#watchedModalMediaId").attr("value", id);
        });
    });
</script>

