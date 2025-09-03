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
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Controller.Conexion;

public class TipoMascotaDAO {
	
	
	//Metodo de lista 
	public List<TipoMascota> listar() {
		List<TipoMascota> lista = new ArrayList<>();
		//Consulta
		String sql = "SELECT * FROM tbltipo_mascotas";
		
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

		return lista;
	}
	
	//Metodo para crear registro
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
			enviarcorreo("osvi.lamadrid@gmail.com", "Nueva mascota registrada", "Se registró la mascota: " + m.getNombre() + "\nObservaciones: " + m.getObservaciones());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	//Metodo para actualizar un registro
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
			System.out.println("Registro actualizado con éxito");
			
			//parametros para enviar un correo notificando la accion
			enviarcorreo("osvi.lamadrid@gmail.com", "Actualizacion de registro", "Se actualizo el tipo de mascota mascota: " + m.getId_tipo() +
					"\nNombre: " + m.getNombre() + "\nObservaciones: "+m.getObservaciones());

		} catch (SQLException e) {
			System.out.println("Error al actualizar: " + e.getMessage());
		}

	}
	
	//Metodo para buscar un registro en especifico
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
	public void delete(int id) {
		//Consulta
		String sql = "DELETE FROM tbltipo_mascotas WHERE id_tipo = ?";
		try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
			
			//Parametro para realizar la consulta
			ps.setInt(1, id);
			
			//Ejecuta la trx
			ps.executeUpdate();
			System.out.println("Registro eliminado con éxito");
			
			//parametros para enviar un correo notificando la accion
			enviarcorreo("osvi.lamadrid@gmail.com", "Eliminacion de registro", "Se elimino el tipo de mascota: "+"\nId " +id );

		} catch (SQLException e) {
			System.out.println("Error al eliminar: " + e.getMessage());
		}
	}

	
	//Metodo para generar un pdf
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
	
	
	public void enviarcorreo(String correoReceptor, String asunto, String mensaje) {
		
		String correoRemitente = "gernanshmith@gmail.com";
    	String passwordRemitente = "oaqq kfwj mgik fpws";
		
    	
    	try {
			
    		
        	Properties props = new Properties();
        	
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
	
	
	
}

