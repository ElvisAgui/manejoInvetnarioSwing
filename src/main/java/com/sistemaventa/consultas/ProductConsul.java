package com.sistemaventa.consultas;

import com.sistemaventa.conexion.Conexion;
import com.sistemaventa.objects.Producto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author elvis_agui
 */
public class ProductConsul extends Consulta {

    public void insertProducto(Producto producto, boolean insetCategoriaProducto) {
        consulta = "INSERT INTO producto (nombre, precio, descripcion, cantidad_existe) VALUES (?,?,?,?)";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, producto.getNombre());
            query.setDouble(2, producto.getPrecio());
            query.setString(3, producto.getDescripcion());
            query.setInt(4, producto.getCantidad());
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto creado con Exito");
            if (insetCategoriaProducto) {
                this.insertProductoCategoria(producto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la Conexion a la Base de Datos o Producto ya existente");
        } finally {
            cierre();
        }
    }

    private void insertProductoCategoria(Producto producto) {
        consulta = "INSERT INTO categoria_producto (nombre_Producto, nombre_categoria) VALUES (?,?)";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, producto.getNombre());
            query.setString(2, producto.getCategoria());
            query.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Producto guardado sin categoria");
        } finally {
            cierre();
        }

    }

    public List<Producto> listaProductos(boolean ascendente, boolean isCantidad) {
        List<Producto> productos = new ArrayList<>();
        if (ascendente) {
            if (isCantidad) {
                consulta = "SELECT * FROM producto ORDER BY cantidad_existe";
            } else {
                consulta = "SELECT * FROM producto ORDER BY precio";
            }
        } else {
            if (isCantidad) {
                consulta = "SELECT * FROM producto ORDER BY cantidad_existe DESC";
            } else {
                consulta = "SELECT * FROM producto ORDER BY precio DESC";
            }
        }
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            result = query.executeQuery();
            while (result.next()) {
                productos.add(new Producto(result.getString("nombre"), result.getInt("cantidad_existe"), result.getDouble("precio"), result.getString("descripcion")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener los Productos de la Base de Datos");
        } finally {
            cierre();
        }

        return productos;
    }

    public List<Producto> listaProductos(String categoria, boolean ascendente, boolean isCantidad) {
        List<Producto> productos = new ArrayList<>();
        if (ascendente) {
            if (isCantidad) {
                consulta = "SELECT a.nombre, a.precio, a.descripcion, a.cantidad_existe FROM producto a JOIN categoria_producto b ON (nombre = nombre_Producto) WHERE b.nombre_categoria = ? ORDER BY cantidad_existe";
            } else {
                consulta = "SELECT a.nombre, a.precio, a.descripcion, a.cantidad_existe FROM producto a JOIN categoria_producto b ON (nombre = nombre_Producto) WHERE b.nombre_categoria = ? ORDER BY precio";
            }
        } else {
            if (isCantidad) {
                consulta = "SELECT a.nombre, a.precio, a.descripcion, a.cantidad_existe FROM producto a JOIN categoria_producto b ON (nombre = nombre_Producto) WHERE b.nombre_categoria = ? ORDER BY cantidad_existe DESC ";
            } else {
                consulta = "SELECT a.nombre, a.precio, a.descripcion, a.cantidad_existe FROM producto a JOIN categoria_producto b ON (nombre = nombre_Producto) WHERE b.nombre_categoria = ? ORDER BY precio DESC ";
            }
        }
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1, categoria);
            result = query.executeQuery();
            while (result.next()) {
                productos.add(new Producto(result.getString("nombre"), result.getInt("cantidad_existe"), result.getDouble("precio"), result.getString("descripcion")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener los Productos de la Base de Datos");
        } finally {
            cierre();
        }

        return productos;
    }

    public List<Producto> listaProductos(String nameSearch) {
        List<Producto> productos = new ArrayList<>();
        consulta = "SELECT * FROM producto WHERE nombre LIKE '%" + nameSearch + "%'";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            result = query.executeQuery();
            while (result.next()) {
                productos.add(new Producto(result.getString("nombre"), result.getInt("cantidad_existe"), result.getDouble("precio"), result.getString("descripcion")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener los Productos de la Base de Datos");
        } finally {
            cierre();
        }

        return productos;
    }

    public List<Producto> productosAgotados(int coeficiente) {
        List<Producto> productos = new ArrayList<>();
        consulta = "SELECT nombre, precio, cantidad_existe FROM producto WHERE cantidad_existe <= ?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(this.consulta);
            query.setInt(1, coeficiente);
            result = query.executeQuery();
            while (result.next()) {
                productos.add(new Producto(result.getString("nombre"), result.getInt("cantidad_existe"), result.getDouble("precio")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Obtener los Productos de la Base de Datos");
        } finally {
            cierre();
        }
        return productos;
    }

    public Producto productoEdit(String name, CategConsul categoriaConsul) {
        Producto producto = new Producto();
        consulta = "SELECT * FROM producto WHERE nombre = ?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(this.consulta);
            query.setString(1, name);
            result = query.executeQuery();
            while (result.next()) {
                producto.setCantidad(result.getInt("cantidad_existe"));
                producto.setNombre(result.getString("nombre"));
                producto.setPrecio(result.getDouble("precio"));
                producto.setDescripcion(result.getString("descripcion"));
                producto.setCategorias(categoriaConsul.getCategoriasProducto(name));
                break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos");
        } finally {
            cierre();
        }
        return producto;
    }
    
    
    public void updateProducto(Producto producto, String nameActual){
        this.consulta = "UPDATE producto set nombre = ?, precio = ?, descripcion = ?, cantidad_existe = ? WHERE nombre=?";
        try {
            conexion = Conexion.getConexion();
            query = conexion.prepareStatement(consulta);
            query.setString(1,producto.getNombre());
            query.setDouble(2, producto.getPrecio());
            query.setString(3, producto.getDescripcion());
            query.setInt(4, producto.getCantidad());
            query.setString(5, nameActual);
            query.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto Actulizado con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al Actulizar el producto en la Base de Datos");
        } finally {
            cierre();
        }
    }
    
    
}
