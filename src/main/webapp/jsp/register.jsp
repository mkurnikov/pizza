<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Pizza Delivery</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/styles.css"/>
    <script type="text/javascript" src="/js/jquery-1.11.2.js"></script>

</head>
<body>
<div class="container">
    <h2>Регистрация</h2>
    <form class="form-inline" role="form" action="/register" method="post">
        <c:if test="${requestScope.error_message}">${requestScope.error_message}</c:if>
        <div class="form-group">
            <label for="username">Имя:</label>
            <input type="text" class="form-control" name="username" id="username"/>
        </div>
        <div class="form-group">
            <label for="login">Логин:</label>
            <input type="text" class="form-control" name="login" id="login"/>
        </div>
        <div class="form-group">
            <label for="pwd">Пароль:</label>
            <input type="password" name="password" id="pwd"/>
        </div>
        <button type="submit" class="btn btn-default">Зарегистрироваться</button>
    </form>
</div>
</body>
</html>