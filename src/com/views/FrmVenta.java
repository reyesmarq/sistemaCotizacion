/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.views;

import com.controller.ClienteJpaController;
import com.controller.DetalleventaJpaController;
import com.controller.EmpleadoJpaController;
import com.controller.VentaJpaController;
import com.entities.Cliente;
import com.entities.Detalleingreso;
import com.entities.Detalleventa;
import com.entities.Empleado;
import com.entities.Ingreso;
import com.entities.Venta;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.Utilidades;

/**
 *
 * @author Ulises
 */
public class FrmVenta extends javax.swing.JInternalFrame {
    
    boolean esNuevo=false;
    DefaultTableModel tablaTemp;
    
    FrmVistaCliente vistaCliente = new FrmVistaCliente();
    FrmVentaArticulos vistaVentaArticulo = new FrmVentaArticulos();
    Venta venta = new Venta();
    VentaJpaController jpaVenta = new VentaJpaController();
    Detalleventa detalleVenta = new Detalleventa();
    DetalleventaJpaController jpaDetalleVenta = new DetalleventaJpaController();
    Empleado empleado = new Empleado();
    EmpleadoJpaController jpaEmpleado = new EmpleadoJpaController();
    Cliente cliente = new Cliente();
    ClienteJpaController jpaCliente = new ClienteJpaController();
    
    String nombre;
    String acceso;
    int idEmpleado;
    double iva=0;
    double cesc=0;
    double dai=0;
    
