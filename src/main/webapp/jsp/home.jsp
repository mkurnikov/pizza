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
    <style>
        .order:hover {
            cursor: pointer;
            background-color: #afd9ee;
        }

        .active {
            background-color: #888888;
        }
    </style>
</head>

<body>
<script type="text/javascript">
    var shortest = function (target) {
        var link = '/shortest?id=' + target.find('.id').html() + "&source=" + target.find('.source').html();
        window.location.href = link;
    };
</script>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h2>Здравствуйте, ${sessionScope.username}</h2>
        </div>
        <div class="col-md-6">
            <h3 style="text-align: right;">
                <a href="/orders">Управление заказами</a>
                <c:if test="${sessionScope.admin == true}">
                    |
                    <a href="/admin">Управление путями</a>
                </c:if>
            </h3>
        </div>
    </div>

    <div class="col-md-6">
        <table class="table">
            <h3>Пути к заказам</h3>
            <thead>
            <tr>
                <th>Номер заказа</th>
                <th>Начало пути</th>
                <th>Окончание пути</th>
                <th>Время</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${sessionScope.orders_sorted != null}">
                    <c:forEach items="${sessionScope.orders_sorted}" var="order">
                        <tr onclick="shortest($(this));" class="order">
                            <td class="id">${order.order.orderId}</td>
                            <td class="source">${order.source.name}</td>
                            <td>${order.order.destination.name}</td>
                            <td>${order.overallTime}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${sessionScope.orders}" var="order">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>---</td>
                            <td>${order.destination.name}</td>
                            <td>---</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <div class="col-md-6">
        <img src="/img/map" alt="Карта не загрузилась" width="482" height="371"/>
    </div>
    <div class="col-md-9">
        <div class="col-md-12">
            <h3>Кратчайший путь для заказа</h3>
            <br/>
            <p>${sessionScope.currentPath}</p>
        </div>
        <div class="col-md-12">
            <h3>Полный путь для всех заказов</h3>
            <p>${sessionScope.fullPath}</p>
        </div>
    </div>
    <div class="col-md-3">
        <br/>
        <form role="form" action="/shortest" method="post">
            <div class="form-group">
                <label for="position">Расположение вагончика</label>
                <input type="text" class="form-control" name="startPosition"
                       value="Красносельский" id="position"/>
            </div>
            <input class="btn btn-lg btn-primary" type="submit" value="Найти пути"/>
        </form>
        <c:if test="${sessionScope.find_path_error_message != null}">
            <div class="alert alert-warning">
                <strong>Ошибка!</strong> Сообщение: ${sessionScope.find_path_error_message}
            </div>
        </c:if>
    </div>
</div>
<%--<p>Hello, ${requestScope.login}</p>--%>
<%--<p>Paths: ${requestScope.paths}</p>--%>
<%--<p>Paths: ${requestScope.districts}</p>--%>
<%--<p>Error message: ${requestScope.error_message}</p>--%>
<%--<p>Shortest path: ${requestScope.shortest}</p>--%>

<%--<form action="/shortest" method="post">--%>
<%--<input type="text" name="source" placeholder="source"/>--%>
<%--<input type="text" name="destination" placeholder="destination"/>--%>
<%--<input type="submit" value="Find"/>--%>
<%--</form>--%>

<%--<form action="/home" method="post">--%>
<%--<input type="text" name="district1" placeholder="district1"/>--%>
<%--<input type="text" name="district2", placeholder="district2"/>--%>
<%--<input type="text" name="travellingTime" placeholder="time"/>--%>
<%--<input type="submit" value="Submit"/>--%>
<%--</form>--%>
<%--<img src="/img/map" alt="Карта не загрузилась" width="482" height="371"/>--%>
</body>
</html>