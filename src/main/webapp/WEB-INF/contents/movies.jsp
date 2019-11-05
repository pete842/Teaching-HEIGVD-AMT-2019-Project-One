<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseNumber var="nbPages" integerOnly="true" type="number" value="${totalEntries / pageSize}"/>

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
                <a href="home#towatch" class="btn btn-primary disabled">
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
        <button class="btn btn-wd btn-primary btn-round float-right"><i class="fa fa-plus"></i> Add</button>
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
            </tr>
            </thead>
            <tbody>
            <c:forEach var="current" items="${movies}">
                <tr>
                    <td><c:out value="${current.getTitle()}"/></td>
                    <td><fmt:formatDate value="${current.getRelease()}"
                                        pattern="MM.dd.yyyy HH:mm"/>
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
                        <a style="cursor:pointer"><i class="fa fa-eye"></i></a>
                        <a style="cursor:pointer"><i class="fa fa-eye-slash"></i></a>
                        <a style="cursor: pointer"><i class="fa fa-calendar-alt "></i></a>
                        <a style="cursor: pointer"><i class="fa fa-calendar-check "></i></a>
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
            <jsp:param name="postUrl" value="#movies"/>
        </jsp:include>
    </div>
</div>