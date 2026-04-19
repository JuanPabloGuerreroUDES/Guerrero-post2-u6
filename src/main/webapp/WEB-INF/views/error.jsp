<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Error en la aplicación</title>

    <link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>

<h1>Ha ocurrido un error</h1>

<!-- Mensaje general -->
<p class="alert-error">
    Ocurrió un problema al procesar la solicitud.
</p>

<!-- Mensaje específico si existe -->
<c:if test="${not empty requestScope['jakarta.servlet.error.message']}">
    <p>
        <strong>Detalle:</strong>
        <c:out value="${requestScope['jakarta.servlet.error.message']}" />
    </p>
</c:if>

<!-- Código de error -->
<c:if test="${not empty requestScope['jakarta.servlet.error.status_code']}">
    <p>
        <strong>Código:</strong>
            ${requestScope['jakarta.servlet.error.status_code']}
    </p>
</c:if>

<!-- Excepción (solo para depuración básica) -->
<c:if test="${not empty exception}">
    <p>
        <strong>Excepción:</strong>
        <c:out value="${exception.message}" />
    </p>
</c:if>

<!-- Navegación -->
<a href="<c:url value='/productos'/>">Volver al inicio</a>

</body>
</html>