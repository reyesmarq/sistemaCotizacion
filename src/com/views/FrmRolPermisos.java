/*
 * Nombre de Formulario: FrmRolPermisos
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.views;

import com.controller.PermisoJpaController;
import com.controller.RolJpaController;
import com.controller.RolpermisoJpaController;
import com.entities.Permiso;
import com.entities.Rol;
import com.entities.Rolpermiso;
import utilidades.ComboItem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dguevara
 */
public class FrmRolPermisos extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRolPermisos
     */
    
    //Dao y Modelo RolPermiso
    RolpermisoJpaController RolPermisoController=new RolpermisoJpaController();
    Rolpermiso rolPermiso;
    
    //Dao y Modelo Rol
    RolJpaController RolController=new RolJpaController();
    Rol rol;
    
    //Dao y Modelo Permiso
    PermisoJpaController PermisoController=new PermisoJpaController();
    Permiso permiso;

    
    private List listaPermisos = new ArrayList();
    
    //Variables para roles y permisos
    int globalPermiso;
    int globalRol;
    
    public FrmRolPermisos() {
        initComponents();
    }
    
    public FrmRolPermisos(List permisosRoles) {
        
        initComponents();
        listaPermisos=permisosRoles;
        mostrarRolPermisos();
        mostrarRoles();
        mostrarPermisos();
        txtCodigo.setEnabled(false);
        btnModificar.setVisible(false);
        
        
        if(listaPermisos.contains("AGREGAR_ROLPERMISO"))
        {
            btnAgregar.setVisible(true);
        }
        else
        {
            btnAgregar.setVisible(false);
        }
        
        
        /*if(listaPermisos.contains("MODIFICAR_ROLPERMISO"))
        {
            btnModificar.setVisible(true);
        }
        else
        {
            btnModificar.setVisible(false);
        }*/
        
        
        if(listaPermisos.contains("ELIMINAR_ROLPERMISO"))
        {
            btnEliminar.setVisible(true);
        }
        else
        {
            btnEliminar.setVisible(false);
        }
    }
    
    
    
    public void mostrarRolPermisos() 
    {
        DefaultTableModel tabla;
        String encabezados[] = {"Código", "Codigo Rol", "Codigo Permiso"};
        tabla = new DefaultTableModel(null, encabezados);
        Object datos[] = new Object[3];
        try 
        {
            List lista;
            lista = RolPermisoController.findRolpermisoEntities();
            for (int i = 0; i < lista.size(); i++) 
            {
                rolPermiso = (Rolpermiso) lista.get(i);
                datos[0] = rolPermiso.getCodigoRolPermiso();
                datos[1] = rolPermiso.getCodigoRol().getNombreRol();
                datos[2] = rolPermiso.getCodigoPermiso().getNombrePermiso();
                tabla.addRow(datos);
            }
            this.tablaRolPermiso.setModel(tabla);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al mostrar en formulario");
        }
    }
    
    
    public void mostrarRoles() 
    {
        DefaultTableModel tabla;
        String encabezados[] = {"Código", "Nombre", "Descripcion"};
        tabla = new DefaultTableModel(null, encabezados);
        Object datos[] = new Object[3];
        try 
        {
            List lista;
            lista = RolController.findRolEntities();
            for (int i = 0; i < lista.size(); i++) 
            {
                rol = (Rol) lista.get(i);
                datos[0] = rol.getCodigoRol();
                datos[1] = rol.getNombreRol();
                datos[2] = rol.getDescripcionRol();
                tabla.addRow(datos);
            }
            this.tablaRol.setModel(tabla);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al mostrar en formulario");
        }
    }
    
    
    public void mostrarPermisos() 
    {
        DefaultTableModel tabla;
        String encabezados[] = {"Código", "Nombre", "Descripcion"};
        tabla = new DefaultTableModel(null, encabezados);
        Object datos[] = new Object[3];
        try 
        {
            List lista;
            lista = PermisoController.findPermisoEntities();
            for (int i = 0; i < lista.size(); i++) 
            {
                permiso = (Permiso) lista.get(i);
                datos[0] = permiso.getCodigoPermiso();
                datos[1] = permiso.getNombrePermiso();
                datos[2] = permiso.getDescripcionPermiso();
                tabla.addRow(datos);
            }
            this.tablaPermiso.setModel(tabla);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al mostrar en formulario");
        }
    }
    
    
    public void llenarTablaRolPermiso() 
    {
        int fila = this.tablaRolPermiso.getSelectedRow();
        this.txtCodigo.setText(String.valueOf(this.tablaRolPermiso.getValueAt(fila, 0)));
        
        this.txtRol.setText(String.valueOf(this.tablaRolPermiso.getValueAt(fila, 1)));
        
        this.txtPermiso.setText(String.valueOf(this.tablaRolPermiso.getValueAt(fila, 2)));
        /*String cmbRol = String.valueOf(this.tablaRolPermiso.getValueAt(fila, 1));
        comboRol.setSelectedItem(cmbRol);
        
        String cmbPer = String.valueOf(this.tablaRolPermiso.getValueAt(fila, 2));
        comboPermiso.setSelectedItem(cmbPer);*/
        this.txtCodigo.setEnabled(false);
    }
    
    
    
    public void llenarTablaRol() 
    {
        int fila = this.tablaRol.getSelectedRow();
        //this.txtCodigo.setText(String.valueOf(this.tablaRolPermiso.getValueAt(fila, 0)));
        
        this.txtCodigo.setText("");
        globalRol=Integer.parseInt(tablaRol.getValueAt(fila,0).toString());
        this.txtRol.setText(String.valueOf(this.tablaRol.getValueAt(fila, 1)));
        //comboRol.getModel().setSelectedItem(cmbRol);
        
        /*String cmbPer = String.valueOf(this.tablaRolPermiso.getValueAt(fila, 2));
        comboPermiso.getModel().setSelectedItem(cmbPer);*/
        this.txtCodigo.setEnabled(false);
    }
    
    
    
    public void llenarTablaPermiso() 
    {
        int fila = this.tablaPermiso.getSelectedRow();
        //this.txtCodigo.setText(String.valueOf(this.tablaRolPermiso.getValueAt(fila, 0)));
        this.txtCodigo.setText("");
        /*String cmbRol = String.valueOf(this.tablaRolPermiso.getValueAt(fila, 1));
        comboRol.getModel().setSelectedItem(cmbRol);*/
        globalPermiso=Integer.parseInt(tablaPermiso.getValueAt(fila,0).toString());
        this.txtPermiso.setText(String.valueOf(this.tablaPermiso.getValueAt(fila, 1)));
        //comboPermiso.getModel().setSelectedItem(cmbPer);
        this.txtCodigo.setEnabled(false);
    }
    
    
    public void insertar() 
    {
        try 
        {
            rolPermiso.setCodigoRol(RolController.findRol(globalRol));//globalRol
            rolPermiso.setCodigoPermiso(PermisoController.findPermiso(globalPermiso));//globalPermiso
            RolPermisoController.create(rolPermiso);
            JOptionPane.showMessageDialog(null, "Permiso de Rol ingresado correctamente");
            this.limpiar();
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al insertar permiso de rol en formulario");
        }
    }
    
    public void modificar() 
    {
        try 
        {
            rolPermiso.setCodigoRolPermiso(Integer.parseInt(this.txtCodigo.getText()));
            rolPermiso.setCodigoRol(RolController.findRol(globalRol));//globalRol
            rolPermiso.setCodigoPermiso(PermisoController.findPermiso(globalPermiso));
            int respuesta = JOptionPane.showConfirmDialog(this, "Desea modificar a permiso de rol",
                    "Modificar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.OK_OPTION) 
            {
                RolPermisoController.edit(rolPermiso);
                JOptionPane.showMessageDialog(null,"Permiso de rol modificado correctamente");
                mostrarRolPermisos();
                limpiar();                
            } 
            else 
            {
                mostrarRolPermisos();
                limpiar();
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al modificar rol");
        }
    }
    
    
    
    public void eliminar()
    {
        try 
        {
            rolPermiso.setCodigoRolPermiso(Integer.parseInt(this.txtCodigo.getText()));
            int respuesta = JOptionPane.showConfirmDialog(this, "Desea eliminar permiso del rol",
                "Eliminar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.OK_OPTION) 
            {
                RolPermisoController.destroy(rolPermiso.getCodigoRolPermiso());
                JOptionPane.showMessageDialog(null, "Permiso de rol eliminado correctamente");
                mostrarRolPermisos();
                limpiar();
            } 
            else 
            {
                mostrarRolPermisos();
                limpiar();
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Error al eliminar permiso del rol");
        }
    }
    
    
    public void limpiar() 
    {
        this.txtCodigo.setText("");
        this.txtRol.setText("");
        this.txtPermiso.setText("");
        //this.txtCodigo.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRolPermiso = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaRol = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPermiso = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        txtRol = new javax.swing.JTextField();
        txtPermiso = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("CRUD ROLES PERMISOS");

        jLabel2.setText("Codigo");

        jLabel3.setText("Rol");

        jLabel4.setText("Permiso");

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        tablaRolPermiso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaRolPermiso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRolPermisoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRolPermiso);

        jLabel5.setText("ROLES");

        tablaRol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaRol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRolMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaRol);

        jLabel6.setText("PERMISOS");

        tablaPermiso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaPermiso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPermisoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaPermiso);

        btnAgregar.setText("Agregar");
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(37, 37, 37)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                .addComponent(txtRol)
                                .addComponent(txtPermiso))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(226, 226, 226))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(209, 209, 209))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(336, 336, 336)
                .addComponent(jLabel1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnAgregar))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(btnModificar)
                                    .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addComponent(btnEliminar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtPermiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30)
                        .addComponent(btnLimpiar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void tablaRolPermisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRolPermisoMouseClicked
        llenarTablaRolPermiso();
    }//GEN-LAST:event_tablaRolPermisoMouseClicked

    private void tablaRolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRolMouseClicked
        llenarTablaRol();
    }//GEN-LAST:event_tablaRolMouseClicked

    private void tablaPermisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPermisoMouseClicked
       llenarTablaPermiso();
    }//GEN-LAST:event_tablaPermisoMouseClicked

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        if(this.txtRol.getText().equals("") || txtPermiso.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            insertar();
            mostrarRolPermisos();
        }
    }//GEN-LAST:event_btnAgregarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        if(txtCodigo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Codigo incorrecto");
        }
        else
        {
            eliminar();
            mostrarRolPermisos();
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        modificar();
    }//GEN-LAST:event_btnModificarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaPermiso;
    private javax.swing.JTable tablaRol;
    private javax.swing.JTable tablaRolPermiso;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtPermiso;
    private javax.swing.JTextField txtRol;
    // End of variables declaration//GEN-END:variables
}
