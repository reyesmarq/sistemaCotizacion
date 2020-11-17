/*
 * Nombre de Formulario: FrmRolUsuario
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.views;


import com.controller.RolJpaController;
import com.controller.RolusuarioJpaController;
import com.controller.UsuarioJpaController;
import com.entities.Rol;
import com.entities.Rolusuario;
import com.entities.Usuario;
import utilidades.ComboItem;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dguevara
 */
public class FmrRolUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form RolUsuario
     */
    Rolusuario rolUsuario=new Rolusuario();
    RolusuarioJpaController RolUsuarioController=new RolusuarioJpaController();
    
    Rol rol=new Rol();
    RolJpaController RolController=new RolJpaController();
    
    Usuario usuario=new Usuario();
    UsuarioJpaController UsuarioController=new UsuarioJpaController();
    
    private List listaPermisos = new ArrayList();
    
    public FmrRolUsuario() {
        initComponents();
    }
    
    public FmrRolUsuario(List permisosRoles) throws Exception 
    {
        initComponents();
        listaPermisos=permisosRoles;
        mostrar();
        List lista=(List<Rol>)RolController.findRolEntities();
        cargarCombo(comboRol, lista);
        txtCodigo.setEnabled(false);
        
        
        if(listaPermisos.contains("AGREGAR_ROLUSUARIO"))
        {
            btnAgregar.setVisible(true);
        }
        else
        {
            btnAgregar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("MODIFICAR_ROLUSUARIO"))
        {
            btnModificar.setVisible(true);
        }
        else
        {
            btnModificar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("ELIMINAR_ROLUSUARIO"))
        {
            btnEliminar.setVisible(true);
        }
        else
        {
            btnEliminar.setVisible(false);
        }
    }
    
    //Metodo para cargar Combo
    private void cargarCombo(JComboBox combo,List<Rol> list)
    {
        for(Rol item:list)
        {
            combo.addItem(new ComboItem(item.getCodigoRol(),item.getNombreRol()));
        }
    }
    
    public void mostrar()
    {
        DefaultTableModel tabla;
        String encabezados[]={"Codigo","Rol","Codigo Usuario"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[3];
        try
        {
            List lista;
            lista=RolUsuarioController.findRolusuarioEntities();
            for(int i=0;i<lista.size();i++)
            {
                rolUsuario=(Rolusuario)lista.get(i);
                datos[0]=rolUsuario.getCodigoRolUsuario();
                datos[1]=rolUsuario.getCodigoRol().getNombreRol();
                datos[2]=rolUsuario.getCodigoUsuario().getCodigoUsuario();
                
                tabla.addRow(datos);
            }
            this.tablaRolUsuario.setModel(tabla);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario");
        }
    }
    
    
    
    public void insertar()
    {
        try
        {
            
            String rol = comboRol.getSelectedItem().toString();
            ComboItem item = new ComboItem();

            for (int i = 0; i < comboRol.getItemCount(); i++) 
            {
                if (rol.equals(comboRol.getItemAt(i).toString())) 
                {
                    item = comboRol.getModel().getElementAt(i) ;
                }
            }            
            rolUsuario.setCodigoRol(RolController.findRol(item.getValue()));
            
            rolUsuario.setCodigoUsuario(UsuarioController.findUsuario(Integer.parseInt(this.txtCodigoUsuario.getText())));
            RolUsuarioController.create(rolUsuario);
            
            JOptionPane.showMessageDialog(null,"Rol de Usuario insertado correctamente");
            mostrar();
            limpiar();
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al insertar rol asignado a usuario");
        }
    }
    
    
    public void modificar() 
    {
        try 
        {
            String rol = comboRol.getSelectedItem().toString();
            ComboItem item = new ComboItem();

            for (int i = 0; i < comboRol.getItemCount(); i++) 
            {
                if (rol.equals(comboRol.getItemAt(i).toString())) 
                {
                    item = comboRol.getModel().getElementAt(i) ;
                }
            }            
            rolUsuario.setCodigoRol(RolController.findRol(item.getValue()));
            
            rolUsuario.setCodigoUsuario(UsuarioController.findUsuario(Integer.parseInt(this.txtCodigoUsuario.getText())));
            RolUsuarioController.edit(rolUsuario);
            
            int respuesta = JOptionPane.showConfirmDialog(this, "Desea modificar a rol de usuario",
                    "Modificar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.OK_OPTION) 
            {
                RolUsuarioController.edit(rolUsuario);
                JOptionPane.showMessageDialog(null, "Rol de Usuario modificado correctamente");
                mostrar();
                limpiar();                
            } 
            else 
            {
                mostrar();
                limpiar();
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al modificar rol de usuario");
        }
    }
    
    
    public void eliminar()
    {
        try 
        {
            rolUsuario.setCodigoRolUsuario(Integer.parseInt(this.txtCodigo.getText()));
            int respuesta = JOptionPane.showConfirmDialog(this, "Desea eliminar a rol de usuario",
                "Eliminar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.OK_OPTION) 
            {
                RolUsuarioController.destroy(rolUsuario.getCodigoRolUsuario());
                JOptionPane.showMessageDialog(null, "Rol de Usuario eliminado correctamente");
                mostrar();
                limpiar();
            } 
            else 
            {
                mostrar();
                limpiar();
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Error al eliminar rol");
        }
    }
    
    
    
    public void limpiar()
    {
        this.txtCodigo.setText("");
        this.txtCodigoUsuario.setText("");
        this.comboRol.setSelectedIndex(0);
        //this.txtCodigo.setEnabled(true);
    }
    
    
    public void llenarTabla()
    {
        limpiar();
        int fila=this.tablaRolUsuario.getSelectedRow();
        this.txtCodigo.setText(String.valueOf(this.tablaRolUsuario.getValueAt(fila,0)));
        
        String role = String.valueOf(this.tablaRolUsuario.getValueAt(fila, 1));
        comboRol.getModel().setSelectedItem(role);
        
        this.txtCodigoUsuario.setText(String.valueOf(this.tablaRolUsuario.getValueAt(fila,2)));
        //this.txtCodigo.setEnabled(false);
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
        txtCodigoUsuario = new javax.swing.JTextField();
        comboRol = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRolUsuario = new javax.swing.JTable();
        btnLimpiar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("CRUD ROL USUARIOS");

        jLabel2.setText("Codigo");

        jLabel3.setText("Codigo Usuario");

        jLabel4.setText("Codigo Rol");

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

        tablaRolUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaRolUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRolUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRolUsuario);

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
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                .addComponent(txtCodigo))
                            .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(194, 194, 194))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(comboRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(btnLimpiar)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablaRolUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRolUsuarioMouseClicked
        llenarTabla();
    }//GEN-LAST:event_tablaRolUsuarioMouseClicked

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        if(txtCodigoUsuario.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            insertar();
            mostrar();
        }
    }//GEN-LAST:event_btnAgregarMouseClicked

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        if(txtCodigoUsuario.getText().equals("") || txtCodigo.getText().equals(""))
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
    private javax.swing.JComboBox<ComboItem> comboRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRolUsuario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoUsuario;
    // End of variables declaration//GEN-END:variables
}
