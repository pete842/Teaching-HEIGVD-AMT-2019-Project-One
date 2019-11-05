<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:parseNumber var="nbPages" integerOnly="true" type="number" value="${totalEntriesWatched / pageSize}"/>

<div class="page-header page-header-small">
    <div class="page-header-image" data-parallax="true"
         style="background-image: url('${pageContext.request.contextPath}/assets/img/bg6.jpg');">
    </div>
    <div class="content-center">
        <div class="container">
            <h1 class="title">Welcome to your media library</h1>
            <h3 class="title">${sessionScope.firstname} ${sessionScope.lastname}</h3>
            <div class="content">
                <a href="home#towatch" class="btn btn-outline-secondary">
                    <div class="social-description">
                        <h2>${totalEntriesToWatch}</h2>
                        <p>To Watch</p>
                    </div>
                </a>

                <a href="watched#watched" class="btn btn-primary disabled">
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
        <h2 id="watched">Watched</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Title</th>
                <th scope="col">Release date</th>
                <th scope="col" class="text-right">Duration</th>
                <th scope="col">Genre</th>
                <th scope="col" class="text-right">Rating</th>
                <th scope="col" class="text-right">IMBD</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="current" items="${watched}">
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
                        <c:out value="${current.getRating()}"/>
                        <i class="fa fa-star" style="color: gold"></i>
                    </td>
                    <td class="text-right">
                        <c:out value="${current.getMedia().getRating()}"/>
                        <i class="fa fa-star"></i>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page nav to watch" class="pagination-container  justify-content-center">
            <ul class="pagination">
                <li class="page-item">
                    <select class="form-control bg-default text-white" id="selectPageSize"
                            onchange='location.href="watched?pageNumber="
                                    + Math.min(${pageNumber}, Math.ceil(${totalEntriesWatched} / this.value))
                                    + "&pageSize="
                                    + this.value
                                    + "#watched"'>
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

                        document.getElementById("selectPageSize").options[i].selected = true;
                    </script>
                </li>
                <li class="page-item <c:if test="${pageNumber == 0}">disabled</c:if>">
                    <a class="page-link " href="watched?pageNumber=${pageNumber-1}&amp;pageSize=${pageSize}#watched"><i
                            class="fa fa-angle-double-left" aria-hidden="true"></i></a>
                </li>
                <c:choose>
                    <c:when test="${nbPages > 7}">
                        <c:if test="${pageNumber > 3}">
                            <li class="page-item">
                                <a class="page-link" href="watched?pageNumber=1&amp;pageSize=${pageSize}#watched">1</a>
                            </li>
                            <li class="page-item">
                                <p>...
                                <p/>
                            </li>
                        </c:if>
                        <c:if test="${pageNumber < 4}">
                            <c:forEach var="i" begin="1" end="${Math.max(pageNumber + 1, 3)}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="watched?pageNumber=${i}&amp;pageSize=${pageSize}#watched">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pageNumber > 3 && pageNumber < nbPages - 2}">
                            <c:forEach var="i" begin="${pageNumber - 1}" end="${pageNumber + 1}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="watched?pageNumber=${i}&amp;pageSize=${pageSize}#watched">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>


                        <c:if test="${pageNumber >= nbPages - 2}">
                            <c:forEach var="i" begin="${Math.min(pageNumber - 1, nbPages - 2)}" end="${nbPages}">
                                <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                    <a class="page-link"
                                       href="watched?pageNumber=${i}&amp;pageSize=${pageSize}#watched">${i}</a>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${pageNumber < nbPages - 2}">
                            <li class="page-item">
                                <p>...
                                <p/>
                            </li>
                            <li class="page-item">
                                <a class="page-link"
                                   href="watched?pageNumber=${nbPages}&amp;pageSize=${pageSize}#watched">${nbPages}</a>
                            </li>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="i" begin="1" end="${nbPages}">
                            <li class="page-item <c:if test="${pageNumber == i}">active</c:if> ">
                                <a class="page-link"
                                   href="watched?pageNumber=${i}&amp;pageSize=${pageSize}#watched">${i}</a>
                            </li>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <li class="page-item <c:if test="${pageNumber == nbPages}">disabled</c:if>">
                    <a class="page-link " href="watched?pageNumber=${pageNumber+1}&amp;pageSize=${pageSize}#watched"><i
                            class="fa fa-angle-double-right" aria-hidden="true"></i></a>
                </li>
            </ul>
        </nav>
    </div>
</div>

