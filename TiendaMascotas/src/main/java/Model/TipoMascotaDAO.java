package Model;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Controller.Conexion;

public class TipoMascotaDAO {
	
	/*Clase que contiene una serie de metodos CRUD, SIM , Generador de PDF  */
	
	
	
	/*Metodo de lista  
	 * 
	 * Metodo encargado de consultar todos los registros de la tabla tbltipo_mascotas.
	 * 
	 * Si se logra realizar la consulta,  llenamos una lista con los registros y se retorna.
	 *  
	 * En caso que no sea posible la consulta se muestra en consola el posible error.
	 *   
	 * */
	public List<TipoMascota> listar() {
		//Se crea una lista de objetos de TipoMascotas
		List<TipoMascota> lista = new ArrayList<>();
		//Consulta 
		String sql = "SELECT * FROM tbltipo_mascotas";
		//Intentamos hacer la consulta en la base de datos
		try (Connection con = Conexion.conectarBD();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			
			//llena la lista
			while (rs.next()) {
				TipoMascota tm = new TipoMascota();
				tm.setId_tipo(rs.getInt("id_tipo"));
				tm.setNombre(rs.getString("nombre"));
				tm.setObservaciones(rs.getString("observaciones"));
				lista.add(tm);

			}

		} catch (SQLException e) {
			System.err.println("Error al listar tipos de mascota: " + e.getMessage());
		}
		//Retorna la lista.
		return lista;
	}
	
	
		//Metodo para crear registro
		
