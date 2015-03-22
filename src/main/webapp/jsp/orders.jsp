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
    <div class="col-md-9">
        <h2>Здравствуйте, ${sessionScope.username}</h2>
    </div>
    <div class="col-md-3">
        <h3><a href="/home">На главную</a></h3>
    </div>
    <%--<br/>--%>
    <div class="col-md-12">
        <table class="table">
            <h3>Заказы</h3>
            <thead>
            <tr>
                <th>Номер заказа</th>
                <th>Район</th>
                <th>Пицца</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.orders}" var="order">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.destination.name}</td>
                    <td>${order.pizza_title}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="col-md-12">
        <h3>Сделать заказ</h3>
        <form class="form-inline" role="form" action="/orders/add" method="post">
            <div class="form-group">
                <label for="district_create">Район проживания:</label>
                <input type="text" class="form-control" name="district" id="district_create"/>
            </div>
            <div class="form-group">
                <label for="pizza_create">Название пиццы:</label>
                <input type="text" class="form-control" name="pizza" id="pizza_create"/>
            </div>
            <button type="submit" class="btn btn-success">Создать заказ</button>
        </form>
        <c:if test="${requestScope.create_error_message != null}">
            <div class="alert alert-warning">
                <strong>Ошибка!</strong> Сообщение: ${requestScope.create_error_message}.
            </div>
        </c:if>
        <h3>Изменить заказ</h3>
        <form class="form-inline" role="form" action="/orders/update" method="post">
            <div class="form-group">
                <label for="id_update">Номер заказа:</label>
                <input type="text" class="form-control" name="id" id="id_update"/>
            </div>
            <div class="form-group">
                <label for="district_update">Район проживания:</label>
                <input type="text" class="form-control" name="district" id="district_update"/>
            </div>
            <div class="form-group">
                <label for="pizza_update">Название пиццы:</label>
                <input type="text" class="form-control" name="pizza" id="pizza_update"/>
            </div>
            <button type="submit" class="btn btn-success">Изменить заказ</button>
        </form>
        <c:if test="${requestScope.update_error_message != null}">
            <div class="alert alert-warning">
                <strong>Ошибка!</strong> Сообщение: ${requestScope.update_error_message}.
            </div>
        </c:if>
        <h3>Удалить заказ</h3>
        <form class="form-inline" role="form" action="/orders/delete" method="post">
            <div class="form-group">
                <label for="id_delete">Номер заказа:</label>
                <input type="text" class="form-control" name="id" id="id_delete"/>
            </div>
            <button type="submit" class="btn btn-success">Удалить заказ</button>
        </form>
        <c:if test="${requestScope.delete_error_message != null}">
            <div class="alert alert-warning">
                <strong>Ошибка!</strong> Сообщение: ${requestScope.delete_error_message}.
            </div>
        </c:if>
    </div>
</div>



</body>
</html>
