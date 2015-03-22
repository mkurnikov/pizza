<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Pizza Delivery</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/styles.css"/>
</head>

<body>
<div class="container">
    <h2>Hello, ${sessionScope.login}</h2>
    <br/>
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
        <form action="/orders/add" method="post">
            <input type="text" name="district" placeholder="Район проживания"/>
            <input type="text" name="pizza" placeholder="Пицца"/>
            <input type="submit" value="Заказать"/>
        </form>
        <h3>Изменить заказ</h3>
        <form action="/orders/update" method="post">
            <input type="text" name="id" placeholder="Номер заказа"/>
            <input type="text" name="district" placeholder="Район проживания"/>
            <input type="text" name="pizza" placeholder="Пицца"/>
            <input type="submit" value="Изменить заказ"/>
        </form>
        <h3>Удалить заказ</h3>
        <form action="/orders/delete" method="post">
            <input type="text" name="id" placeholder="Номер заказа"/>
            <input type="submit" value="Удалить"/>
        </form>
    </div>
</div>



</body>
</html>
