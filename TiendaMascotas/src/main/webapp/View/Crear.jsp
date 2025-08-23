<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agregar Tipo de Mascota</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>

  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card shadow">
          <div class="card-header bg-info text-white text-center">
            <h4>Agregar Tipo de Mascota</h4>
          </div>
          <div class="card-body">
            <form action="SvTipoMascotas" method="POST">
              <input type="hidden" name="accion" value="create">

              <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" maxlength="35" class="form-control" required>
              </div>

              <div class="form-group">
                <label for="observaciones">Observaciones</label>
                <input type="text" name="observaciones" id="observaciones" class="form-control">
              </div>

              <button type="submit" class="btn btn-success btn-block">Guardar Tipo de Mascota</button>
            </form>

            <div class="text-center mt-3">
              <a href="Index.jsp" class="btn btn-outline-secondary">⬅ Volver al inicio</a>
              
               <div class="text-center mt-3">
              <a href="SvTipoMascotas?accion=listar" class="btn btn-outline-secondary">⬅ Volver a la lista</a>
              
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
   </div>

</body>
</html>