package com.sistemaventa.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elvis_agui
 */
public class Conexion {

    public static String URL = "jdbc:mysql://localhost:3306/Tienda_Mariposa";
    public static String USER = "elvis-admin";
    public static String PASS = "4056ELVIS";
    public static Connection conexion = null;

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en acceder a la Base de datos");
        } catch (ClassNotFoundException ex) {
            System.err.println("error en el driver de conexion");   
        }
        return conexion;

    }

    public static void getCloseConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
            }

        }
    }
}
