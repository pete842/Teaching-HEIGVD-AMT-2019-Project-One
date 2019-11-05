<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseNumber var="nbPages" integerOnly="true" type="number" value="${totalEntriesToWatch / pageSize - 1}"/>

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
        <h2 id="towatch">To watch</h2>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Release date</th>
                <th scope="col">Duration</th>
                <th scope="col">Genre</th>
                <th scope="col">Rating</th>
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
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page nav to watch">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <select class="form-control bg-default text-white" id="selectPageSize"
                            onchange='location.href="home?pageNumber="
                                    + Math.min(${pageNumber}, ${totalEntriesWatched} / this.value - 1)
                                    + "&pageSize="
                                    + this.value
                                    + "#towatch"' >
                        <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="${pageSize * (nbPages + 1)}">All</option>
                    </select>
                    <script>
                        let i;
                        switch (${pageSize}) {
                            case 5:
                                i = 0;
                                break;
                            case 10:
                                i = 1;
                                break;
                            case 20:
                                i = 2;
                                break;
                            case 50:
                                i = 3;
                                break;
                            default:
                                i = 4;
                        }
                    </script>
                </li>
                <li class="page-item <c:if test="${pageNumber == 0}">disabled</c:if>">
                    <a class="page-link "
                       onclick="location.href=this.href+'&pageSize='+pageSize+'#towatch';return false;"
                       href="home?pageNumber=${pageNumber-1}"><i class="fa fa-angle-double-left" aria-hidden="true"></i></a>
                </li>
                <c:choose>
                    <c:when test="${nbPages > 7}">
                        <c:if test="${pageNumber > 3}">
                            <li class="page-item">
                                <a class="page-link" href="home?pageNumber=1&amp;pageSize=${pageSize}#towatch">0</a>
                            </li>
                            <li class="page-item">
                                <p>...
                                <p/>
                            </li>
                        </c:if>
                        <c:if test="${pageNumber < 4}">
                            <c:forEach var="i" begin="0" end="${pageNumber + 1}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="home?pageNumber=${i}&amp;pageSize=${pageSize}#towatch">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pageNumber > 3 && pageNumber < nbPages - 3}">
                            <c:forEach var="i" begin="${pageNumber - 1}" end="${pageNumber + 1}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="home?pageNumber=${i}&amp;pageSize=${pageSize}#towatch">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>


                        <c:if test="${pageNumber >= nbPages - 3}">
                            <c:forEach var="i" begin="${Math.min(pageNumber - 1, nbPages - 2)}" end="${nbPages}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="home?pageNumber=${i}&amp;pageSize=${pageSize}#towatch">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pageNumber < nbPages - 3}">
                            <li class="page-item">
                                <p>...
                                <p/>
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="home?pageNumber=${nbPages}&amp;pageSize=${pageSize}#towatch">${nbPages}</a>
                            </li>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${pageNumber < 3}">
                            <c:forEach var="i" begin="0" end="${nbPages}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="home?pageNumber=${i}&amp;pageSize=${pageSize}#towatch">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <li class="page-item <c:if test="${pageNumber == nbPages}">disabled</c:if>">
                    <a class="page-link " href="home?pageNumber=${pageNumber+1}&amp;pageSize=${pageSize}#towatch"><i
                            class="fa fa-angle-double-right" aria-hidden="true"></i></a>
                </li>
            </ul>
        </nav>
    </div>
</div>

