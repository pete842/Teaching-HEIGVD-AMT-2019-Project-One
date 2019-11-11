<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav aria-label="Page nav to watch" class="pagination-container justify-content-center">
    <ul class="pagination">
        <li class="page-item">
            <select class="form-control bg-default text-white" id="selectPageSize"
                    onchange='location.href="${param.preUrl}pageNumber="
                            + Math.min(${param.pageNumber}, Math.ceil(${param.totalEntries} / this.value))
                            + "&pageSize="
                            + this.value
                            + "${param.postUrl}"'>
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="${param.pageSize * (param.nbPages + 1)}">All</option>
            </select>
            <script>
                let i;
                switch (${param.pageSize}) {
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
        <li class="page-item <c:if test="${param.pageNumber <= 1}">disabled</c:if>">
            <a class="page-link " href="${param.preUrl}pageNumber=${param.pageNumber-1}&amp;pageSize=${param.pageSize}${param.postUrl}"><i
                    class="fa fa-angle-double-left" aria-hidden="true"></i></a>
        </li>
        <c:choose>
            <c:when test="${param.nbPages > 7}">
                <c:if test="${param.pageNumber > 3}">
                    <li class="page-item">
                        <a class="page-link" href="${param.preUrl}pageNumber=1&amp;pageSize=${param.pageSize}${param.postUrl}">1</a>
                    </li>
                    <li class="page-item">
                        <p>...
                        <p/>
                    </li>
                </c:if>
                <c:if test="${param.pageNumber < 4}">
                    <c:forEach var="i" begin="1" end="${Math.max(pageNumber + 1, 3)}">
                        <li class="page-item <c:if test="${param.pageNumber == i}">active</c:if> ">
                            <a class="page-link"
                               href="${param.preUrl}pageNumber=${i}&amp;pageSize=${param.pageSize}${param.postUrl}">${i}</a>
                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${param.pageNumber > 3 && param.pageNumber < param.nbPages - 2}">
                    <c:forEach var="i" begin="${param.pageNumber - 1}" end="${param.pageNumber + 1}">
                        <li class="page-item <c:if test="${param.pageNumber == i}">active</c:if> ">
                            <a class="page-link"
                               href="${param.preUrl}pageNumber=${i}&amp;pageSize=${param.pageSize}${param.postUrl}">${i}</a>
                        </li>
                    </c:forEach>
                </c:if>


                <c:if test="${param.pageNumber >= param.nbPages - 2}">
                    <c:forEach var="i" begin="${Math.min(param.pageNumber - 1, param.nbPages - 2)}" end="${param.nbPages}">
                        <li class="page-item <c:if test="${param.pageNumber == i}">active</c:if> ">
                            <a class="page-link"
                               href="${param.preUrl}pageNumber=${i}&amp;pageSize=${param.pageSize}${param.postUrl}">${i}</a>
                        </li>
                    </c:forEach>
                </c:if>
                <c:if test="${param.pageNumber < param.nbPages - 2}">
                    <li class="page-item">
                        <p>...
                        <p/>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${param.preUrl}pageNumber=${param.nbPages}&amp;pageSize=${param.pageSize}${param.postUrl}">${param.nbPages}</a>
                    </li>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:forEach var="i" begin="1" end="${param.nbPages}">
                    <li class="page-item <c:if test="${param.pageNumber == i}">active</c:if> ">
                        <a class="page-link"
                           href="${param.preUrl}pageNumber=${i}&amp;pageSize=${param.pageSize}${param.postUrl}">${i}</a>
                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <li class="page-item <c:if test="${param.nbPages < param.pageNumber + 1}">disabled</c:if>">
            <a class="page-link " href="${param.preUrl}pageNumber=${param.pageNumber+1}&amp;pageSize=${param.pageSize}${param.postUrl}"><i
                    class="fa fa-angle-double-right" aria-hidden="true"></i></a>
        </li>
    </ul>
</nav>