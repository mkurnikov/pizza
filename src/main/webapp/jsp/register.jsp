<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mkurnikov
  Date: 23.02.15
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Pizza Delivery</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/styles.css"/>
</head>
<body>
<form action="/register" method="post">
    <p>Register</p>
    <c:if test="${requestScope.error_message}">${requestScope.error_message}</c:if>
    <input type="text" name="username" placeholder="username"/>
    <input type="text" name="login" placeholder="login"/>
    <input type="password" name="password" placeholder="password"/>
    <input type="checkbox" name="remember" value="Remember me" checked="checked"/>
    <input type="submit" value="Submit"/>
    <br/>
</form>
</body>
</html>
