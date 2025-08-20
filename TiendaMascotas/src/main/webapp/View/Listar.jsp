<%@page import="Model.TipoMascota"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LISTAR</title>
</head>
<body>
    <h1>Lista de Tipos de Mascota</h1>
    
    <a href="SvTipoMascotas?accion=crear">Crear nuevo registro</a>
    
    <table border="1">
        <tr>
            <th>ID Tipo</th>
            <th>Nombre</th>
            <th>Observaciones</th>
            <th>Acciones</th>
        </tr>
        <%
            List<TipoMascota> tipomascota = (List<TipoMascota>) request.getAttribute("tipomascota");
            if (tipomascota != null) {
                for (TipoMascota tm : tipomascota) {
        %>
        <tr>
            <td><%= tm.getId_tipo() %></td>
            <td><%= tm.getNombre() %></td>
            <td><%= tm.getObservaciones() %></td>
            <td>
                <a href="SvTipoMascotas?accion=editar&id_tipo=<%= tm.getId_tipo() %>">Editar</a>
                <a href="SvTipoMascotas?accion=eliminar&id=<%= tm.getId_tipo() %>" 
   				onclick="return confirm('¿Seguro que deseas eliminar este registro?');">
   				Eliminar
					</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
    
    <p><a href="Index.jsp">Volver al inicio</a></p>
    
</body>
</html>
	