<%--
  Created by IntelliJ IDEA.
  User: rail
  Date: 11/11/23
  Time: 3:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New products</title>
</head>
<body>
<h1>New products adding page</h1>

<form action="/admin/addProducts" method="post">
    <label for="productName"> Product:
        <input type="text" name="productName" id="productName">
    </label>

    <label for="description"> Description:
        <input type="description" name="description" id="description">
    </label>

    <label for="quantity"> Quantity:
        <input type="text" name="quantity" id="quantity">
    </label>

    <label for="price"> Price:
        <input type="text" name="price" id="price">
    </label>

    <br/>

    <input type="submit" value="add">
</form>

</body>
</html>
