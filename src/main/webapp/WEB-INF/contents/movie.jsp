<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-header">
    <div class="page-header-image" data-parallax="true"
         style="background-image: url('${pageContext.request.contextPath}/assets/img/bg6.jpg');">
    </div>
    <div class="content-center">
        <div class="container">
            <h1 class="title">Media sheet</h1>
            <h3 class="title">${movie.getTitle()}
                <span style="font-size: 0.7em;vertical-align: middle">
                    (<fmt:formatDate value="${movie.getRelease()}"
                                    pattern="MM.dd.yyyy"/>)
                </span></h3>

            <p>
                <span class="float-left position-absolute" style="left:0">
                    <c:forTokens var="tag" items="${movie.getMainGenre()}" delims="|">
                     <span class="badge badge-primary">
                         <c:out value="${tag}"/>
                     </span>
                    </c:forTokens>
                </span>

                <span data-toggle="tooltip" data-placement="top"
                      title="${movie.getRating()}">
                    <c:forEach begin="1" end="${Math.round(movie.getRating()/10)}" step="1">
                        <i class="fa fa-star"></i>
                    </c:forEach>
                </span>

                <span class="position-absolute" style="right:0"><i class="fa fa-clock"></i>${movie.getDuration()}</span>
            </p>


            <a onclick="goBack()" class="btn btn-primary">Back</a>
        </div>
    </div>
</div>

<script>
    function goBack() {
        window.history.back();
    }
</script>