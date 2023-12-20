<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Admin Panel</title>
    <style>
        .role-user {
            color: blue;
        }

        .role-admin {
            color: red;
        }
    </style>
</head>
<body>
<h1>Welcome to Admin Panel</h1>

<h2>All Users:</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Role</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <span class="role-admin">${user.role}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="role-user">${user.role}</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
