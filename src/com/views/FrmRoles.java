/*
 * Nombre de Formulario: FrmRoles
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.views;



import com.controller.RolJpaController;
import com.entities.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dguevara
 */
public class FrmRoles extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmRoles
     */
    Rol rol=new Rol();
    RolJpaController RolController=new RolJpaController();
    private List listaPermisos = new ArrayList();
    int idRol;
    String nombreRol;
    String descripcionRol;
    public FrmRoles() {
        initComponents();
    }
    
    public FrmRoles(List permisosRoles) {
        
        initComponents();
        listaPermisos=permisosRoles;
        mostrar();
        txtCodigo.setEnabled(false);
        
        if(listaPermisos.contains("AGREGAR_ROL"))
        {
            btnAgregar.setVisible(true);
        }
        else
        {
            btnAgregar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("MODIFICAR_ROL"))
        {
            btnModificar.setVisible(true);
        }
        else
        {
            btnModificar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("ELIMINAR_ROL"))
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
    
    
    public void insertar() 
    {
        Object role;
        try 
        {
            rol.setNombreRol(this.txtNombre.getText());
            rol.setDescripcionRol(this.txtDescripcion.getText());
            nombreRol=this.txtNombre.getText();
            descripcionRol=this.txtDescripcion.getText();
            role=RolController.getExistRol(nombreRol,descripcionRol);
            int resp=Integer.parseInt(role.toString());
            if(resp==0)
            {
                RolController.create(rol);
                JOptionPane.showMessageDialog(null, "Rol creado correctamente");
                this.limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Rol ya existe");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al insertar rol en formulario");
        }
    }
    
    
    
    
    public void modificar() 
    {
        Object rol1;
        Object rol2;
        try 
        {
            rol.setCodigoRol(Integer.parseInt(this.txtCodigo.getText()));
            rol.setNombreRol(this.txtNombre.getText());
            rol.setDescripcionRol(this.txtDescripcion.getText());
            idRol=Integer.parseInt(this.txtCodigo.getText());
            
            rol1=RolController.getDependRolUsuario(idRol);
            rol2=RolController.getDependRolPermiso(idRol);
            
            int resp1=Integer.parseInt(rol1.toString());
            int resp2=Integer.parseInt(rol2.toString());
            if(resp1==0 || resp2==0)
            {
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea modificar a rol",
                    "Modificar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.OK_OPTION) 
                {
                    RolController.edit(rol);
                    JOptionPane.showMessageDialog(null, "Rol modificado correctamente");
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
                JOptionPane.showMessageDialog(null,"Rol no puede modificarse ya que hay registros dependientes");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al modificar rol");
        }
    }
    
    
    
    public void eliminar()
    {
        Object rol1;
        Object rol2;
        try 
        {
            rol.setCodigoRol(Integer.parseInt(this.txtCodigo.getText()));
            idRol=Integer.parseInt(this.txtCodigo.getText());
            rol1=RolController.getDependRolUsuario(idRol);
            rol2=RolController.getDependRolPermiso(idRol);
            
            int resp1=Integer.parseInt(rol1.toString());
            int resp2=Integer.parseInt(rol2.toString());
            if(resp1==0 && resp2==0)
            {
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea eliminar a rol",
                "Eliminar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.OK_OPTION) 
                {
                    RolController.destroy(rol.getCodigoRol());
                    JOptionPane.showMessageDialog(null,"Rol eliminado correctamente");
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
                JOptionPane.showMessageDialog(null,"Rol no puede eliminarse ya que hay registros dependientes");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Error al eliminar rol");
        }
    }
    
    
    
    public void llenarTabla() 
    {
        int fila = this.tablaRol.getSelectedRow();
        this.txtCodigo.setText(String.valueOf(this.tablaRol.getValueAt(fila, 0)));
        this.txtNombre.setText(String.valueOf(this.tablaRol.getValueAt(fila, 1)));
        this.txtDescripcion.setText(String.valueOf(this.tablaRol.getValueAt(fila, 2)));
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRol = new javax.swing.JTable();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("CRUD ROLES");

        jLabel2.setText("Codigo");

        jLabel3.setText("Nombre");

        jLabel4.setText("Descripcion");

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
        jScrollPane1.setViewportView(tablaRol);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4))
                                    .addGap(35, 35, 35)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                        .addComponent(txtDescripcion))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(11, 11, 11)
                .addComponent(btnAgregar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(btnModificar)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(btnEliminar)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(btnLimpiar)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaRolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRolMouseClicked
        llenarTabla();
    }//GEN-LAST:event_tablaRolMouseClicked

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

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

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
    private javax.swing.JTable tablaRol;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
