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
        }
    </style>
</head>

<body>
<script type="text/javascript">
    var shortest = function (target) {
//        alert('clicked!');
        var link = '/shortest?id=' + target.find('.id').html() + "&source=" + target.find('.source').html();
//        alert(link);
        window.location.href = link;

//        $.get('/shortest', {
//            data: {'id': target.find('.id').html()},
//            async: true
//        });
    };
</script>
<div class="container">
    <div class="col-md-9">
        <h2>Hello, ${sessionScope.login}</h2>
    </div>
    <div class="col-md-3">
        <h3>
            <a href="/orders">Сделать заказ</a>
        </h3>
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
            <%--<c:if test="${sessionScope.orders_sorted != null}">--%>

            <%--</c:if>--%>
            <%--<c:if test="${sessionScope.orders != null}">--%>
            <%--<c:forEach items="${sessionScope.orders}" var="order">--%>
            <%--<tr>--%>
            <%--<td>${order.orderId}</td>--%>
            <%--<td>---</td>--%>
            <%--<td>${order.destination.name}</td>--%>
            <%--<td>---</td>--%>
            <%--</tr>--%>
            <%--</c:forEach>--%>
            <%--</c:if>--%>
            </tbody>
        </table>
    </div>
    <div class="col-md-6">
        <img src="/img/map" alt="Карта не загрузилась" width="482" height="371"/>
    </div>
    <div class="col-md-10">
        <div class="col-md-12">
            <h3>Кратчайший путь для заказа</h3>
            <br/>
            <p>${sessionScope.currentPath}</p>
            <%--<p><b>Красносельский</b> -> 10.0 -> Кронштадский -> 12.0 -> <b>Невский</b></p>--%>
        </div>
        <div class="col-md-12">
            <h3>Полный путь для всех заказов</h3>
            <%--<p><b>Красносельский</b> -> 10.0 -> Кронштадский -> 12.0 -> <b>Невский</b> -> 16.0 ->--%>
            <%--<b>Красносельский</b> -> 10.0 -> Кронштадский -> 12.0 -> <b>Невский</b></p>--%>
            <p>${sessionScope.fullPath}</p>
        </div>
    </div>
    <div class="col-md-2">
        <form action="/shortest" method="post">
            <input type="submit" value="Найти пути"/>
        </form>
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