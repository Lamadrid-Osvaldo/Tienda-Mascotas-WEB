<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agregar Tipo de Mascota</title>
</head>
<body>

	<form action="SvTipoMascotas" method="POST">
	
	  <input type="hidden" name="accion" value="create">
	
	  <label for="nombre">Nombre:</label>
	  <input type="text" name="nombre" id="nombre" maxlength="35"><br>
	
	  <label for="observaciones">Observaciones:</label>
	  <input name="observaciones" id="observaciones"><br>
	
	  <button type="submit"  >Guardar Tipo de Mascota</button>
	</form>
	
	<h2></h2>

</body>
</html>