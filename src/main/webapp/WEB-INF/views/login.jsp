<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>

<h1>Iniciar Sesión</h1>

<c:if test="${not empty errorLogin}">
    <p class="alert-error">${errorLogin}</p>
</c:if>

<form method="post" action="<c:url value='/login'/>">

    <label>
        Usuario:
        <input type="text" name="username" required>
    </label>

    <label>
        Contraseña:
        <input type="password" name="password" required>
    </label>

    <button type="submit">Ingresar</button>

</form>

</body>
</html>