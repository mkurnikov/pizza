<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Pizza Delivery</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/styles.css"/>
</head>
<body>
<form action="/login" method="post">
    <p>Login</p>
    <c:if test="${requestScope.loginFailed}">Login failed previously</c:if>
    <input type="text" name="login" placeholder="username"/>
    <input type="password" name="password" placeholder="password"/>
    <input type="checkbox" name="remember" value="Remember me" checked="checked"/>
    <input type="submit" value="Submit"/>
    <br/>
    <a href="/register">Register</a>
</form>
</body>
</html>