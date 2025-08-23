<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resultado de Conexión</title>
    
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    
</head>
<body>


    <div class="container py-5">
        <div class="text-center mb-4">
            <h1 class="display-4">🔌 Test de Conexión a la Base de Datos</h1>
            <p class="lead text-muted">Verifica si la conexión fue exitosa</p>
        </div>

        <%
            String estado = (String) request.getAttribute("estadoConexion");
            if ("conectado".equals(estado)) {
        %>
            <div class="alert alert-success text-center" role="alert">
                ✅ Conexión exitosa a la base de datos
            </div>
        <%
            } else {
        %>
            <div class="alert alert-danger text-center" role="alert">
                ❌ No se pudo conectar a la base de datos
            </div>
        <%
            }
        %>

        <div class="text-center mt-4">
            <a href="Index.jsp" class="btn btn-outline-primary btn-lg">
                ⬅️ Volver al inicio
            </a>
        </div>
    </div>

    

</body>
</html>
