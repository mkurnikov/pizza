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
    <div class="col-md-9">
        <h2>Здравствуйте, ${sessionScope.username}</h2>
    </div>
    <div class="col-md-3">
        <h3><a href="/home">На главную</a></h3>
    </div>
    <%--<br/>--%>
    <div class="col-md-6">
        <br/>
        <br/>
        <img src="/img/map" alt="Карта не загрузилась" width="482" height="371"/>
    </div>
    <div class="col-md-6">
        <h3>Добавить путь</h3>
        <form class="form-inline" role="form" action="/path/add" method="post">
            <div class="form-group">
                <label for="source_create">Начало пути:</label>
                <input type="text" class="form-control" name="source" id="source_create"/>
            </div>
            <div class="form-group inline">
                <label for="destination_create">Конец пути:</label>
                <input type="text" class="form-control" name="destination" id="destination_create"/>
            </div>
            <div class="form-group">
                <label for="time_create">Время пути:</label>
                <input type="text" name="time" class="form-control" id="time_create"/>
            </div>
            <button type="submit" class="btn btn-success">Добавить путь</button>
        </form>
        <c:if test="${requestScope.create_error_message != null}">
            <div class="alert alert-warning">
                <strong>Ошибка!</strong> Сообщение: ${requestScope.create_error_message}.
            </div>
        </c:if>

        <h3>Изменить путь</h3>
        <form class="form-inline" role="form" action="/path/update" method="post">
            <div class="form-group">
                <label for="source_update">Начало пути:</label>
                <input type="text" class="form-control" name="source" id="source_update"/>
            </div>
            <div class="form-group">
                <label for="destination_update">Конец пути:</label>
                <input type="text" class="form-control" name="destination" id="destination_update"/>
            </div>
            <div class="form-group">
                <label for="time_update">Время пути:</label>
                <input type="text" name="time" class="form-control" id="time_update"/>
            </div>
            <button type="submit" class="btn btn-success">Изменить путь</button>
        </form>
        <c:if test="${requestScope.update_error_message != null}">
            <div class="alert alert-warning">
                <strong>Ошибка!</strong> Сообщение: ${requestScope.update_error_message}.
            </div>
        </c:if>

        <h3>Удалить путь</h3>
        <form class="form-inline" role="form" action="/path/delete" method="post">
            <div class="form-group">
                <label for="source_delete">Начало пути:</label>
                <input type="text" class="form-control" name="source" id="source_delete"/>
            </div>
            <div class="form-group inline">
                <label for="destination_delete">Конец пути:</label>
                <input type="text" class="form-control" name="destination" id="destination_delete"/>
            </div>
            <button type="submit" class="btn btn-success">Удалить путь</button>
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
