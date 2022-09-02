package com.sistemaventa.consultas;

import com.sistemaventa.Encript;
import com.sistemaventa.conexion.Conexion;
import com.sistemaventa.objects.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author elvis_agui
 */
public class UserConsul extends Consulta {

    private List<Usuario> usuarios = new ArrayList<>();
    private Usuario usuario;
    private Encript encript = new Encript();

    /**
     * Funcion encargado verificar si los datos son correcrtos del usuario en el
     * login
     *
     * @param nombre
     * @param pass
     * @return
     */
    public boolean usuarioCorrecto(String nombre, String pass) {
        this.consulta = "SELECT * FROM usuario WHERE nombre = ? AND password = ?";
        boolean exist = false;
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, nombre);
            query.setString(2, pass);
            result = query.executeQuery();
            while (result.next()) {
                if (result.getInt("rol") != 3) {
                    this.usuario = new Usuario(result.getString("nombre"), result.getString("password"), result.getInt("rol"));
                    exist = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion a la Base de Datos");
        } finally {
            cierre();
        }
        return exist;
    }

    /**
     * Actuliza los datos del perfil del usuario
     *
     * @param newNombre
     * @param newPass
     */
    public void updatePerfil(String newNombre, String newPass) {
        try {
            if (!this.usuario.getNombre().equalsIgnoreCase(newNombre) && !this.usuario.getPassword().equalsIgnoreCase(newPass)) {
                this.consulta = "UPDATE usuario SET password = ? , nombre = ? WHERE nombre = ?";
                this.updateTwo(newNombre, newPass);
                JOptionPane.showMessageDialog(null, "Usuario y Contraseña Actulizados Exitosamente");

            } else if (!this.usuario.getNombre().equalsIgnoreCase(newNombre)) {
                this.consulta = "UPDATE usuario SET nombre = ? WHERE nombre = ?";
                JOptionPane.showMessageDialog(null, "Nombre de Usuario Actulizado Exitosamente");

                this.updateName(newNombre);
            } else if (!this.usuario.getPassword().equalsIgnoreCase(newPass)) {
                this.consulta = "UPDATE usuario SET password = ? WHERE nombre = ?";
                JOptionPane.showMessageDialog(null, "Contraseña Actulizada Exitosamente");

                this.updatePass(newPass);
            } else {
                JOptionPane.showMessageDialog(null, "Los datos no han sido Modificados :)");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la Conexion a Base de datos");
        } finally {
            cierre();
        }
    }

    public void newEmpleado(String nombre, String pass, int rol) {
        this.consulta = "INSERT INTO usuario (nombre, password, rol) VALUES (?,?,?)";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, nombre);
            query.setString(2, this.encript.ecnode(pass));
            query.setInt(3, rol);
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario Creado con Exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la Conexion a la Base de Datos o Usuario ya existente");
        } finally {
            cierre();
        }
    }

    /**
     * enlista a todos los usuarios existentes descartando al usuario Actual
     * @return
     */
    public List<Usuario> listUsuarios() {
        this.usuarios.clear();
        this.consulta = "SELECT * FROM usuario";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(this.consulta);
            result = query.executeQuery();
            while (result.next()) {
                if (!result.getString("nombre").equals(this.usuario.getNombre()) && result.getInt("rol") != 3) {
                    usuarios.add(new Usuario(result.getString("nombre"), "", result.getInt("rol")));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener a los Usuarios de la Base de Datos");
        } finally {
            cierre();
        }

        return usuarios;
    }

    public void deletUser(String nameUser) {
        this.consulta = "UPDATE  usuario set nombre = ?, rol = ? WHERE nombre=?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1,this.nameDeprecado(nameUser) );
            query.setInt(2, 3);
            query.setString(3, nameUser);
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario Actulizado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al Actualizar al Usuario en la Base de Datos");
        } finally {
            cierre();
        }
    }
    
    
    public String nameDeprecado(String name){
        return name + Math.random();
    }

    public void updateRol(String nameUser) {
        consulta = "UPDATE usuario SET rol = ?  WHERE nombre = ?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setInt(1, this.cambioRol(nameUser));
            query.setString(2, nameUser);
            query.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la Base de Datos");
        } finally {
            cierre();
        }

    }

    private int cambioRol(String nameUser) {
        int rol = 2;
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getNombre().equals(nameUser)) {
                rol = usuario1.getRol();
                break;
            }
        }
        if (rol == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    private void updateTwo(String newNombre, String newPass) throws SQLException {
        conexion = Conexion.getConexion();
        query = conexion.prepareStatement(consulta);
        query.setString(1, newPass);
        query.setString(2, newNombre);
        query.setString(3, this.usuario.getNombre());
        query.executeUpdate();
    }

    private void updateName(String newNombre) throws SQLException {
        conexion = Conexion.getConexion();
        query = conexion.prepareStatement(consulta);
        query.setString(1, newNombre);
        query.setString(2, this.usuario.getNombre());
        query.executeUpdate();
    }

    private void updatePass(String newPass) throws SQLException {
        conexion = Conexion.getConexion();
        query = conexion.prepareStatement(consulta);
        query.setString(1, newPass);
        query.setString(2, this.usuario.getNombre());
        query.executeUpdate();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Encript getEncript() {
        return encript;
    }

    public void setEncript(Encript encript) {
        this.encript = encript;
    }

}
