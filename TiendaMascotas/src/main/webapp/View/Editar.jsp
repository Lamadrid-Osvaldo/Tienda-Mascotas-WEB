<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Registro</title>
</head>
<body>

	<form action="SvTipoMascotas" method="POST">
	
    <input type="hidden" name="accion" value="editar">
    
    <input type="hidden" name="id_tipo" value="${tipomascota.id_tipo}">

    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" value="${tipomascota.nombre}" maxlength="35"><br>

    <label for="observaciones">Observaciones:</label>
    <input type="text" name="observaciones" id="observaciones" value="${tipomascota.observaciones}"><br>

    <button type="submit">Actualizar</button>
</form>
	

</body>
</html>