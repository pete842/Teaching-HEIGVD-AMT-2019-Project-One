<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseNumber var="nbPages" integerOnly="true" type="number" value="${totalEntriesToWatch / pageSize}"/>

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
            <h1 class="title">Welcome to your media library</h1>
            <h3 class="title">${sessionScope.firstname} ${sessionScope.lastname}</h3>
            <div class="content">
                <a href="home#towatch" class="btn btn-primary disabled">
                    <div class="social-description">
                        <h2>${totalEntriesToWatch}</h2>
                        <p>To Watch</p>
                    </div>
                </a>

                <a href="watched#watched" class="btn btn-outline-secondary">
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
        <button class="btn btn-wd btn-primary btn-round float-right"><i class="fa fa-plus"></i> Add</button>
        <h2 id="towatch">To watch</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Release date</th>
                <th scope="col" class="text-right">Duration</th>
                <th scope="col">Genre</th>
                <th scope="col" class="text-right">IMBG</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="current" items="${toWatch}">
                <tr>
                    <td><c:out value="${current.getMedia().getTitle()}"/></td>
                    <td><fmt:formatDate value="${current.getMedia().getRelease()}"
                                        pattern="MM.dd.yyyy HH:mm"/>
                    </td>
                    <td class="text-right"><c:out value="${current.getMedia().getDuration()}"/>'</td>
                    <td>
                        <c:forTokens var="tag" items="${current.getMedia().getMainGenre()}" delims="|">
                        <span class="badge badge-primary">
                            <c:out value="${tag}"/>
                        </span>
                        </c:forTokens>
                    </td>
                    <td class="text-right">
                        <c:out value="${current.getMedia().getRating()}"/>
                        <i class="fa fa-star"></i>
                    </td>
                    <td class="text-right">
                        <div class="dropdown">
                            <form method="post" action="home" id="delete${current.getMedia().getId()}">
                                <input type="hidden" name="delete"/>
                                <input type="hidden" name="media_id" value="${current.getMedia().getId()}"/>
                            </form>
                            <a class="text-black" id="dropdownMenuButton" href="#"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i
                                    class="fa fa-edit"></i></a>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <a class="dropdown-item" href="#"><i class="fa fa-check"></i>Watched</a>
                                <a class="dropdown-item" style="cursor: pointer"
                                   onclick='document.getElementById("delete" + ${current.getMedia().getId()}).submit()'><i
                                        class="fa fa-trash "></i>Delete</a>
                            </div>
                        </div>
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
            <jsp:param name="postUrl" value="#towatch"/>
        </jsp:include>
    </div>
</div>

