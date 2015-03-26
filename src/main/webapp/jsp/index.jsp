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
    <c:choose>
        <c:when test="${sessionScope.username != null}">
            <h2>Здравствуйте, ${sessionScope.username}</h2>
        </c:when>
        <c:otherwise>
            <h2>Здравствуйте, Аноним</h2>
            <p>Для получения доступа к функциональности добавления заказов - войдите.</p>
        </c:otherwise>
    </c:choose>
    <h3><a href="/home">К таблице заказов</a></h3>
    <c:if test="${sessionScope.username == null}">
        <h3><a href="/login">Логин</a></h3>
        <h3><a href="/register">Зарегистрироваться</a></h3>
    </c:if>
</div>
</body>
</html>
