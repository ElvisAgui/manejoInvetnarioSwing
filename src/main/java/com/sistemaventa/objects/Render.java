
package com.sistemaventa.objects;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author elvis_agui
 */
public class Render extends DefaultTableCellRenderer{

    //clase para renderizar la celda de la tabla y poder colocar componentes dinamicamente
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        if (value instanceof JButton) {
            JButton btn = (JButton) value;
            return btn;
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
