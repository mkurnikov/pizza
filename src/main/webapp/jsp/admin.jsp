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
    <%--<br/>--%>
    <div class="col-md-6">
        <img src="/img/map" alt="Карта не загрузилась" width="482" height="371"/>
    </div>
    <div class="col-md-6">
        <h3>Добавить путь</h3>
        <form action="/path/add" method="post">
            <input type="text" name="source" placeholder="Source"/>
            <input type="text" name="destination" placeholder="Destination"/>
            <input type="text" name="time" placeholder="Time"/>
            <input type="submit" value="Добавить"/>
        </form>
        <br/>

        <h3>Изменить путь</h3>
        <form action="/path/update" method="post">
            <input type="text" name="source" placeholder="Source"/>
            <input type="text" name="destination" placeholder="Destination"/>
            <input type="text" name="time" placeholder="Time"/>
            <input type="submit" value="Изменить"/>
        </form>
        <br/>

        <h3>Удалить путь</h3>
        <form action="/path/delete" method="post">
            <input type="text" name="source" placeholder="Source"/>
            <input type="text" name="destination" placeholder="Destination"/>
            <%--<input type="text" name="time" placeholder="Time"/>--%>
            <input type="submit" value="Удалить"/>
        </form>
    </div>
</div>
</body>
</html>
