package com.sistemaventa.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elvis_agui
 */
public class Consulta {

    protected PreparedStatement query = null;
    protected ResultSet result = null;
    protected Connection conexion = null;
    protected String consulta = "";

    /**
     * funcion que cierra las conexiones de la base de datos, conexion, result,
     * query
     */
    protected void cierre() {
        try {
            if (conexion != null) {
                conexion.close();
            }
            if (result != null) {
                result.close();
            }
            if (query != null) {
                query.close();
            }
        } catch (SQLException e) {
            this.massageError("error en cierre de conexion, result, query",e.getMessage() );
        }

    }

    private void massageError(String mensaje, String error) {
        System.err.println(mensaje);
        System.err.println(error);
        JOptionPane.showMessageDialog(null, "Proceso con posibles fallas al completarse :)");

    }
}
