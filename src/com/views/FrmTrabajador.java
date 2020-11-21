/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.views;

import com.controller.EmpleadoJpaController;
import com.entities.Empleado;
import java.awt.event.KeyEvent;
import utilidades.Utilidades;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import utilidades.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Ulises
 */
public class FrmTrabajador extends javax.swing.JInternalFrame {

    /**
     * Creates new form FrmTrabajador
     */
    private List listaPermisos = new ArrayList();
    Utilidades utilidades = new Utilidades();
    Empleado empleado = new Empleado();
    EmpleadoJpaController jpaEmpleado = new EmpleadoJpaController();
    EncrypPass encry= new EncrypPass();
    
    String cryptPass;
    String pass;
    String nombreUsuario;
    
    int codUsuario;
    boolean esNuevo=false;
    boolean esEditar=false;
    
    private void mensajeOk(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mensajeError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.ERROR_MESSAGE);
    }
    
    public FrmTrabajador() {
        initComponents();
        mostrar();
        habilitar(false);
        botones();
    }
    
    public FrmTrabajador(String nombre, String acceso, int id) {
        initComponents();
        mostrar();
        habilitar(false);
        botones();
    }
    
    public void llenarFormulario(){
        int fila = this.tblEmpleado.getSelectedRow();
        
        this.txtIdTrabajador.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 0)));
        this.txtNombre.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 1)));
        this.txtDui.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 2)));
        this.txtNit.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 3)));
        this.txtTelefono.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 4)));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFecha = this.tblEmpleado.getValueAt(fila, 5).toString();
            Date fechas;
            fechas=sdf.parse(strFecha);
            this.dtFechaNacimiento.setDate(fechas);
        } catch (ParseException e) {
            //No hacer nada
        }
        this.txtDireccion.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 6)));
        this.cmbAcceso.setSelectedItem(String.valueOf(this.tblEmpleado.getValueAt(fila, 7)));
        this.txtUsuario.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 8)));
        this.txtContra.setText(String.valueOf(this.tblEmpleado.getValueAt(fila, 9)));
        
        if(!chkEliminar.isSelected()){
            jTabbedPane4.setSelectedIndex(1);
        }
    }
    
    public void ocultarColumnas(){
        tblEmpleado.removeColumn(tblEmpleado.getColumnModel().getColumn(10));//Definir cantidad de columnas de tabla
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
    
    public void limpiar(){
        this.txtIdTrabajador.setText("");
        this.txtNombre.setText("");
        this.txtDui.setText("");
        this.txtNit.setText("");
        this.txtTelefono.setText("");
        this.txtDireccion.setText("");
        this.txtUsuario.setText("");
        this.txtContra.setText("");
        this.cmbAcceso.setSelectedIndex(0);
    }
    
    public void habilitar(boolean valor){
        this.txtNombre.setEnabled(valor);
        this.txtDui.setEnabled(valor);
        this.txtNit.setEnabled(valor);
        this.txtTelefono.setEnabled(valor);
        this.dtFechaNacimiento.setEnabled(valor);
        this.txtDireccion.setEnabled(valor);
        this.cmbAcceso.setEnabled(valor);
        this.txtUsuario.setEnabled(valor);
        this.txtContra.setEnabled(valor);
    }

    public void mostrar(){
        DefaultTableModel tabla;
        String encabezados[] = {"Id", "Nombre","DUI","NIT","Telefono","Fecha","Direccion","Nivel de acceso","Usuario","Contraseña","Eliminar"};
        tabla = new DefaultTableModel(null, encabezados);
        Object datos[] = new Object[10];
        try{
            List<Empleado> lista = new ArrayList<>();
            EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("POE_Proyecto_finalPU");
            EntityManager entitymanager = emfactory.createEntityManager();
            
            if (txtBuscar.getText().trim().isEmpty()) {
                lista = jpaEmpleado.findEmpleadoEntities();
            } else if (!txtBuscar.getText().trim().isEmpty() && cmbBusqueda.getSelectedItem() == "Dui") {
                Query query = entitymanager.createQuery("SELECT e FROM Empleado e WHERE e.dui LIKE :dui");
                query.setParameter("dui", txtBuscar.getText() + "%");
                lista = query.getResultList();
            } else if (!txtBuscar.getText().trim().isEmpty() && cmbBusqueda.getSelectedItem() == "Nombres") {
                Query query = entitymanager.createQuery("SELECT e FROM Empleado e WHERE e.nombre LIke :nombre");
                query.setParameter("nombre", txtBuscar.getText() + "%");
                lista = query.getResultList();
            }
            
            for (int i = 0; i < lista.size(); i++) 
            {
                empleado=(Empleado)lista.get(i);
                datos[0] = empleado.getIdEmpleado();
                datos[1] = empleado.getNombre();
                datos[2] = empleado.getDui();
                datos[3] = empleado.getNit();
                datos[4] = empleado.getTelefono();
                datos[5] = empleado.getFechaNacimiento();
                datos[6] = empleado.getDireccion();
                datos[7] = empleado.getAcceso();
                datos[8] = empleado.getUser();
                datos[9] = empleado.getPassword();
                tabla.addRow(datos);
            }
            this.tblEmpleado.setModel(tabla);
            
            if(chkEliminar.isSelected()){
                utilidades.agregarCheckBox(10, tblEmpleado);
            }else{
                ocultarColumnas();
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al mostrar en datos");
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleado = new javax.swing.JTable();
        chkEliminar = new javax.swing.JCheckBox();
        cmbBusqueda = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdTrabajador = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JFormattedTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        dtFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        txtDui = new javax.swing.JFormattedTextField();
        txtNit = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtContra = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        cmbAcceso = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Empleados");

        jScrollPane1.setViewportView(txtBuscar);

        btnBuscar.setText("Buscar");
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        tblEmpleado.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpleadoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmpleado);

        chkEliminar.setText("Eliminar");
        chkEliminar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEliminarStateChanged(evt);
            }
        });

        cmbBusqueda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dui", "Nombres" }));

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
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cmbBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar)
                                .addGap(106, 106, 106))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(btnEliminar))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmbBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)))
                .addComponent(chkEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane4.addTab("Listado", jPanel1);

        jLabel2.setText("Código:");

        jLabel4.setText("Nombre:");

        txtIdTrabajador.setEditable(false);
        txtIdTrabajador.setEnabled(false);

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

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

        jLabel12.setText("DUI:");

        jLabel5.setText("Direccion:");

        jLabel6.setText("Teléfono:");

        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        txtDireccion.setColumns(20);
        txtDireccion.setRows(5);
        jScrollPane3.setViewportView(txtDireccion);

        jLabel13.setText("NIT:");

        dtFechaNacimiento.setDateFormatString("dd-MM-yyyy");

        jLabel11.setText("Fecha de nacimiento:");

        try {
            txtDui.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            txtNit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-######-###-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel7.setText("Nombre Usuario:");

        jLabel9.setText("Contraseña:");

        jLabel10.setText("Acceso:");

        cmbAcceso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Bodeguero", "Vendedor" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addComponent(jLabel7))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(12, 12, 12)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(70, 70, 70)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtIdTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtContra)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGap(63, 63, 63)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdTrabajador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(txtDui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cmbAcceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar)
                    .addComponent(btnCancelar))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jTabbedPane4)
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
        // La parte de filtrado 
        mostrar();
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        try {
            Utilidades utilidades = new Utilidades();
            for (int i = 0; i < tblEmpleado.getRowCount(); i++) {
                if (utilidades.isSelected(i, 10, tblEmpleado)) {
                    jpaEmpleado.destroy(Integer.parseInt(tblEmpleado.getValueAt(i, 0).toString()));
                }
            }
            mostrar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Mensaje de error: "+e);
        }
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void tblEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadoMouseClicked
        llenarFormulario();
    }//GEN-LAST:event_tblEmpleadoMouseClicked

    private void chkEliminarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEliminarStateChanged
        mostrar();
    }//GEN-LAST:event_chkEliminarStateChanged

    private void btnNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoMouseClicked
        esNuevo = true;
        esEditar=false;
        botones();
        limpiar();
        habilitar(true);
        this.txtNombre.setFocusable(true);
    }//GEN-LAST:event_btnNuevoMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        try {
            String respuesta = "";
            String temp="";
            if(this.txtNombre.getText().equals("") || this.txtUsuario.getText().equals("")||this.txtContra.getText().equals("")){
                mensajeError("Falta ingresar datos");
                this.txtNombre.setFocusable(true);
            }else{
                if(esNuevo){
                    empleado.setNombre(this.txtNombre.getText());
                    empleado.setDui(this.txtDui.getText());
                    empleado.setNit(this.txtNit.getText());
                    empleado.setTelefono(this.txtTelefono.getText());
                    Date date = dtFechaNacimiento.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                    empleado.setFechaNacimiento(sdf.format(date));
                    empleado.setDireccion(this.txtDireccion.getText());
                    empleado.setUser(this.txtUsuario.getText());
                    empleado.setAcceso(this.cmbAcceso.getSelectedItem().toString());
                    String cryptPass;
                    pass=this.txtContra.getText();
                    cryptPass=encry.encrypt(pass);
                    empleado.setPassword(cryptPass);
                    jpaEmpleado.create(empleado);
                    respuesta = "Ok";
                }else{
                    //Codigo de edicion (Al no ser un dato nuevo quiere decir es un dato a editar)
                    empleado.setIdEmpleado(Integer.parseInt(this.txtIdTrabajador.getText().toString()));
                    empleado.setNombre(this.txtNombre.getText());
                    empleado.setDui(this.txtDui.getText());
                    empleado.setNit(this.txtNit.getText());
                    empleado.setTelefono(this.txtTelefono.getText());
                    Date date = dtFechaNacimiento.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                    empleado.setFechaNacimiento(sdf.format(date));
                    empleado.setDireccion(this.txtDireccion.getText());
                    empleado.setUser(this.txtUsuario.getText());
                    empleado.setAcceso(this.cmbAcceso.getSelectedItem().toString());
                    String cryptPass;
                    pass=this.txtContra.getText();
                    cryptPass=encry.encrypt(pass);
                    empleado.setPassword(cryptPass);
                    jpaEmpleado.edit(empleado);
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
        if (!this.txtIdTrabajador.getText().equals("")) {
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

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        Character s = evt.getKeyChar();
       
       if(!Character.isLetter(s) && s !=KeyEvent.VK_SPACE){
           
           evt.consume();
       }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // el filtrado se manejo en mostrar();
        mostrar();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JCheckBox chkEliminar;
    private javax.swing.JComboBox<String> cmbAcceso;
    private javax.swing.JComboBox<String> cmbBusqueda;
    private com.toedter.calendar.JDateChooser dtFechaNacimiento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTable tblEmpleado;
    private javax.swing.JTextPane txtBuscar;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JFormattedTextField txtDui;
    private javax.swing.JTextField txtIdTrabajador;
    private javax.swing.JFormattedTextField txtNit;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JFormattedTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
