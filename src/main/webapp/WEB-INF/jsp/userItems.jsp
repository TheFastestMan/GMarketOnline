<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>User Items</title>
</head>
<body>
<h1>User Items</h1>

<c:if test="${not empty userItems}">
  <ul>
    <c:forEach items="${userItems}" var="productDto">
      <li>${productDto.productName} (ID: ${productDto.id}) - Price: ${productDto.price}</li>
    </c:forEach>
  </ul>
</c:if>


<c:url value="user" var="userProfileUrl" />
<a href="${userProfileUrl}">Back to User Profile</a>
</body>
</html>
