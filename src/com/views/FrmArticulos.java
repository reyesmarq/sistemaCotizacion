/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.views;

import com.views.FrmPrincipal;
import com.controller.ArticuloJpaController;
import com.controller.CategoriaJpaController;
import com.controller.PresentacionJpaController;
import com.entities.Articulo;
import com.entities.Categoria;
import com.entities.Presentacion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.ComboItem;
import utilidades.Utilidades;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ulises
 */
public class FrmArticulos extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmArticulos
     */
    
    Categoria categoria = new Categoria();
    CategoriaJpaController jpaCategoria = new CategoriaJpaController();
    Presentacion presentacion = new Presentacion();
    PresentacionJpaController jpaPresentacion = new PresentacionJpaController();
    Articulo articulo = new Articulo();
    ArticuloJpaController jpaArticulo = new ArticuloJpaController();
    FrmVistaCategoriaArticulos visaCategoria = null;
    FrmVistaPresentacionArticulos vistaPresentacion = null;
    
    
    boolean esNuevo=false;
    boolean esEditar = false;
    
    public FrmArticulos() {
        initComponents();
        this.mostrar();
        this.habilitar(false);
        this.botones();
        this.txtIdArticulo.setEnabled(false);
        this.txtIdCategoria.setEnabled(false);
    }
    
    private void llenarComboPresentacion(JComboBox combo){ //Llenado de combobox modificado a listas.
        List list=jpaPresentacion.findPresentacionEntities();
        for(int i=0;i<list.size();i++){
            presentacion=(Presentacion)list.get(i);
            combo.addItem(new ComboItem(presentacion.getIdPresentacion(),presentacion.getNombre()));
        }
    }
    
    public void llenarFormulario(){ //Para el llenado de formulario necesito traer el id de articulo
        int fila = this.tblArticulo.getSelectedRow();
        this.txtIdArticulo.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 0)));
        this.txtCodigo.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 1)));
        this.txtNombre.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 2)));
        this.txtIdCategoria.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 3)));
        this.txtCategoria.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 4)));
        this.txtIdPresentacion.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 5)));
        this.txtPresentacion.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 6)));
        this.txtDescripcion.setText(String.valueOf(this.tblArticulo.getValueAt(fila, 7)));
        if(!chkEliminar.isSelected()){
            jTabbedPane4.setSelectedIndex(1);
        }
    }
    
    public void mostrar(){
        DefaultTableModel tabla;
        String temp;
        Utilidades utilidades = new Utilidades();
        String encabezados[]={"Id","Codigo","Nombre","Id Cat.","Categoria","Id Pre.","Presentacion","Descripción","Eliminar"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[9];
        try{
            List lista;
            lista=jpaArticulo.findArticuloEntities();
            
            for(int i=0;i<lista.size();i++){
                articulo=(Articulo)lista.get(i);
                datos[0]=articulo.getIdArticulo();
                datos[1]=articulo.getCodigo();
                datos[2]=articulo.getNombre();
                datos[3]=articulo.getIdCategoria().getIdCategoria();
                datos[4]=articulo.getIdCategoria().getNombre();
                datos[5]=articulo.getIdPresentacion().getIdPresentacion();
                datos[6]=articulo.getIdPresentacion().getNombre();
                datos[7]=articulo.getDescripcion();
                tabla.addRow(datos);
            }
            
            this.tblArticulo.setModel(tabla);
            
            if(chkEliminar.isSelected()){
                utilidades.agregarCheckBox(8, tblArticulo);
            }else{
                ocultarColumnas();
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario : "+e);
        }
    }
    
    private void mensajeOk(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mensajeError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.ERROR_MESSAGE);
    }
    
    public void limpiar() {
        this.txtIdArticulo.setText("");
        this.txtNombre.setText("");        
        this.txtDescripcion.setText("");
        this.txtIdCategoria.setText("");
        this.txtCategoria.setText("");
        this.txtIdPresentacion.setText("");
        this.txtPresentacion.setText("");
        this.txtCodigo.setText("");
    }
    
    public void habilitar(boolean valor){
        this.txtCodigo.setEnabled(valor);
        this.txtNombre.setEnabled(valor);
        this.txtDescripcion.setEnabled(valor);
        this.btnBuscarCategoria.setEnabled(valor);
        this.btnBuscarPresentacion.setEnabled(valor);
    }
    
    public void ocultarColumnas(){
        tblArticulo.removeColumn(tblArticulo.getColumnModel().getColumn(8));//Definir cantidad de columnas de tabla
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
    
    public void buscarNombre(){
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtBuscar = new javax.swing.JTextPane();
        btnBuscar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblArticulo = new javax.swing.JTable();
        chkEliminar = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtIdArticulo = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtCategoria = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnBuscarCategoria = new javax.swing.JButton();
        txtIdCategoria = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtPresentacion = new javax.swing.JTextField();
        txtIdPresentacion = new javax.swing.JTextField();
        btnBuscarPresentacion = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel1.setText("Nombre:");

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

        tblArticulo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArticuloMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblArticulo);

        chkEliminar.setText("Eliminar");
        chkEliminar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEliminarStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chkEliminar)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBuscar)
                        .addComponent(btnEliminar)
                        .addComponent(btnImprimir))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Listado", jPanel1);

        jLabel2.setText("Código:");

        jLabel3.setText("Código de ventas:");

        jLabel4.setText("Nombre:");

        jLabel5.setText("Descripción:");

        txtIdArticulo.setEnabled(false);

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        jLabel6.setText("Categoria:");

        btnBuscarCategoria.setText("Buscar");
        btnBuscarCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarCategoriaMouseClicked(evt);
            }
        });
        btnBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCategoriaActionPerformed(evt);
            }
        });

        txtIdCategoria.setEditable(false);
        txtIdCategoria.setEnabled(false);

        jLabel7.setText("Presentación:");

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

        jLabel9.setText("Id Categoria:");

        txtIdPresentacion.setEditable(false);
        txtIdPresentacion.setEnabled(false);

        btnBuscarPresentacion.setText("Buscar");
        btnBuscarPresentacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPresentacionActionPerformed(evt);
            }
        });

        jLabel10.setText("Id Presentacion:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIdArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3)
                    .addComponent(txtNombre)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtPresentacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(txtIdPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(58, 58, 58))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarCategoria)
                    .addComponent(btnBuscarPresentacion, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(141, 141, 141))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCategoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtIdPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPresentacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarPresentacion)
                            .addComponent(jLabel7)))
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar)
                    .addComponent(btnCancelar))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Mantenimiento", jPanel2);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Artículos");

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
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        buscarNombre();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        esNuevo=true;
        esEditar=false;
        botones();
        limpiar();
        habilitar(true);
        this.txtCodigo.setFocusable(true);
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        try {
            String respuesta = "";
            if(this.txtNombre.getText().equals("")){
                mensajeError("Falta ingresar algunos datos");
                this.txtCodigo.setFocusable(true);
            }else{
                if(esNuevo){
                    articulo.setCodigo(this.txtCodigo.getText());
                    articulo.setNombre(this.txtNombre.getText());
                    articulo.setDescripcion(this.txtDescripcion.getText());
                    categoria.setIdCategoria(Integer.parseInt(this.txtIdCategoria.getText()));
                    articulo.setIdCategoria(categoria);
                    presentacion.setIdPresentacion(Integer.parseInt(this.txtIdPresentacion.getText()));
                    articulo.setIdPresentacion(presentacion);
                    articulo.setIdPresentacion(presentacion);
                    jpaArticulo.create(articulo);
                    respuesta = "Ok";
                }else{
                    //Codigo de edicion (Al no ser un dato nuevo quiere decir es un dato a editar)
                    articulo.setIdArticulo(Integer.parseInt(this.txtIdArticulo.getText()));
                    articulo.setCodigo(this.txtCodigo.getText());
                    articulo.setNombre(this.txtNombre.getText());
                    articulo.setDescripcion(this.txtDescripcion.getText());
                    categoria.setIdCategoria(Integer.parseInt(this.txtIdCategoria.getText()));
                    articulo.setIdCategoria(categoria);
                    presentacion.setIdPresentacion(Integer.parseInt(this.txtIdPresentacion.getText()));
                    articulo.setIdPresentacion(presentacion);
                    articulo.setIdPresentacion(presentacion);
                    jpaArticulo.edit(articulo);
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
        if (!txtIdArticulo.getText().equals("")) {
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

    private void tblArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArticuloMouseClicked
        llenarFormulario();
    }//GEN-LAST:event_tblArticuloMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        try {
            Utilidades utilidades = new Utilidades();
            for (int i = 0; i < tblArticulo.getRowCount(); i++) {
                if (utilidades.isSelected(i, 6, tblArticulo)) {
                    jpaArticulo.destroy(Integer.parseInt(tblArticulo.getValueAt(i, 0).toString()));
                }
            }
            mostrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Mensaje de error: "+e);
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void chkEliminarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEliminarStateChanged
        mostrar();
    }//GEN-LAST:event_chkEliminarStateChanged

    private void btnBuscarCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarCategoriaMouseClicked
        
    }//GEN-LAST:event_btnBuscarCategoriaMouseClicked

    private void btnBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCategoriaActionPerformed
        try {
            if(visaCategoria==null){
                visaCategoria=new FrmVistaCategoriaArticulos();
            }
            visaCategoria.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarCategoriaActionPerformed

    private void btnBuscarPresentacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPresentacionActionPerformed
        try {
            if(vistaPresentacion==null){
                vistaPresentacion=new FrmVistaPresentacionArticulos();
            }
            vistaPresentacion.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarPresentacionActionPerformed
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarCategoria;
    private javax.swing.JButton btnBuscarPresentacion;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBox chkEliminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable tblArticulo;
    private javax.swing.JTextPane txtBuscar;
    public static javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtIdArticulo;
    public static javax.swing.JTextField txtIdCategoria;
    public static javax.swing.JTextField txtIdPresentacion;
    private javax.swing.JTextField txtNombre;
    public static javax.swing.JTextField txtPresentacion;
    // End of variables declaration//GEN-END:variables
}
