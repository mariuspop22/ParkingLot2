<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:pageTemplate pageTitle="Edit User">
    <h1>Edit User</h1>

    <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/EditUser">
        <div class="row">

            <div class="col-md-6 mb-3">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="" value="${user.email}" required>
                <div class="invalid-feedback">
                    Email is required.
                </div>
            </div>


            <div class="col-md-6 mb-3">
                <label for="username">username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="" value="${user.username}" required>
                <div class="invalid-feedback">
                    Username is required.
                </div>
            </div>


            <div class="col-md-6 mb-3">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="" value="">

                <div class="invalid-feedback">
                    Password is not required.
                </div>
                <input type="hidden" name="user_id" value="${user.id}" />
            </div>
        </div>


        <div class="row">
            <div class="col-md-6 mb-3">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>

    </form>

    <script src="checkout.js" class="astro-vvvwv3sm"></script>
</t:pageTemplate>
