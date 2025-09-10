package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import com.sun.net.httpserver.Request;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

import Model.TipoMascota;
import Model.TipoMascotaDAO;

@WebServlet("/SvTipoMascotas")
public class SvTipoMascotas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	TipoMascotaDAO tmDao = new TipoMascotaDAO();

	public SvTipoMascotas() {
		super();
	}

		/*Servlet encargado de gestionar todas las operaciones relacionadas con la entidad TipoMascotas.
		 * 
		 * Este controlador centraliza las acciones CRUD y la generacion de documentos PDF.
		 * Recibe peticiones mediante los parametros enviados atravez de de las vistas .JSP.
		 * 
		 * doGet:
		 * -Accion-> listar: Trae el objeto lista que contiene los registros de una tabla y los manda al Listar.JSP.
		 * 
		 * -Accion-> crear: Redirige al formulario de creacion (Crear.jsp).
		 * 
		 * -Accion-> editar: Busca un registro por ID y redirige al formulario (Editar.JSP) para ser modificado.
		 * 
		 * -Accion-> eliminar: Elimina un registro segun el ID recibido y redirige al listado.
		 * 
		 * -Accion-> crearpdf: Genera un documento PDF con los registros de una tabla.
		 * 
		 * 
		 * - doPost():
		 *  -Accion-> create → recibe datos del formulario y guarda un nuevo registro en la base de datos.
		 *  
		 *  -Accion-> editar → recibe los datos actualizados de un registro y los guarda en la base de datos.
		 *
		 * Respuestas:
		 *  - Redirige o reenvía a las vistas JSP correspondientes.
		 *  
		 *  - Genera un archivo PDF que se envía directamente al navegador.
		 *
		 * En caso de errores (conexión, SQL o parámetros inválidos), se captura la excepción y se imprime en consola.
		 *  
		 *  */
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String accion = request.getParameter("accion");

		if ("listar".equals(accion)) {
			List<TipoMascota> lista = tmDao.listar();
			request.setAttribute("tipomascota", lista);
			request.getRequestDispatcher("View/Listar.jsp").forward(request, response);
		}
		else if ("crear".equals(accion)) {
            request.getRequestDispatcher("View/Crear.jsp").forward(request, response);
        }
		else if ("editar".equals(accion)) {
		    String id = request.getParameter("id_tipo");

		    if (id != null && !id.isEmpty()) {
		        TipoMascota tm = tmDao.buscarPorId(Integer.parseInt(id)); 
		        request.setAttribute("tipomascota", tm);
		        request.getRequestDispatcher("View/Editar.jsp").forward(request, response);
		    } else {
		        response.sendRedirect("SvTipoMascotas?accion=listar");
		    }
		}
		else if ("eliminar".equals(accion)) {
		    String id = request.getParameter("id");
		    System.out.println(id);
		    if (id != null && !id.isEmpty()) {
		        tmDao.delete(Integer.parseInt(id));
		    }
		    response.sendRedirect("SvTipoMascotas?accion=listar");
		}
		else if ("crearpdf".equals(accion)) {
			
			response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "inline; filename=tipos_mascotas.pdf");
			
	        TipoMascotaDAO tm = new TipoMascotaDAO();
	        
	        try {
	        	tm.generarpdf(response.getOutputStream());
	        	
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
		}
	 
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String accion = request.getParameter("accion");
		
		if (accion.equals("create")) {
            String nombre = request.getParameter("nombre");
            String observaciones = request.getParameter("observaciones");

            if (nombre != null && !nombre.isEmpty()) {
                TipoMascota tm = new TipoMascota();
                tm.setNombre(nombre);
                tm.setObservaciones(observaciones);

                tmDao.create(tm);
            } else {
                
            }

            
            response.sendRedirect("SvTipoMascotas?accion=listar");
        }
		
		
		else if ("editar".equals(accion)) {
	        
	        String id = request.getParameter("id_tipo");
	        String nombre = request.getParameter("nombre");
	        String observaciones = request.getParameter("observaciones");

	        if (id != null && !id.isEmpty() && nombre != null && !nombre.isEmpty()) {
	            TipoMascota tm = new TipoMascota();
	            tm.setId_tipo(Integer.parseInt(id));
	            tm.setNombre(nombre);
	            tm.setObservaciones(observaciones);

	            tmDao.uptade(tm); 
	        }

	        response.sendRedirect("SvTipoMascotas?accion=listar");
	    }
		
		

		
	}
	
	
	
	
	
}
