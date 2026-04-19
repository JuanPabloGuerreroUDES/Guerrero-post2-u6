<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">

    <title>
        <c:choose>
            <c:when test="${empty producto}">Nuevo Producto</c:when>
            <c:otherwise>Editar Producto</c:otherwise>
        </c:choose>
    </title>

    <link rel="stylesheet" href="<c:url value='/css/estilos.css'/>">
</head>
<body>

<h1>
    <c:choose>
        <c:when test="${empty producto}">Registrar Producto</c:when>
        <c:otherwise>Editar Producto</c:otherwise>
    </c:choose>
</h1>

<form method="post" action="<c:url value='/productos'/>">

    <!-- Acción -->
    <c:choose>
        <c:when test="${empty producto}">
            <input type="hidden" name="accion" value="guardar">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="accion" value="actualizar">
            <input type="hidden" name="id" value="${producto.id}">
        </c:otherwise>
    </c:choose>

    <!-- Nombre -->
    <label>
        Nombre:
        <input type="text" name="nombre" required
               value="<c:out value='${producto.nombre}'/>">
    </label>

    <!-- Categoría -->
    <label>
        Categoría:
        <input type="text" name="categoria"
               value="<c:out value='${producto.categoria}'/>">
    </label>

    <!-- Precio -->
    <label>
        Precio:
        <input type="number" name="precio" step="0.01" min="0" required
               value="${producto.precio}">
    </label>

    <!-- Stock -->
    <label>
        Stock:
        <input type="number" name="stock" min="0" required
               value="${producto.stock}">
    </label>

    <!-- Botones -->
    <button type="submit">
        <c:choose>
            <c:when test="${empty producto}">Guardar</c:when>
            <c:otherwise>Actualizar</c:otherwise>
        </c:choose>
    </button>

    <a href="<c:url value='/productos'/>">Cancelar</a>

</form>

</body>
</html>