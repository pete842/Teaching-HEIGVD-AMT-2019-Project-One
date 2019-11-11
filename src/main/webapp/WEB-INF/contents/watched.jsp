<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseNumber var="nbPages" integerOnly="true" type="number" value="${Math.ceil(totalEntriesWatched / pageSize)}"/>

<div class="page-header page-header-small">
    <div class="page-header-image" data-parallax="true"
         style="background-image: url('${pageContext.request.contextPath}/assets/img/bg6.jpg');">
    </div>
    <div class="content-center">
        <div class="container">
            <h1 class="title">Welcome to your media library</h1>
            <h3 class="title">${sessionScope.firstname} ${sessionScope.lastname}</h3>
            <div class="content">
                <a href="home" class="btn btn-outline-secondary">
                    <div class="social-description">
                        <h2>${totalEntriesToWatch}</h2>
                        <p>To Watch</p>
                    </div>
                </a>

                <a href="watched" class="btn btn-primary disabled">
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
        <button class="btn btn-wd btn-primary btn-round float-right" type="button" onclick="document.location.href='movies'"><i class="fa fa-plus"></i> Add</button>
        <h2 id="watched">Watched</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Genres</th>
                <th scope="col" class="text-right">Watched</th>
                <th scope="col" class="text-right">Release</th>
                <th scope="col" class="text-right">Duration</th>
                <th scope="col" class="text-right">Rating</th>
                <th scope="col" class="text-right">IMBD</th>
                <th scope="col"></th>
                <th scope="col" class="text-center"><i class="fa fa-search"></i></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="current" items="${watched}">
                <tr>
                    <td><c:out value="${current.getMedia().getTitle()}"/></td>
                    <td>
                        <c:forTokens var="tag" items="${current.getMedia().getMainGenre()}" delims="|">
                        <span class="badge badge-primary">
                            <c:out value="${tag}"/>
                        </span>
                        </c:forTokens>
                    </td>
                    <td class="text-right"><fmt:formatDate value="${current.getWatched()}"
                                                           pattern="MM.dd.yyyy"/>
                    </td>
                    <td class="text-right"><fmt:formatDate value="${current.getMedia().getRelease()}"
                                                           pattern="MM.dd.yyyy"/>
                    </td>
                    <td class="text-right"><c:out value="${current.getMedia().getDuration()}"/>'</td>
                    <td class="text-right">
                        <c:out value="${current.getRating()}"/>
                        <i class="fa fa-star" style="color: gold"></i>
                    </td>
                    <td class="text-right">
                        <c:out value="${current.getMedia().getRating()}"/>
                        <i class="fa fa-star"></i>
                    </td>
                    <td class="text-right">
                        <form method="post" action="media_user?action=delete" id="delete${current.getMedia().getId()}">
                            <input type="hidden" name="media_id" value="${current.getMedia().getId()}"/>
                            <input type="hidden" name="back" value="watched?pageNumber=${pageNumber}&amp;pageSize=${pageSize}"/>
                        </form>
                        <div class="dropdown">
                            <a class="btn btn-sm btn-neutral m-0 p-1 text-black" title="Remove from the list?" style="cursor: pointer" onclick='document.getElementById("delete" + ${current.getMedia().getId()}).submit()'><i class="fa fa-trash "></i></a>
                        </div>
                    </td>
                    <td scope="col" class="text-center">
                        <a href="movie?media_id=${current.getMedia().getId()}&amp;back=watched?pageNumber=${pageNumber}&amp;pageSize=${pageSize}" class="btn btn-sm btn-neutral m-0 p-1 text-black" title="More info">
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
            <jsp:param name="preUrl" value="watched?"/>
            <jsp:param name="postUrl" value=""/>
        </jsp:include>
    </div>
</div>