		/*Metodo encargado  de insertar registros en la tabla tbltipo_mascotas
		 * 
		 *Este metodo recibe unos parametros (nombre y observaciones) a travez de el objeto "m" TipoMascota
		 *y se guardan en la base de datos mediante un INSERT INTO.
		 *
		 * Tras completar la operacion, envia un correo electronico notificando un nuevo registro.
		 * 
		 * En caso de que no pueda llevar a cabo la insercion, mostrara en consola el posible error. 
		 * 
		 *   */
	public void create(TipoMascota m) {
		//Consulta
		String sql = "INSERT INTO tbltipo_mascotas (nombre,observaciones) VALUES (?, ?);";

		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql);) {
			
			//Datos a insertar
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getObservaciones());
			
			//Realiza la trx
			ps.executeUpdate();
			
			//parametros para enviar un correo notificando la accion
			enviarcorreo("Nuevo tipo de mascota registrada", "Se registró el tipo de mascota: " + m.getNombre() + "\nObservaciones: " + m.getObservaciones());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

		//Metodo para actualizar un registro
		
		/*Metodo encargado de actualizar un registro de la tabla tbltipo_mascotas.
		 * 
		 * Este metodo recibe unos parametros (id_tipo ,nombre y observaciones) a travez de el objeto "m" TipoMascota
		 * y actualizando el registro identificado por su ID.
		 * 
		 * Tras completar la operacion, envia un correo electronico notificando la actualizacion de un registro.
		 * 
		 * En caso de que no se pueda llevar a cabo la actualizacion, mostrara en consola el posible error.
		 *  
		 *  */
	
	public void uptade(TipoMascota m) {
		//Consulta
		String sql = "UPDATE tbltipo_mascotas SET nombre = ?, observaciones = ? WHERE (id_tipo = ?);";

		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			
			//Datos actualizados
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getObservaciones());
			ps.setInt(3, m.getId_tipo());
			
			//Realiza la trx
			ps.executeUpdate();
			
			
			//parametros para enviar un correo notificando la accion
			enviarcorreo("Actualizacion de registro", "Se actualizo el tipo de mascota: \nID: " + m.getId_tipo() +
					"\nNombre: " + m.getNombre() + "\nObservaciones: "+m.getObservaciones());

		} catch (SQLException e) {
			System.out.println("Error al actualizar: " + e.getMessage());
		}

	}
	
		//Metodo para buscar un registro en especifico
		
		/* Metodo encargado de consultar un registro de la tabla tbltipo_mascotas a partir de su ID.
		 *
		 * Si encuentra un registro con el ID especificado, crea un objeto TipoMascota, 
		 * asigna los valores obtenidos de la consulta (id, nombre, observaciones) y 
		 * lo retorna.
		 *
		 * En caso de no encontrar el registro retornara nulo (NULL).
		 *
		 * En caso de error durante la consulta, se muestra el mensaje de la excepción.
		 * 
		 *  */
	
	public TipoMascota buscarPorId(int id) {
		TipoMascota tm = null;
		
		//Consulta
		String sql = "SELECT * FROM tbltipo_mascotas WHERE id_tipo = ?";
		
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			
			ps.setInt(1, id);
			
			//Ejecuta la trx
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				tm = new TipoMascota();
				
				//Se traen los datos de la consulta y se llenan 
				tm.setId_tipo(rs.getInt("id_tipo"));
				tm.setNombre(rs.getString("nombre"));
				tm.setObservaciones(rs.getString("observaciones"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tm;
	}

		//Metodo para eliminar un registro 
		
		/* Metodo encargado de eliminar un regristo de la tabla tbltipo_mascotas a partir de su ID.
		 * 
		 * El metodo recibe un parametro "id" y ejecuta la sentencia DELETE , elimninando el registro donde el id sea el proporcionado 	
		 * 
		 * Tras completar la operacion, envia un correo electronico notificando la eliminacion de un registro.
		 * 
		 * En caso de que no se pueda llevar a cabo la eliminacion, mostrara en consola el posible error.
		 * 
		 */
	
	
	public void delete(int id) {
		//Consulta
		String sql = "DELETE FROM tbltipo_mascotas WHERE id_tipo = ?";
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			
			//Parametro para realizar la consulta
			ps.setInt(1, id);
			
			//Ejecuta la trx
			ps.executeUpdate();
			
			
			//parametros para enviar un correo notificando la accion
			enviarcorreo("Eliminacion de registro", "Se elimino el tipo de mascota: "+"\nId " +id );

		} catch (SQLException e) {
			System.out.println("Error al eliminar: " + e.getMessage());
		}
	}

	
		//Metodo para generar un pdf
	
		/* Metodo encargado de generar un documento PDF de los registros de la tabla tbltipo_mascotas.
		 * 
		 * Se consulta en la base de datos los registros de la tabla tbltipo_mascotas.
		 * 
		 * Se crea y abre un documento con los registros previamente consultados.
		 * 
		 * Asignandole al documento un nombre dinamico (dia/mes/año/hora/minuto/segundo actual).
		 * 
		 * Se guarda el documento generado en la ruta configurada.
		 * 
		 * Se envia el documento generado al navegador a travez del "OutputStream" recibido como parametro.
		 * 
		 * Si ocurre un error durante la creacion del documento, se mostrara en consola el posible error.
		 * */
	
	
	public void generarpdf(OutputStream out) throws DocumentException, SQLException {
		//consulta
		String sql = "SELECT id_tipo, nombre, observaciones FROM tbltipo_mascotas";
		String mesDC = "";
		
		Calendar cal = Calendar.getInstance();
		//Se calcula el mes 
		if ((cal.get(Calendar.MONTH) + 1) < 10) {
			mesDC = "0" + (Integer.valueOf(cal.get(Calendar.MONTH)) + 1);
		} else {
			mesDC = String.valueOf(cal.get(Calendar.MONTH)) + 1;
		}
		
		//Nombre dinamico para el pdf con fecha y hora
		String Filename = String.valueOf(cal.get(Calendar.DATE)) + String.valueOf(mesDC)
				+ String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.HOUR))
				+ String.valueOf(cal.get(Calendar.MINUTE)) + String.valueOf(cal.get(Calendar.SECOND));

		// RUTA
		String rutaCarpeta = "C:\\Users\\APRENDIZ\\Documents\\pruebapdf\\";
		String nombreArchivo = Filename + ".pdf";
		
		//Se intenta la consulta
		try (Connection con = Conexion.conectarBD();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			
			//se crea un buffer
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			Document doc = new Document();
			//Se conecta el documento con el buffer
			PdfWriter.getInstance(doc, baos);
			
			//se abre y llena el documento PDF
			doc.open();

			doc.add(new Paragraph("Lista de tipos de mascotas"));
			doc.add(new Paragraph(" "));

			while (rs.next()) {
				String linea = rs.getInt("id_tipo") + " | " + rs.getString("nombre") + " | "
						+ rs.getString("observaciones");
				doc.add(new Paragraph(linea));
			}

			doc.close();
			
			//Se guarda el documento PDF en la ruta 
			try (FileOutputStream fos = new FileOutputStream(rutaCarpeta + nombreArchivo)) {
				baos.writeTo(fos);
			}

			System.out.println("se creo el pdf");
			
			//Se envia al navegador
			baos.writeTo(out);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
		//Metodo SIM
	
		/*Metodo encargado de enviar un correo electronico notificando un cambio en la BD
		 * 
		 * Recibe como parametros (asunto y mensaje) 
		 * 
		 * Intenta enviar un correo desde la cuenta de remitente ya configurada y autentificada
		 * hacia el receptor ya definido.
		 * 
		 * Si el proceso se realiza correctamente se enviara el correo.
		 * 
		 * En caso que no sea posible el envio mostrara el error en consola
		 *  
		 *  */
		
	public void enviarcorreo( String asunto, String mensaje) {
		
		//Datos de configuracion para el envio de correo
		String correoRemitente = "gernanshmith@gmail.com";
    	String passwordRemitente = "oaqq kfwj mgik fpws";
    	String correoReceptor = "osvi.lamadrid@gmail.com";
    	
    	try {
			
    		
        	Properties props = new Properties();
        	
        	//Propiedades de configuracion smtp
        	props.put("mail.smtp.auth", "true");
        	props.put("mail.smtp.starttls.enable", "true"); 
        	props.put("mail.smtp.host", "smtp.gmail.com"); 
        	props.put("mail.smtp.port", "587");
        
        	
        	Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correoRemitente, passwordRemitente);
                        }
                    });
        	
        	MimeMessage message = new MimeMessage(session);
        	
        	message.setFrom(new InternetAddress(correoRemitente));
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
        	message.setSubject(asunto);
        	message.setText(mensaje);
        	
        	Transport.send(message);
        	
        	
    	}
    	
	catch (MessagingException e) {
		e.printStackTrace();
	}
    	
    	
    	
    	
    	
	}
	
	/*public void enviarcorreoPDF( String asunto, String mensaje) {
		
		//Datos de configuracion para el envio de correo
		String correoRemitente = "gernanshmith@gmail.com";
    	String passwordRemitente = "oaqq kfwj mgik fpws";
    	String correoReceptor = "osvi.lamadrid@gmail.com";
    	
    	try {
			
    		
        	Properties props = new Properties();
        	
        	//Propiedades de configuracion smtp
        	props.put("mail.smtp.auth", "true");
        	props.put("mail.smtp.starttls.enable", "true"); 
        	props.put("mail.smtp.host", "smtp.gmail.com"); 
        	props.put("mail.smtp.port", "587");
        
        	
        	Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correoRemitente, passwordRemitente);
                        }
                    });
        	
        	MimeMessage message = new MimeMessage(session);
        	
        	message.setFrom(new InternetAddress(correoRemitente));
        	message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
        	message.setSubject(asunto);
        	
        	MimeBodyPart texto = new MimeBodyPart();
        	texto.setText();

        	// Crear un adjunto
        	MimeBodyPart adjunto = new MimeBodyPart();
        	adjunto.attachFile("C:/reportes/reporte.pdf");

        	// Agrupar las partes en un Multipart
        	Multipart multipart = new MimeMultipart();
        	multipart.addBodyPart(texto);
        	multipart.addBodyPart(adjunto);
        	
        	
        	
        	
        	Transport.send(message);
        	
        	
    	}
    	
	catch (MessagingException e) {
		e.printStackTrace();
	}
    	
    	
    	
    	
    	
	}*/
	
	
	
}

