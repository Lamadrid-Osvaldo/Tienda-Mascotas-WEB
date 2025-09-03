<%@page import="Controller.Conexion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

  
<title>Inicio</title>
</head>
<body >

	<div class="container py-5">
    <div class="text-center mb-4">
      <h1 class="display-4">🐾 Tipo de Mascotas</h1>
      <p class="lead">Gestiona los tipos disponibles en el sistema</p>
    </div>

    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="list-group">
          <a href="SvTipoMascotas?accion=listar" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            📋 Ver Lista de Tipos
            <span class="badge badge-primary badge-pill">Ir</span>
          </a>
          <a href="SvTestConexion" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            🔍 Probar conexión a la base de datos
            <span class="badge badge-success badge-pill">Test</span>
          </a>
          <a href="SvTipoMascotas?accion=crear" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            ➕ Crear nuevo registro
            <span class="badge badge-warning badge-pill">Nuevo</span>
          </a> 
        </div>
      </div>
    </div>
  </div>
	
	
	
	

	
	
</body>
</html>
