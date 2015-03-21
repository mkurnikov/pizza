<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Pizza Delivery</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/styles.css"/>
</head>
<body>
    <p>Hello, ${requestScope.login}</p>
    <p>Paths: ${requestScope.paths}</p>
    <p>Paths: ${requestScope.districts}</p>
    <p>Error message: ${requestScope.error_message}</p>
    <p>Shortest path: ${requestScope.shortest}</p>

    <form action="/shortest" method="post">
        <input type="text" name="source" placeholder="source"/>
        <input type="text" name="destination" placeholder="destination"/>
        <input type="submit" value="Find"/>
    </form>

    <form action="/home" method="post">
        <input type="text" name="district1" placeholder="district1"/>
        <input type="text" name="district2", placeholder="district2"/>
        <input type="text" name="travellingTime" placeholder="time"/>
        <input type="submit" value="Submit"/>
    </form>
    <img src="/img/map" alt="Карта не загрузилась" width="482" height="371"/>
</body>
</html>