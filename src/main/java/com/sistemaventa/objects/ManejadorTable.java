package com.sistemaventa.objects;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author elvis_agui
 */
public class ManejadorTable {

    public void manejadorTableEmpleados(List<Usuario> empleados, JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setDefaultRenderer(Object.class, new Render());
        table.setModel(modelo);
        //Anadir columnas
        modelo.addColumn("NOMBRE");
        modelo.addColumn("ROL");
        modelo.addColumn("ELIMINAR");
        modelo.addColumn("CAMBIAR ROL");
        empleados.forEach(empleado -> {
            modelo.addRow(new Object[]{empleado.getNombre(), empleado.rolString(), button("Eliminar", true, empleado.getNombre()), button("Cambiar", false, empleado.getNombre())});
        });

    }

    public void manejadorTableCategorias(List<Categoria> categorias, JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setDefaultRenderer(Object.class, new Render());
        table.setModel(modelo);
        //Anadir columnas
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("ELIMINAR");
        modelo.addColumn("EDITAR");
        categorias.forEach(categoria -> {
            modelo.addRow(new Object[]{categoria.getNombre(), categoria.getDescripcion(), button("Eliminar", true, categoria.getNombre()), button("Editar", false, categoria.getNombre())});
        });
        
        int[] anchos = {90, 300, 50, 40};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }

    public void manejadorAgotados(List<Producto> productos, JTable table) {
        DefaultTableModel modelo = new DefaultTableModel();
        table.setDefaultRenderer(Object.class, new Render());
        table.setModel(modelo);
        //Anadir columnas
        modelo.addColumn("NOMBRE");
        modelo.addColumn("PRECIO");
        modelo.addColumn("UNIDADES-EXISTENTE");
        productos.forEach(producto -> {
            modelo.addRow(new Object[]{producto.getNombre(), producto.getPrecio(), producto.getCantidad()});
        });
        
    }
    
    public void manejoInventarioProductos(List<Producto> productos, JTable table){
        DefaultTableModel modelo = new DefaultTableModel();
        table.setDefaultRenderer(Object.class, new Render());
        table.setModel(modelo);
        //Anadir columnas
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("PRECIO");
        modelo.addColumn("UNIDADES");
        modelo.addColumn("EDITAR");
        productos.forEach(producto -> {
            modelo.addRow(new Object[]{producto.getNombre(),producto.getDescripcion(), producto.getPrecio(), producto.getCantidad(), button("Editar", false, producto.getNombre())});
        });
        
        int[] anchos = {150, 275, 50, 65, 60};
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    private JButton button(String cont, boolean isEliminar, String referenciaEmpleado) {
        JButton btn = new JButton(cont);
        if (isEliminar) {
            btn.setBackground(Color.RED);
            btn.setName(referenciaEmpleado);

        } else {
            btn.setBackground(Color.YELLOW);
            btn.setName(referenciaEmpleado);
        }
        return btn;
    }



}
