<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header data-bs-theme="dark">
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Parking Lot</a>
            <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarCollapse"
                    aria-controls="navbarCollapse"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav me-auto mb-2 mb-md-0">
                    <li class="nav-item">


                <a class="nav-link${pageContext.request.requestURI.substring(pageContext.request.requestURI.lastIndexOf("/"))
                eq '/about.jsp' ? ' active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/about.jsp">About</a>

                </li>

                    <li class="nav-item">
<c:if test="${pageContext.request.isUserInRole('READ_CARS')}">
                        <a class="nav-link ${activePage eq 'Cars' ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/Cars">Cars</a>
</c:if>
                    </li>
                    <li class="nav-item">
<c:if test="${pageContext.request.isUserInRole('READ_USERS')}">
                        <a class="nav-link ${activePage eq 'Users' ? 'active' : ''}"
                           href="${pageContext.request.contextPath}/Users">Users</a>
</c:if>
                    </li>

                </ul>

               <ul class="navbar-nav">
                   <li class="nav-item">
                       <c:choose>
                           <c:when test="${pageContext.request.getRemoteUser() == null}">
                               <a class="nav-link" href="${pageContext.request.contextPath}/Login">Login</a>
                           </c:when>
                           <c:otherwise>
                               <a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a>
                           </c:otherwise>
                       </c:choose>
                   </li>

               </ul>
            </div>
        </div>
    </nav>
</header>
<footer class="bg-dark text-light text-center py-3 mt-5" style="position: fixed; bottom: 0; width: 100%;">
    <div class="container">
        <span>&copy; 2025 Popescu Marius Valentin</span>
    </div>
</footer>
