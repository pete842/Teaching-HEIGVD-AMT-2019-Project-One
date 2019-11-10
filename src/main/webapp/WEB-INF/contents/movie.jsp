<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-header page-header-small">
    <div class="page-header-image" data-parallax="true"
         style="background-image: url('${pageContext.request.contextPath}/assets/img/bg6.jpg');">
    </div>
    <div class="content-center">
        <div class="container">
            <h1 class="title">Media sheet</h1>
            <h3 class="title">${sessionScope.firstname} ${sessionScope.lastname}
                <span class="float-right"><i class="fa fa-clock"></i>movie.getDuration()</span></h3>
        </div>
    </div>
</div>
<div class="section">
    <div class="container">
        <div class="card">
            <h5 class="card-header">movie.getTitle()</h5>
            <p data-toggle="tooltip" data-placement="right" title="${movie.getRating()}">
                <c:forEach begin="0" end="${Math.round(movie.getRating()/10)}" step="1">
                    <i class="fa fa-star"></i>
                </c:forEach>
            </p>
            <div class="card-body">
                <p>
                    <c:forTokens var="tag" items="${movie.getMainGenre()}" delims="|">
                     <span class="badge badge-primary">
                         <c:out value="${tag}"/>
                     </span>
                    </c:forTokens>
                </p>
                <h6>movie.getRelease()</h6>
                <a href="#" class="btn btn-primary">Back</a>
            </div>
        </div>
    </div>
</div>
