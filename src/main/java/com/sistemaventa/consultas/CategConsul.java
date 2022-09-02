package com.sistemaventa.consultas;

import com.sistemaventa.conexion.Conexion;
import com.sistemaventa.objects.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author elvis_agui
 */
public class CategConsul extends Consulta {

    private final List<Categoria> categorias = new ArrayList<>();

    public void insertCategoria(Categoria categoria) {
        consulta = "INSERT INTO categoria (nombre, descripcion) VALUES (?,?)";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, categoria.getNombre());
            query.setString(2, categoria.getDescripcion());
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria creada con Exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la Conexion a la Base de Datos o Categoria ya existente");
        } finally {
            cierre();
        }
    }

    public List<Categoria> getCategoriasDB() {
        this.categorias.clear();
        this.consulta = "SELECT * FROM categoria";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(this.consulta);
            result = query.executeQuery();
            while (result.next()) {
                this.categorias.add(new Categoria(result.getString("nombre"), result.getString("descripcion")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener las Categorias de la Base de Datos");
        } finally {
            cierre();
        }
        return categorias;
    }

    public void deletCategoria(String categoria) {
        this.consulta = "DELETE FROM categoria WHERE nombre=?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, categoria);
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria Elimina con exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al Categoria en la Base de Datos");
        } finally {
            cierre();
        }
    }
    
    public void updateCategoria(String nameActual, String nameCambio, String descr){
        consulta = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE nombre = ?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, nameCambio);
            query.setString(2, descr);
            query.setString(3, nameActual);
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria actulizada con exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la Base de Datos");
        } finally {
            cierre();
        }
    }
    
    public void deleteCategoriaProducto(String namepro, String nameCa){
        this.consulta = "DELETE FROM categoria_producto WHERE nombre_Producto=? AND nombre_categoria=?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, namepro);
            query.setString(2, nameCa);
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Categoria Elimina con exito");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar al Categoria en la Base de Datos");
        } finally {
            cierre();
        }
    }

    public String categoriaEditar(String nameCate, JTextField nombre, JTextArea descrp) {
        for (Categoria categoria : categorias) {
            if (nameCate.equals(categoria.getNombre())) {
                nombre.setText(categoria.getNombre());
                descrp.setText(categoria.getDescripcion());
                break;
            
            }
        }
        return nameCate;
    }

    public void enlistarCategorias(JComboBox comboCategorias, String inicial) {
        comboCategorias.removeAllItems();
        comboCategorias.addItem(inicial);
        categorias.forEach(categoria -> {
            comboCategorias.addItem(categoria.getNombre());
        });
    }
    
    public void enlistarCategorias(JComboBox comboCategorias) {
        comboCategorias.removeAllItems();
        categorias.forEach(categoria -> {
            comboCategorias.addItem(categoria.getNombre());
        });
    }
    
    public void enlistarCategorias(JComboBox comboCategorias, List<Categoria>  categ) {
        comboCategorias.removeAllItems();
        categ.forEach(categoria -> {
            comboCategorias.addItem(categoria.getNombre());
        });
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    public List<Categoria> getCategoriasProducto(String name){
        List<Categoria> categoriasP = new ArrayList<>();
        consulta = "SELECT nombre_categoria FROM categoria_producto WHERE nombre_Producto = ?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(this.consulta);
            query.setString(1, name);
            result = query.executeQuery();
            while (result.next()) {
                categoriasP.add(new Categoria(result.getString("nombre_categoria"), ""));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener las Categorias del producto");
        } finally {
            cierre();
        }
        return  categoriasP;
    }

}
