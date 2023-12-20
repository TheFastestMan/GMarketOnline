<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 9/3/23
  Time: 11:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User</title>
</head>
<body>
<h1>User:</h1>
<ul>

    <li>ID: ${user.id}</li>
    <li>Username: ${user.username}</li>
    <li>Email: ${user.email}</li>
    <li>Role: ${user.role}</li>
    <!-- Display admin link if role is ADMIN -->
    <c:if test="${user.role eq 'ADMIN'}">
        <li><a href="/admin">Go to admin page</a></li>
    </c:if>
<c:if test="${user.role eq 'ADMIN'}">
    <li><a href="/admin/addProducts">Go to products adding page</a></li>

</c:if>

<li><a href="products">Choose Available Products</a></li>

    <c:if test="${not empty user}">
        <li><a href="userItems?userId=${user.id}">View User Items</a></li>
    </c:if>

</ul>
</body>
</html>
