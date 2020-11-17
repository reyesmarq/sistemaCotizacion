/*
 * Nombre de Formulario: FrmPermiso
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.views;


import com.controller.PermisoJpaController;
import com.entities.Permiso;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dguevara
 */
public class FrmPermiso extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmPermiso
     */
    
    Permiso permiso=new Permiso();
    PermisoJpaController PermisoController=new PermisoJpaController();
    private List listaPermisos = new ArrayList();
    
    String nombrePermiso;
    String descripcionPermiso;
    int idPermiso;
    public FrmPermiso() {
        initComponents();
    }
    
    public FrmPermiso(List permisosRoles) {
        
        initComponents();
        listaPermisos=permisosRoles;
        mostrar();
        txtCodigo.setEnabled(false);
        
        if(listaPermisos.contains("AGREGAR_PERMISO"))
        {
            btnAgregar.setVisible(true);
        }
        else
        {
            btnAgregar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("MODIFICAR_PERMISO"))
        {
            btnModificar.setVisible(true);
        }
        else
        {
            btnModificar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("ELIMINAR_PERMISO"))
        {
            btnEliminar.setVisible(true);
        }
        else
        {
            btnEliminar.setVisible(false);
        }
    }

    
    public void mostrar() 
    {
        DefaultTableModel tabla;
        String encabezados[] = {"Código", "Nombre", "Descripcion"};
        tabla = new DefaultTableModel(null, encabezados);
        Object datos[] = new Object[3];
        try 
        {
            List lista;
            lista = PermisoController.findPermisoEntitiesOrdenado();
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
    
    public void insertar() 
    {
        Object permi;
        try 
        {
            permiso.setNombrePermiso(this.txtNombre.getText().toUpperCase());
            permiso.setDescripcionPermiso(this.txtDescripcion.getText().toUpperCase());
            nombrePermiso=this.txtNombre.getText().toUpperCase();
            descripcionPermiso=this.txtDescripcion.getText().toUpperCase();
            permi=PermisoController.getExistPermiso(nombrePermiso,descripcionPermiso);
            int resp=Integer.parseInt(permi.toString());
            if(resp==0)
            {
                PermisoController.create(permiso);
                JOptionPane.showMessageDialog(null, "Permiso ingresado correctamente");
                this.limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Permiso ya existe");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al insertar permiso en formulario"+e.toString());
        }
    }
    
    
    
    public void modificar() 
    {
        Object permi;
        try 
        {
            permiso.setCodigoPermiso(Integer.parseInt(this.txtCodigo.getText()));
            idPermiso=Integer.parseInt(this.txtCodigo.getText());
            permiso.setNombrePermiso(this.txtNombre.getText());
            permiso.setDescripcionPermiso(this.txtDescripcion.getText());
            permi=PermisoController.getDependRolPermiso(idPermiso);
            int resp=Integer.parseInt(permi.toString());
            if(resp==0)
            {
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea modificar permiso",
                    "Modificar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.OK_OPTION) 
                {
                    PermisoController.edit(permiso);
                    JOptionPane.showMessageDialog(null, "Permiso modificado correctamente");
                    mostrar();
                    limpiar();                
                } 
                else 
                {
                    mostrar();
                    limpiar();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Permiso no puede modificarse ya que hay registros dependientes");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al modificar permiso");
        }
    }
    
    
    public void eliminar()
    {
        Object permi;
        try 
        {
            permiso.setCodigoPermiso(Integer.parseInt(this.txtCodigo.getText()));
            idPermiso=Integer.parseInt(this.txtCodigo.getText());
            permi=PermisoController.getDependRolPermiso(idPermiso);
            int resp=Integer.parseInt(permi.toString());
            if(resp==0)
            {
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea eliminar permiso",
                "Eliminar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.OK_OPTION) 
                {
                    PermisoController.destroy(permiso.getCodigoPermiso());
                    JOptionPane.showMessageDialog(null, "Permiso eliminado correctamente");
                    mostrar();
                    limpiar();
                } 
                else 
                {
                    mostrar();
                    limpiar();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Permiso no puede eliminarse ya que hay registros dependientes");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Error al eliminar permiso");
        }
    }
    
    
    public void llenarTabla() 
    {
        int fila = this.tablaPermiso.getSelectedRow();
        this.txtCodigo.setText(String.valueOf(this.tablaPermiso.getValueAt(fila, 0)));
        this.txtNombre.setText(String.valueOf(this.tablaPermiso.getValueAt(fila, 1)));
        this.txtDescripcion.setText(String.valueOf(this.tablaPermiso.getValueAt(fila, 2)));
        this.txtCodigo.setEnabled(false);
    }
    
    public void limpiar() 
    {
        this.txtCodigo.setText("");
        this.txtNombre.setText("");
        this.txtDescripcion.setText("");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPermiso = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

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
        jScrollPane1.setViewportView(tablaPermiso);

        jLabel1.setText("Codigo");

        jLabel2.setText("Nombre");

        jLabel3.setText("Descripcion");

        jLabel4.setText("CRUD PERMISO");

        btnModificar.setText("Modificar");
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
        });

        btnAgregar.setText("Agregar");
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(txtDescripcion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37))
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17))
            .addGroup(layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)))
                .addGap(18, 18, 18)
                .addComponent(btnEliminar)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(btnLimpiar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        if(txtNombre.getText().equals("") || txtDescripcion.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            insertar();
            mostrar();
        }
    }//GEN-LAST:event_btnAgregarMouseClicked

    private void tablaPermisoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPermisoMouseClicked
        llenarTabla();
    }//GEN-LAST:event_tablaPermisoMouseClicked

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        if(txtNombre.getText().equals("") || txtDescripcion.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            modificar();
            mostrar();
        }
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        if(txtCodigo.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Codigo incorrecto");
        }
        else
        {
            eliminar();
            mostrar();
        }
    }//GEN-LAST:event_btnEliminarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPermiso;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
