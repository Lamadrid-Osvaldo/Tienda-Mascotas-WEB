package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Controller.Conexion;

public class TipoMascotaDAO {

	public List<TipoMascota> listar() {
		List<TipoMascota> lista = new ArrayList<>();
		String sql = "SELECT * FROM tbltipo_mascotas";

		try (Connection con = Conexion.conectarBD();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

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
	
	public void create(TipoMascota m) {
		
		String sql = "INSERT INTO tbltipo_mascotas (nombre,observaciones) VALUES (?, ?);";
		
		try (Connection con = Conexion.conectarBD();
				PreparedStatement ps = con.prepareStatement(sql);
				) {
			
			
			ps.setString(1, m.getNombre());
			ps.setString(2, m.getObservaciones());
			
			
			ps.executeUpdate();
			
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	
	public void uptade(TipoMascota m) {
		
		String sql = "UPDATE tbltipo_mascotas SET nombre = ?, observaciones = ? WHERE (id_tipo = ?);";
		
		try (Connection con = Conexion.conectarBD();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setString(1, m.getNombre());
		        ps.setString(2, m.getObservaciones());
		        ps.setInt(3, m.getId_tipo()); 

		        ps.executeUpdate();
		        System.out.println("Registro actualizado con éxito");

		    } catch (SQLException e) {
		        System.out.println("Error al actualizar: " + e.getMessage());
		    }
		
	}
	
	public TipoMascota buscarPorId(int id) {
	    TipoMascota tm = null;
	    String sql = "SELECT * FROM tbltipo_mascotas WHERE id_tipo = ?";
	    try (Connection con = Conexion.conectarBD();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            tm = new TipoMascota();
	            tm.setId_tipo(rs.getInt("id_tipo"));
	            tm.setNombre(rs.getString("nombre"));
	            tm.setObservaciones(rs.getString("observaciones"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return tm;
	}

	public void delete(int id) {
	    String sql = "DELETE FROM tbltipo_mascotas WHERE id_tipo = ?";
	    try (Connection con = Conexion.conectarBD();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, id);
	        ps.executeUpdate();
	        System.out.println("Registro eliminado con éxito");

	    } catch (SQLException e) {
	        System.out.println("Error al eliminar: " + e.getMessage());
	    }
	}


	
	
}
