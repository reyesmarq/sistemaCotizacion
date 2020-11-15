/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.views;

import com.controller.ProveedorJpaController;
import com.entities.Proveedor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.Utilidades;

/**
 *
 * @author Ulises
 */
public class FrmProveedor extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmProveedor
     */
    
    Proveedor proveedor = new Proveedor();
    ProveedorJpaController jpaProveedor = new ProveedorJpaController();
    
    boolean esNuevo = false;
    boolean esEditar = false;
    
    private void mensajeOk(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mensajeError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.ERROR_MESSAGE);
    }
    
    public FrmProveedor() {
        initComponents();
        mostrar();
        habilitar(false);
        botones();
    }
    
    public void ocultarColumnas(){
        tblProveedor.removeColumn(tblProveedor.getColumnModel().getColumn(8));//Definir cantidad de columnas de tabla
    }
    
    public void botones(){
        if(esNuevo || esEditar){
            habilitar(true);
            btnNuevo.setEnabled(false);
            btnGuardar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnCancelar.setEnabled(true);
        }else{
            habilitar(false);
            btnNuevo.setEnabled(true);
            btnGuardar.setEnabled(false);
            btnEditar.setEnabled(true);
            btnCancelar.setEnabled(false);
        }
    }
    
    public void limpiar() {
        this.txtIdProveedor.setText("");
        this.txtRazonSocial.setText("");
        this.cmbSectorComercial.setSelectedIndex(0);
        this.txtTelefono.setText("");
        this.txtCorreo.setText("");
        this.cmbTipoDocumento.setSelectedIndex(0);
        this.txtTipoDocumento.setText("");
        this.txtDireccion.setText("");
    }
    
    public void habilitar(boolean valor){
        this.txtRazonSocial.setEnabled(valor);
        this.cmbSectorComercial.setEnabled(valor);
        this.txtTelefono.setEnabled(valor);
        this.txtCorreo.setEnabled(valor);
        this.cmbTipoDocumento.setEnabled(valor);
        this.txtTipoDocumento.setEnabled(valor);
        this.txtDireccion.setEnabled(valor);
    }
    
    public void buscarNombre(){
        
    }
    
    public void llenarFormulario(){
        int fila = this.tblProveedor.getSelectedRow();
        this.txtIdProveedor.setText(String.valueOf(this.tblProveedor.getValueAt(fila, 0)));
        this.txtRazonSocial.setText(String.valueOf(this.tblProveedor.getValueAt(fila, 1)));
        this.txtTelefono.setText(String.valueOf(this.tblProveedor.getValueAt(fila, 2)));
        this.cmbSectorComercial.setSelectedItem(String.valueOf(this.tblProveedor.getValueAt(fila, 3)));
        this.cmbTipoDocumento.setSelectedItem(String.valueOf(this.tblProveedor.getValueAt(fila, 4)));
        this.txtTipoDocumento.setText(String.valueOf(this.tblProveedor.getValueAt(fila, 5)));
        this.txtCorreo.setText(String.valueOf(this.tblProveedor.getValueAt(fila, 6)));
        this.txtDireccion.setText(String.valueOf(this.tblProveedor.getValueAt(fila, 7)));
        if(!chkEliminar.isSelected()){
            jTabbedPane4.setSelectedIndex(1);
        }
    }
    
    public void mostrar(){ //Continuar esto
        DefaultTableModel tabla;
        Utilidades utilidades = new Utilidades();
        String encabezados[]={"Id","Razon Social","Telefono","Sector comercial","Tipo documento","Num. de documento","Correo","Direccion","Eliminar"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[8];
        try{
            List lista;
            lista=jpaProveedor.findProveedorEntities();
            for(int i=0;i<lista.size();i++){
                proveedor=(Proveedor)lista.get(i);
                datos[0]=proveedor.getIdProveedor();
                datos[1]=proveedor.getRazonSocial();
                datos[2]=proveedor.getTelefono();
                datos[3]=proveedor.getSectorComercial();
                datos[4]=proveedor.getTipoDocumento();
                datos[5]=proveedor.getNumeroDocumento();
                datos[6]=proveedor.getEmail();
                datos[7]=proveedor.getDireccion();
                tabla.addRow(datos);
            }
            
            this.tblProveedor.setModel(tabla);
            
            if(chkEliminar.isSelected()){
                utilidades.agregarCheckBox(8, tblProveedor);
            }else{
                ocultarColumnas();
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario : "+e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtBuscar = new javax.swing.JTextPane();
        btnBuscar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProveedor = new javax.swing.JTable();
        chkEliminar = new javax.swing.JCheckBox();
        cmbBusqueda = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdProveedor = new javax.swing.JTextField();
        txtRazonSocial = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmbSectorComercial = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cmbTipoDocumento = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        txtTipoDocumento = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Proveedor");

        jScrollPane1.setViewportView(txtBuscar);

        btnBuscar.setText("Buscar");
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        btnImprimir.setText("Imprimir");

        tblProveedor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedorMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProveedor);

        chkEliminar.setText("Eliminar");
        chkEliminar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEliminarStateChanged(evt);
            }
        });

        cmbBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Documento", "Razon social" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkEliminar)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 9, Short.MAX_VALUE)
                                .addComponent(cmbBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnImprimir))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(btnEliminar)
                            .addComponent(btnImprimir))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addComponent(chkEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Listado", jPanel1);

        jLabel2.setText("Código:");

        jLabel4.setText("Razon Social:");

        txtIdProveedor.setEditable(false);
        txtIdProveedor.setEnabled(false);

        btnNuevo.setText("Nuevo");
        btnNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoMouseClicked(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMouseClicked(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });

        cmbSectorComercial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tecnología", "Alimentos", "Salud", "Ropa", "Servicios", "Muebles" }));

        jLabel10.setText("Sector comercial:");

        cmbTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NIT" }));

        jLabel12.setText("Tipo de documento:");

        jLabel5.setText("Direccion:");

        jLabel6.setText("Teléfono:");

        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(503)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel7.setText("Email:");

        txtDireccion.setColumns(20);
        txtDireccion.setRows(5);
        jScrollPane3.setViewportView(txtDireccion);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTipoDocumento))
                            .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSectorComercial, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSectorComercial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(txtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar)
                    .addComponent(btnCancelar))
                .addGap(27, 27, 27))
        );

        jTabbedPane4.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        buscarNombre();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        try {
            Utilidades utilidades = new Utilidades();
            for (int i = 0; i < tblProveedor.getRowCount(); i++) {
                if (utilidades.isSelected(i, 8, tblProveedor)) {
                    jpaProveedor.destroy(Integer.parseInt(tblProveedor.getValueAt(i, 0).toString()));
                }
            }
            mostrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Mensaje de error: "+e);
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void tblProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedorMouseClicked
        llenarFormulario();
    }//GEN-LAST:event_tblProveedorMouseClicked

    private void chkEliminarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEliminarStateChanged
        mostrar();
    }//GEN-LAST:event_chkEliminarStateChanged

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        esNuevo=true;
        esEditar=false;
        botones();
        limpiar();
        habilitar(true);
        this.txtRazonSocial.setFocusable(true);
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        try {
            String respuesta = "";
            String temp="";
            if(this.txtRazonSocial.getText().equals("")){
                mensajeError("Falta ingresar algunos datos");
                this.txtRazonSocial.setFocusable(true);
            }else{
                if(esNuevo){
                    proveedor.setRazonSocial(this.txtRazonSocial.getText());
                    proveedor.setTelefono(this.txtTelefono.getText());
                    proveedor.setSectorComercial(this.cmbSectorComercial.getSelectedItem().toString());
                    proveedor.setTipoDocumento(this.cmbTipoDocumento.getSelectedItem().toString());
                    proveedor.setNumeroDocumento(this.txtTipoDocumento.getText());
                    proveedor.setEmail(this.txtCorreo.getText());
                    proveedor.setDireccion(this.txtDireccion.getText());
                    jpaProveedor.create(proveedor);
                    respuesta = "Ok";
                }else{
                    //Codigo de edicion (Al no ser un dato nuevo quiere decir es un dato a editar)
                    proveedor.setIdProveedor(Integer.parseInt(this.txtIdProveedor.getText()));
                    proveedor.setRazonSocial(this.txtRazonSocial.getText());
                    proveedor.setTelefono(this.txtTelefono.getText());
                    proveedor.setSectorComercial(this.cmbSectorComercial.getSelectedItem().toString());
                    proveedor.setTipoDocumento(this.cmbTipoDocumento.getSelectedItem().toString());
                    proveedor.setNumeroDocumento(this.txtTipoDocumento.getText());
                    proveedor.setEmail(this.txtCorreo.getText());
                    proveedor.setDireccion(this.txtDireccion.getText());
                    jpaProveedor.edit(proveedor);
                    respuesta = "Ok";
                }
                if(respuesta.equals("Ok")){
                    if (this.esNuevo) {
                        this.mensajeOk("Se inserto de forma correcta el registro");
                    }else{
                        this.mensajeOk("Se edito de forma correcta el registro");
                    }
                }else{
                    this.mensajeError(respuesta);
                }
                esNuevo=false;
                esEditar=false;
                this.botones();
                this.limpiar();
                this.mostrar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Mensaje de error: "+e);
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked
        if (!txtIdProveedor.getText().equals("")) {
            esEditar=true;
            this.botones();
            this.habilitar(true);
        }else{
            mensajeError("Debe de seleccionar primero el registro a modificar");
        }
    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        this.esNuevo = false;
        this.esEditar = false;
        this.botones();
        this.limpiar();
        this.habilitar(false);
    }//GEN-LAST:event_btnCancelarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBox chkEliminar;
    private javax.swing.JComboBox<String> cmbBusqueda;
    private javax.swing.JComboBox<String> cmbSectorComercial;
    private javax.swing.JComboBox<String> cmbTipoDocumento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tblProveedor;
    private javax.swing.JTextPane txtBuscar;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JFormattedTextField txtTelefono;
    private javax.swing.JTextField txtTipoDocumento;
    // End of variables declaration//GEN-END:variables
}