    private void mensajeOk(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mensajeError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.ERROR_MESSAGE);
    }
    
    //Encabezado detalle venta
    public void crearTabla(){
        String encabezados2[]={"Id","Articulo","Cantidad","Fecha vec.","Precio venta","IVA","CESC","Sub total","descuento","total"};
        tablaTemp = new DefaultTableModel(null, encabezados2);
        this.tblDetalleVenta.setModel(tablaTemp);
    }
    
    public void limpiar() {
        this.txtIdVenta.setText("");
        this.txtIdCliente.setText("");
        this.txtCliente.setText("");
        this.cmbComprobante.setSelectedIndex(0);
        this.txtSerie.setText("");
        this.txtCorrelativo.setText("");
        crearTabla();
    }
    
    public void limpiarDetalle(){
        this.txtIdDetalleVentaArticulo.setText("");
        this.txtArticulo.setText("");
        this.txtCantidad.setText("");
        this.txtStockInicialVenta.setText("");
        this.txtPrecioCompra.setText("");
        this.txtIva.setText("");
        this.txtCesc.setText("");
        this.txtPrecioVenta.setText("");
        this.txtPrecioVentaTotal.setText("");
        //this.txtCantidad.setEnabled(false);
        //this.txtDescuento.setEnabled(false);
    }
     public void habilitar(boolean valor){
        this.dtFecha.setEnabled(valor);
        this.dtFechaVencimiento.setEnabled(valor);
        this.cmbComprobante.setEnabled(valor);
        this.txtStockInicialVenta.setEnabled(valor);
        this.txtPrecioCompra.setEnabled(valor);
        this.btnBuscarCliente.setEnabled(valor);
        this.btnBuscarArticulo.setEnabled(valor);
        this.txtSerie.setEnabled(valor);
        this.txtCorrelativo.setEnabled(valor);
        this.btnAgregar.setEnabled(valor);
        this.txtCantidad.setEnabled(valor);
        this.txtDescuento.setEnabled(valor);
        this.btnQuitar.setEnabled(valor);
        this.btnGuardar.setEnabled(valor);
        this.btnNuevo.setEnabled(!valor);
        this.btnCancelar.setEnabled(valor);
    }
     
    public void botones(){
        if(esNuevo){
            habilitar(true);
            btnNuevo.setEnabled(false);
            btnGuardar.setEnabled(true);
            btnCancelar.setEnabled(true);
        }else{
            habilitar(false);
            btnNuevo.setEnabled(true);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }
    //Mostrar lista de cotizaciones
    public void mostrar(){
        DefaultTableModel tabla;
        Utilidades utilidades = new Utilidades();
        String encabezados[]={"Id cotizacion","Id Cliente","Cliente","Id empleado","Empleado","Fecha","Tipo comprobante","Serie","Correlativo","Eliminar"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[9];
        try{
            List lista;
            lista=jpaVenta.findVentaEntities();
            for(int i=0;i<lista.size();i++){
                venta=(Venta)lista.get(i);
                datos[0]=venta.getIdVenta();
                datos[1]=venta.getIdCliente().getIdCliente();
                datos[2]=venta.getIdCliente().getNombre();
                datos[3]=venta.getIdEmpleado().getIdEmpleado();
                datos[4]=venta.getIdEmpleado().getNombre();
                datos[5]=venta.getFecha();
                datos[6]=venta.getTipoComprobante();
                datos[7]=venta.getSerie();
                datos[8]=venta.getCorrelativo();
                tabla.addRow(datos);
            }
            this.tblListadoVentas.setModel(tabla);
            if(chkEliminar.isSelected()){
                utilidades.agregarCheckBox(9,tblListadoVentas);
            }else{
                ocultarColumnas();
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario: "+e);
        }
    }
    
    public void ocultarColumnas(){
        tblListadoVentas.removeColumn(tblListadoVentas.getColumnModel().getColumn(9));
    }
    
    //Para agregar de la tabla de detalle venta a los elementos eliminar/agregar elemento
    public void llenarIngresoDetalleVenta(){
        int fila = this.tblDetalleVenta.getSelectedRow();
        DecimalFormat decimal = new DecimalFormat ("###.00");
        DecimalFormat decimal2 = new DecimalFormat ("###.0");
        this.txtIdDetalleVentaArticulo.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 0)));
        this.txtArticulo.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 1)));
        this.txtCantidad.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 2)));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFechaVencimiento = this.tblDetalleVenta.getValueAt(fila, 9).toString();
            Date fechas;
            fechas=sdf.parse(strFechaVencimiento);
            this.dtFechaVencimiento.setDate(fechas);
        } catch (ParseException e) {}
        this.txtPrecioVenta.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 4)));
        this.txtIva.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 5)));
        this.txtCesc.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 6)));
        this.txtPrecioVentaTotal.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 7)));
        this.txtDescuento.setText(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 8)));
    }
    
    
    /**
     * Creates new form FrmVenta
     */
    public FrmVenta() {
        initComponents();
        mostrar();
        crearTabla();
        habilitar(false);
    }
    
    public FrmVenta(String nombre, String acceso, int id) {
        initComponents();
        idEmpleado = id;
        mostrar();
        crearTabla();
        habilitar(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel8 = new javax.swing.JLabel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListadoVentas = new javax.swing.JTable();
        btnComprobante = new javax.swing.JButton();
        chkEliminar = new javax.swing.JCheckBox();
        txtBuscar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdVenta = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtArticulo = new javax.swing.JTextField();
        btnBuscarArticulo = new javax.swing.JButton();
        txtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dtFechaVencimiento = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        txtStockInicialVenta = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        txtCesc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtPrecioVentaTotal = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleVenta = new javax.swing.JTable();
        cmbComprobante = new javax.swing.JComboBox<>();
        txtIdDetalleVentaArticulo = new javax.swing.JTextField();
        txtIdCliente = new javax.swing.JTextField();
        txtCliente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCorrelativo = new javax.swing.JTextField();
        dtFecha = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

        jScrollPane3.setViewportView(jTree1);

        setClosable(true);
        setIconifiable(true);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Ventas");

        jLabel1.setText("Id cotización:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tblListadoVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblListadoVentas);

        btnComprobante.setText("Comprobante");

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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComprobante)
                        .addGap(103, 103, 103))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkEliminar)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 904, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(15, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(btnComprobante))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado", jPanel1);

        jLabel2.setText("Código:");

        jLabel4.setText("Comprobante:");

        txtIdVenta.setEnabled(false);

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel6.setText("Artículo:");

        txtArticulo.setEnabled(false);

        btnBuscarArticulo.setText("Buscar");
        btnBuscarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarArticuloActionPerformed(evt);
            }
        });

        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        jLabel7.setText("Cantidad:");

        txtPrecioCompra.setEnabled(false);

        jLabel9.setText("Precio compra:");

        txtPrecioVenta.setEnabled(false);

        jLabel10.setText("Precio venta:");

        jLabel5.setText("Fecha Venc.:");

        jLabel11.setText("Descuento:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        txtStockInicialVenta.setEnabled(false);

        txtDescuento.setText("0");
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });

        txtCesc.setEnabled(false);

        jLabel17.setText("Precio venta total:");

        txtIva.setEnabled(false);

        jLabel15.setText("Iva:");

        txtPrecioVentaTotal.setEnabled(false);

        jLabel19.setText("CESC:");

        jLabel16.setText("$");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarArticulo))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtStockInicialVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(84, 84, 84)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPrecioVentaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(txtCesc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(192, 192, 192)))
                        .addGap(47, 47, 47)))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuitar)
                    .addComponent(btnAgregar))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(btnBuscarArticulo)
                            .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addComponent(jLabel5)
                        .addComponent(dtFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnAgregar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuitar)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(txtStockInicialVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrecioVentaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tblDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalleVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleVentaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetalleVenta);

        cmbComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura", "Ticket" }));

        txtIdDetalleVentaArticulo.setEnabled(false);

        txtIdCliente.setEnabled(false);

        txtCliente.setEnabled(false);

        jLabel12.setText("Cliente:");

        jLabel13.setText("Número:");

        jLabel14.setText("Fecha:");

        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });

        jLabel18.setText("Id Cliente:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnNuevo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2)
                                    .addComponent(txtIdDetalleVentaArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)))
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuscarCliente))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(49, 49, 49)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarCliente))
                    .addComponent(jLabel14)
                    .addComponent(dtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdDetalleVentaArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)
                    .addComponent(btnNuevo)))
        );

        jTabbedPane4.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkEliminarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEliminarStateChanged
        mostrar();
    }//GEN-LAST:event_chkEliminarStateChanged

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        try {
            if(vistaCliente==null){
                vistaCliente =new FrmVistaCliente();
            }
            vistaCliente.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void btnBuscarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarArticuloActionPerformed
        try {
            if(vistaVentaArticulo==null){
                vistaVentaArticulo =new FrmVentaArticulos();
            }
            vistaVentaArticulo.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarArticuloActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        try {
            for (int i=0; i < tblDetalleVenta.getRowCount(); i++) {
                String temp = tblDetalleVenta.getValueAt(i, 0).toString();
                if(temp.equals(this.txtIdDetalleVentaArticulo.getText())){
                    tablaTemp.removeRow(i); //Elimina de la lista de productos a cotizar
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto de la lista");
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            DecimalFormat decimal = new DecimalFormat ("##.00");
            DecimalFormat entero = new DecimalFormat ("##");
            if (this.txtIdDetalleVentaArticulo.getText().equals("") || this.txtArticulo.getText().equals("") || this.txtPrecioCompra.getText().equals("") ||
                dtFechaVencimiento.getDate().equals("") || this.txtCantidad.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Error, datos de articulo incompletos");
            }else{
                boolean registrar = true;
                for (int i=0; i < tblDetalleVenta.getRowCount(); i++) {
                    String temp = tblDetalleVenta.getValueAt(i, 0).toString();
                    if(temp.equals(this.txtIdDetalleVentaArticulo.getText())){
                        registrar=false;
                        JOptionPane.showMessageDialog(null, "Error, el articulo ya se encuentra en el listado");
                     }
                }
                if (registrar) {
                    Object datos[]=new Object[10];
                    try{
                        datos[0]=this.txtIdDetalleVentaArticulo.getText();
                        datos[1]=this.txtArticulo.getText();
                        int cantidad = Integer.parseInt(this.txtCantidad.getText());
                        datos[2]=cantidad;
                        Date dateVen = dtFechaVencimiento.getDate();
                        SimpleDateFormat sdfVen = new SimpleDateFormat("dd-MM-yyy");
                        datos[3]=sdfVen.format(dateVen);
                        datos[4]=this.txtPrecioVenta.getText();
                        datos[5]=this.txtIva.getText();
                        datos[6]=this.txtCesc.getText();
                        double precioVentaTotal = Double.parseDouble(txtPrecioVentaTotal.getText());
                        datos[7]=precioVentaTotal;
                        double descuento = Double.parseDouble(this.txtDescuento.getText());
                        datos[8]=descuento;
                        datos[9]=(precioVentaTotal-descuento)*cantidad;
                        tablaTemp.addRow(datos);
                        limpiarDetalle();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Error al mostrar formulario: "+e);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en agregar articulo a listado: "+e);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        esNuevo = true;
        botones();
        limpiar();
        limpiarDetalle();
        habilitar(true);
        txtSerie.isFocusable();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tblDetalleVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleVentaMouseClicked
        llenarIngresoDetalleVenta();
    }//GEN-LAST:event_tblDetalleVentaMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        esNuevo=false;
        botones();
        limpiar();
        limpiarDetalle();
        habilitar(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
       Character s = evt.getKeyChar();
       if(!Character.isDigit(s) && s !='.' ){
           
           evt.consume();
       }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
       Character s = evt.getKeyChar();
       if(!Character.isDigit(s) && s !='.' ){
           evt.consume();
       }
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            String respuesta = "";
            DecimalFormat decimal = new DecimalFormat("###,###.00");
            if(this.txtIdCliente.getText().equals("") || this.txtSerie.getText().equals("") || this.txtCorrelativo.getText().equals("")){
                mensajeError("Falta ingresar algunos datos");
            }else{
                if(esNuevo){
                    Venta venta2 = new Venta();
                    empleado.setIdEmpleado(idEmpleado);
                    cliente.setIdCliente(Integer.parseInt(this.txtIdCliente.getText().toString()));
                    venta2.setIdEmpleado(empleado);
                    venta2.setIdCliente(cliente);
                    Date date = dtFecha.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                    venta2.setFecha(sdf.format(date));
                    venta2.setTipoComprobante(this.cmbComprobante.getSelectedItem().toString());
                    venta2.setSerie(this.txtSerie.getText());
                    venta2.setCorrelativo(this.txtCorrelativo.getText());
                    jpaVenta.create(venta2);
                    for (int fila = 0; fila < tblDetalleVenta.getRowCount(); fila++) {
                        Detalleventa detalleVenta2 = new Detalleventa();
                        detalleVenta2.setIdVenta(venta2); //Prueba de ultimo dato ingresado
                        Detalleingreso detalleIngreso = new Detalleingreso();
                        detalleIngreso.setIdDetalleIngreso(Integer.parseInt(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 0))));
                        detalleVenta2.setIdDetalleIngreso(detalleIngreso);
                        detalleVenta2.setCantidad(Integer.parseInt(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 2))));
                        JOptionPane.showMessageDialog(null, "Ingreso cantidad");
                        detalleVenta2.setPrecioVenta(Double.parseDouble(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 7))));
                        JOptionPane.showMessageDialog(null, "Ingreso venta");
                        detalleVenta2.setDescuento(Double.parseDouble(String.valueOf(this.tblDetalleVenta.getValueAt(fila, 8))));
                        jpaDetalleVenta.create(detalleVenta2);
                    }
                }
                JOptionPane.showMessageDialog(null, "Ingreso completado con exitó");
                esNuevo=false;
                this.botones();
                this.limpiar();
                this.mostrar();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Mensaje de error: "+e);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarArticulo;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnComprobante;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JCheckBox chkEliminar;
    private javax.swing.JComboBox<String> cmbComprobante;
    private com.toedter.calendar.JDateChooser dtFecha;
    public static com.toedter.calendar.JDateChooser dtFechaVencimiento;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JTable tblDetalleVenta;
    private javax.swing.JTable tblListadoVentas;
    public static javax.swing.JTextField txtArticulo;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    public static javax.swing.JTextField txtCesc;
    public static javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCorrelativo;
    private javax.swing.JTextField txtDescuento;
    public static javax.swing.JTextField txtIdCliente;
    public static javax.swing.JTextField txtIdDetalleVentaArticulo;
    private javax.swing.JTextField txtIdVenta;
    public static javax.swing.JTextField txtIva;
    public static javax.swing.JTextField txtPrecioCompra;
    public static javax.swing.JTextField txtPrecioVenta;
    public static javax.swing.JTextField txtPrecioVentaTotal;
    private javax.swing.JTextField txtSerie;
    public static javax.swing.JTextField txtStockInicialVenta;
    // End of variables declaration//GEN-END:variables
}
