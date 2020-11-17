/*
 * Nombre de Formulario: FrmUsuario
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.views;

import com.controller.RolJpaController;
import com.controller.UsuarioJpaController;
import com.entities.Rol;
import com.entities.Rolusuario;
import com.entities.Usuario;
import utilidades.EncrypPass;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dguevara
 */
public class FrmUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmUsuario
     */
    //Usuario us;
    private List listaPermisos = new ArrayList();
    //UsuarioJpaController UsuarioController=new UsuarioJpaController
    Usuario usuario=new Usuario();
    UsuarioJpaController UsuarioController=new UsuarioJpaController();
    
    Rol rol=new Rol();
    RolJpaController RolController=new RolJpaController();
    
    Rolusuario RolUsuario=new Rolusuario();
    
    EncrypPass encry= new EncrypPass();
    String cryptPass;
    String pass;
    String nombreUsuario;
    int codUsuario;
    
    public FrmUsuario() {
        initComponents();
        mostrar();
        txtCodigo.setEnabled(false);
    }
    
    public FrmUsuario(List permisosUsuario) {
        
        initComponents();
        listaPermisos=permisosUsuario;
        mostrar();
        txtCodigo.setEnabled(false);
        
        if(listaPermisos.contains("AGREGAR_USUARIO"))
        {
            btnAgregar.setVisible(true);
        }
        else
        {
            btnAgregar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("MODIFICAR_USUARIO"))
        {
            btnModificar.setVisible(true);
        }
        else
        {
            btnModificar.setVisible(false);
        }
        
        
        if(listaPermisos.contains("ELIMINAR_USUARIO"))
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
        String encabezados[] = {"Código", "Nombre", "Contraseña"};
        tabla = new DefaultTableModel(null, encabezados);
        Object datos[] = new Object[3];
        try 
        {
            List lista;
            lista = UsuarioController.findUsuarioEntities();
            for (int i = 0; i < lista.size(); i++) 
            {
                usuario = (Usuario) lista.get(i);
                datos[0] = usuario.getCodigoUsuario();
                datos[1] = usuario.getNombreUsuario();
                datos[2] = usuario.getContraUsuario();
                tabla.addRow(datos);
            }
            this.tablaUsuario.setModel(tabla);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al mostrar en formulario");
        }
    }
    
    
    public void insertar() 
    {
        Object us;
        try 
        {
            //usuario.setCodigoUsuario(100000);
            usuario.setNombreUsuario(this.txtNombre.getText());
            nombreUsuario=this.txtNombre.getText();
            pass=this.txtContra.getText();
            cryptPass=encry.encrypt(pass);
            usuario.setContraUsuario(cryptPass);
            us=UsuarioController.getExistUser(nombreUsuario,cryptPass);
            int resp=Integer.parseInt(us.toString());
            if(resp==0)
            {
                UsuarioController.create(usuario);
                JOptionPane.showMessageDialog(null,"Usuario insertado correctamente");//daoUs.insertarUsuario(us));
                this.limpiar();
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Usuario ya existe");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al insertar usuario en formulario: ");
        }
    }
    
    
    
    public void modificar() 
    {
        Object us;
        try 
        {
            usuario.setCodigoUsuario(Integer.parseInt(this.txtCodigo.getText()));
            codUsuario=Integer.parseInt(this.txtCodigo.getText());
            usuario.setNombreUsuario(this.txtNombre.getText());
            pass=this.txtContra.getText();
            cryptPass=encry.encrypt(pass);
            usuario.setContraUsuario(cryptPass);
            us=UsuarioController.getDependUser(codUsuario);
            int resp=Integer.parseInt(us.toString());
            if(resp==0)
            {
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea modificar a usuario",
                    "Modificar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.OK_OPTION) 
                {
                    UsuarioController.edit(usuario);
                    JOptionPane.showMessageDialog(null,"Usuario modificado correctamente");
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
                JOptionPane.showMessageDialog(null,"Usuario no puede modificarse ya que hay registros dependientes");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario");
        }
    }
    
    
    
    public void eliminar()
    {
        Object us;
        try 
        {
            usuario.setCodigoUsuario(Integer.parseInt(this.txtCodigo.getText()));
            codUsuario=Integer.parseInt(this.txtCodigo.getText());
            us=UsuarioController.getDependUser(codUsuario);
            int resp=Integer.parseInt(us.toString());
            if(resp==0)
            {
                int respuesta = JOptionPane.showConfirmDialog(this, "Desea eliminar a usuario",
                "Eliminar", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.OK_OPTION) 
                {
                    UsuarioController.destroy(usuario.getCodigoUsuario());
                    JOptionPane.showMessageDialog(null,"Usuario eliminado correctamente");
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
                JOptionPane.showMessageDialog(null,"Usuario no puede eliminarse ya que hay registros dependientes");
            }
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,"Error al eliminar usuario");
        }
    }

    
    public void llenarTabla() 
    {
        try
        {
            int fila = this.tablaUsuario.getSelectedRow();
            String pass=String.valueOf(this.tablaUsuario.getValueAt(fila, 2));
            String cryptPass=encry.decrypt(pass);
            this.txtCodigo.setText(String.valueOf(this.tablaUsuario.getValueAt(fila, 0)));
            this.txtNombre.setText(String.valueOf(this.tablaUsuario.getValueAt(fila, 1)));
            this.txtContra.setText(cryptPass);
        
            this.txtCodigo.setEnabled(false);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al llenar tabla");
        }
        
    }
    
    public void limpiar() 
    {
        this.txtCodigo.setText("");
        this.txtNombre.setText("");
        this.txtContra.setText("");
        this.txtVerificarContra.setText("");
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
        tablaUsuario = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtContra = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtVerificarContra = new javax.swing.JPasswordField();
        btnLimpiar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        tablaUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuario);

        jLabel1.setText("CRUD USUARIOS");

        jLabel2.setText("Codigo Usuario");

        jLabel3.setText("Nombre Usuario");

        jLabel4.setText("Contraseña");

        btnAgregar.setText("Agregar");
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAgregarMousePressed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnModificarMousePressed(evt);
            }
        });
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        btnModificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnModificarKeyPressed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        jLabel5.setText("Verificar Contraseña");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(35, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtVerificarContra, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(txtNombre)
                            .addComponent(txtCodigo)
                            .addComponent(txtContra))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregar)
                            .addComponent(btnModificar)
                            .addComponent(btnEliminar)
                            .addComponent(btnLimpiar))))
                .addGap(37, 37, 37))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(203, 203, 203))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtVerificarContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tablaUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuarioMouseClicked
        llenarTabla();
    }//GEN-LAST:event_tablaUsuarioMouseClicked

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        if(txtVerificarContra==null)
        {
            JOptionPane.showMessageDialog(null, "Verificacion de contraseña está vacía");
        }
        else if (txtNombre.getText().equals("") || txtContra.getText().equals("") 
                || txtVerificarContra.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            if(txtContra.getText().equals(txtVerificarContra.getText()))
            {
                //JOptionPane.showMessageDialog(null, "Verificacion de contraseña no coincide");
                insertar();
                mostrar();
            }
            else
            {
                
                JOptionPane.showMessageDialog(null, "Verificacion de contraseña no coincide");
            }
        }
        
    }//GEN-LAST:event_btnAgregarMouseClicked

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

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

    private void btnModificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnModificarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarKeyPressed

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        if(txtVerificarContra==null)
        {
            JOptionPane.showMessageDialog(null, "Verificacion de contraseña está vacía");
        }
        else if(txtCodigo.getText().equals("") || txtNombre.getText().equals("") ||
                txtContra.getText().equals("") || txtVerificarContra.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            if(txtContra.getText().equals(txtVerificarContra.getText()))
            {
                modificar();
                mostrar();
            }
            else
            {
                //insertar();
                JOptionPane.showMessageDialog(null, "Verificacion de contraseña no coincide");
            }
        }
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnAgregarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMousePressed
        if(txtVerificarContra==null)
        {
            JOptionPane.showMessageDialog(null, "Verificacion de contraseña está vacía");
        }
        else if (txtNombre.getText().equals("") || txtContra.getText().equals("") 
                || txtVerificarContra.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "Falta completar campos");
        }
        else
        {
            if(txtContra.getText().equals(txtVerificarContra.getText()))
            {
                //JOptionPane.showMessageDialog(null, "Verificacion de contraseña no coincide");
                insertar();
                mostrar();
            }
            else
            {
                //insertar();
                JOptionPane.showMessageDialog(null, "Verificacion de contraseña no coincide");
            }
        }
    }//GEN-LAST:event_btnAgregarMousePressed

    private void btnModificarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarMousePressed


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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaUsuario;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtVerificarContra;
    // End of variables declaration//GEN-END:variables
}
