package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public static Connection conectarBD() {
		Connection connection = null;
		try {
			// Cargar el driver JDBC
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establecer la conexión
		/*connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bd_tienda_mascotas?useSSL=false&serverTimezone=UTC","root","2556229");*/
			
			connection = DriverManager.getConnection(
				    "jdbc:mysql://localhost:3306/bd_tienda_mascotas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
				    "root",
				    "2556229");
				


			
		} catch (ClassNotFoundException e) {
			System.out.println("No se encontró el driver JDBC: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		return connection;
	}
}
