/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilidades;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *  Nombre de la clase: utilidades
 *  Fecha: 11-05-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class Utilidades {
    public void agregarCheckBox(int column, JTable table){
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
    
    public boolean isSelected(int row, int column, JTable table){
        return table.getValueAt(row, column)!= null;
    }
}
