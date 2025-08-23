<%@page import="Model.TipoMascota"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Tipos de Mascota</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>

  <div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2 class="text-primary">📋 Lista de Tipos de Mascota</h2>
      <a href="SvTipoMascotas?accion=crear" class="btn btn-success">➕ Crear nuevo registro</a>
    </div>

    <div class="table-responsive">
      <table class="table table-bordered table-hover">
        <thead class="thead-dark">
          <tr>
            <th>ID Tipo</th>
            <th>Nombre</th>
            <th>Observaciones</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
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
              <a href="SvTipoMascotas?accion=editar&id_tipo=<%= tm.getId_tipo() %>" class="btn btn-sm btn-warning">Editar</a>
              <a href="SvTipoMascotas?accion=eliminar&id=<%= tm.getId_tipo() %>" 
                 onclick="return confirm('¿Seguro que deseas eliminar este registro?');" 
                 class="btn btn-sm btn-danger ml-1">Eliminar</a>
            </td>
          </tr>
          <%
              }
            } else {
          %>
          <tr>
            <td colspan="4" class="text-center text-muted">No hay registros disponibles.</td>
          </tr>
          <%
            }
          %>
        </tbody>
      </table>
    </div>

    <div class="text-center mt-4">
      <a href="Index.jsp" class="btn btn-outline-secondary">⬅ Volver al inicio</a>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    
</body>
</html>
	