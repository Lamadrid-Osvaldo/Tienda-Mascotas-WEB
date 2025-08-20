package Model;

import javax.swing.JOptionPane;

import Controller.Conexion;

public class test {

	public static void main(String[] args) {
		Conexion test = new Conexion();
		if (test.conectarBD() != null) {
			JOptionPane.showMessageDialog(null, "Conectado a base datos");

		} else {
			JOptionPane.showConfirmDialog(null, "no conectado a la BD");
		}

	}

}
