<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado de Conexión</title>
    
</head>
<body>
    <h1> Test de Conexión a la Base de Datos</h1>

    <%
        String estado = (String) request.getAttribute("estadoConexion");
        if ("conectado".equals(estado)) {
    %>
        <div class="mensaje ok"> Conexión exitosa a la base de datos</div>
    <%
        } else {
    %>
        <div class="mensaje fail"> No se pudo conectar a la base de datos</div>
    <%
        }
    %>

    <p><a href="Index.jsp">Volver al inicio</a></p>

</body>
</html>
