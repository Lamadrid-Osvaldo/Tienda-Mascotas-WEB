package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	/* Clase encargada de establecer conexion a una base de datos usando un driver de conexion (mysql-connector-j-9.4.0.jar).
	 * 
	 * 
	 * Establecemos las propiedades de conexion como el URL, host,  puerto, nombre de la base de datos,  usuario y contraseña. 
	 * con estas propiedades intentamos conectar a la base de datos, en caso que sea exitosa el retorna un objeto llamado "connection".
	 * 
	 * En caso que no sea posible la conexion indicara en consola cual es el posible error, pudiendo ser el driver de conexion o 
	 * que no se haya encontrado la base de datos.
	 *  */

	public static Connection conectarBD() {
		Connection connection = null;
		try {
			//Se cargar el driver JDBC
			Class.forName("com.mysql.cj.jdbc.Driver");

			
		/*connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bd_tienda_mascotas?useSSL=false&serverTimezone=UTC","root","2556229");*/
			
			
			connection = DriverManager.getConnection(
				    "jdbc:mysql://localhost:3306/bd_tienda_mascotas?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
				    "root",
				    "2556229");
				


			
		}
		//En caso de que no logre la conexion mostrara en consola el debido error.
		catch (ClassNotFoundException e) {
			System.out.println("No se encontró el driver JDBC: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		
		//Si se logra hacer conexion a la base de datos retornara el objeto connection
		return connection;
	}
}
