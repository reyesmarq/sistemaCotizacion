/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.views;

import com.views.FrmPrincipal;
import com.controller.ArticuloJpaController;
import com.controller.DetalleingresoJpaController;
import com.controller.EmpleadoJpaController;
import com.controller.IngresoJpaController;
import com.controller.ProveedorJpaController;
import com.entities.Articulo;
import com.entities.Detalleingreso;
import com.entities.Empleado;
import com.entities.Ingreso;
import com.entities.Presentacion;
import com.entities.Proveedor;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import utilidades.Utilidades;

/**
 *
 * @author Ulises
 */
public class FrmIngreso extends javax.swing.JInternalFrame {
    
    boolean esNuevo=false;
    DefaultTableModel tablaTemp;
    
    Articulo articulo = new Articulo();
    ArticuloJpaController jpaArticulo = new ArticuloJpaController();
    Proveedor proveedor = new Proveedor();
    ProveedorJpaController jpaProveedor = new ProveedorJpaController();
    Ingreso ingreso = new Ingreso();
    IngresoJpaController jpaIngreso = new IngresoJpaController();
    Detalleingreso detalleIngreso = new Detalleingreso();
    DetalleingresoJpaController jpaDetalleIngreso = new DetalleingresoJpaController();
    Empleado empleado = new Empleado();
    EmpleadoJpaController jpaEmpleado = new EmpleadoJpaController();
    
    String nombre;
    String acceso;
    int idEmpleado;
    double iva=0;
    double cesc=0;
    double dai=0;
    
    private FrmVistaProveedor vistaProveedor=null;
    private FrmVistaArticulo vistaArticulo=null; 
    
    private void mensajeOk(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mensajeError(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje + " sistema de cotización", "Respuesta", JOptionPane.ERROR_MESSAGE);
    }
    
    public void limpiar() {
        this.txtIdIngreso.setText("");
        this.txtIdProveedor.setText("");
        this.txtProveedor.setText("");
        this.cmbComprobante.setSelectedIndex(0);
        this.txtSerie.setText("");
        this.txtCorrelativo.setText("");
        crearTabla();
    }
    
    public void limpiarDetalle(){
        this.txtDai.setText("");
        this.txtIva.setText("");
        this.txtCesc.setText("");
        this.txtIdArticulo.setText("");
        this.txtArticulo.setText("");
        this.txtStockInicial.setText("");
        this.txtPrecioCompra.setText("");
        this.txtPrecioVenta.setText("");
        this.txtIva.setEnabled(false);
        this.txtDai.setEnabled(false);
        this.txtCesc.setEnabled(false);
    }
    
    public void habilitar(boolean valor){
        this.dtFecha.setEnabled(valor);
        this.dtFechaProduccion.setEnabled(valor);
        this.dtFechaVencimiento.setEnabled(valor);
        this.cmbComprobante.setEnabled(valor);
        this.txtStockInicial.setEnabled(valor);
        this.txtPrecioCompra.setEnabled(valor);
        this.btnBuscarProveedor.setEnabled(valor);
        this.btnBuscarArticulo.setEnabled(valor);
        this.txtSerie.setEnabled(valor);
        this.txtCorrelativo.setEnabled(valor);
        this.btnAgregar.setEnabled(valor);
        this.btnQuitar.setEnabled(valor);
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
    
    public void crearTabla(){
        String encabezados2[]={"Id","Articulo","Precio Compra","IVA","CESC","DAI","Precio Venta","Stock Inicial","Fecha Produccion","Fecha Vencimiento","Sub Total"};
        tablaTemp = new DefaultTableModel(null, encabezados2);
        this.tblDetalleIngreso.setModel(tablaTemp);
    }
    
    //Mostrar al arrastar el id de ingreso.
    public void mostrarDetalleIngreso(int id){
        DefaultTableModel tabla;
        DecimalFormat decimal = new DecimalFormat("###.00");
        String encabezados[]={"Id","Articulo","Precio Compra","IVA","CESC","DAI","Precio Venta","Stock Inicial","Fecha Produccion","Fecha Vencimiento","Sub Total"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[11];
        try{
            List<Detalleingreso> lista;
            lista=jpaDetalleIngreso.buscarDetalleIngreso(id);
            for(int i=0;i<lista.size();i++){
                detalleIngreso=(Detalleingreso)lista.get(i);
                datos[0]=detalleIngreso.getIdDetalleIngreso();
                datos[1]=detalleIngreso.getIdArticulo().getNombre();
                double precioCompra = detalleIngreso.getPrecioCompra();
                precioCompra = Double.parseDouble(decimal.format(precioCompra));
                datos[2]=precioCompra;
                datos[3]=detalleIngreso.getIva();
                datos[4]=detalleIngreso.getCesc();
                datos[5]=detalleIngreso.getDai();
                datos[6]=detalleIngreso.getPrecioVenta();
                double stockInicial = detalleIngreso.getStockInicial();
                datos[7]= stockInicial;
                datos[8]=detalleIngreso.getFechaProduccion();
                datos[9]=detalleIngreso.getFechaVencimiento();
                datos[10]= precioCompra*stockInicial;
                tabla.addRow(datos);
            }
            this.tblDetalleIngreso.setModel(tabla);
            if(!chkEliminar.isSelected()){
                jTabbedPane4.setSelectedIndex(1);
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario: "+e);
        }
    }
    
    //Mostrar lista de ingresos a almacen
    public void mostrar(){
        DefaultTableModel tabla;
        Utilidades utilidades = new Utilidades();
        String encabezados[]={"Id Factura","Id Prov.","Proveedor","Fecha","Tipo Comprobante","Serie","Correlativo","Eliminar"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[7];
        try{
            List lista;
            lista=jpaIngreso.findIngresoEntities();
            for(int i=0;i<lista.size();i++){
                ingreso=(Ingreso)lista.get(i);
                datos[0]=ingreso.getIdIngreso();
                datos[1]=ingreso.getIdProveedor().getIdProveedor();
                datos[2]=ingreso.getIdProveedor().getRazonSocial();
                datos[3]=ingreso.getFecha();
                datos[4]=ingreso.getTipoComprobante();
                datos[5]=ingreso.getSerie();
                datos[6]=ingreso.getCorrelativo();
                tabla.addRow(datos);
            }
            this.tblAlmacen.setModel(tabla);
            if(chkEliminar.isSelected()){
                utilidades.agregarCheckBox(7,tblAlmacen);
            }else{
                ocultarColumnas();
            }
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario: "+e);
        }
    }
    
    public void ocultarColumnas(){
        tblAlmacen.removeColumn(tblAlmacen.getColumnModel().getColumn(7));
    }
    
    //doble clic en tblDetalleIngreso para editar en apartado articulos
    public void llenarIngresoDetalle(){ 
        int fila = this.tblDetalleIngreso.getSelectedRow();
        DecimalFormat decimal = new DecimalFormat ("###.00");
        DecimalFormat decimal2 = new DecimalFormat ("###.0");
        this.txtIdArticulo.setText(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 0)));
        this.txtArticulo.setText(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 1)));
        this.txtPrecioCompra.setText(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 2)));
        double iva=0, cesc=0;
        
        if (this.tblDetalleIngreso.getValueAt(fila, 3).equals("0")) {
            this.txtIva.setText("No");
        }else{
            this.txtIva.setText("Si");
            iva=Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 3)));
        }
        
        if (this.tblDetalleIngreso.getValueAt(fila, 4).equals("0")) {
            this.txtCesc.setText("No");
        }else{
            this.txtCesc.setText("Si");
            cesc=Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 4)));
        }
        
        if (this.tblDetalleIngreso.getValueAt(fila, 5).equals("0")) {
            this.txtDai.setText("0");
        }else{
            double dai=0, precioCompra=0, precioVenta=0;
            precioCompra=Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 2)));
            precioVenta=Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 6)));
            dai = Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 5)));
            //dai = (precioVenta-precioCompra*iva*cesc-(precioCompra+iva+cesc)*0.20)/1.20;
            dai = (precioVenta/1.2)-precioCompra-iva-cesc;
            dai = (dai/precioCompra)/0.01;
            this.txtDai.setText(String.valueOf(decimal2.format(dai)));
        }
        this.txtPrecioVenta.setText(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 6)));
        this.txtStockInicial.setText(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 7)));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFechaProduccion = this.tblDetalleIngreso.getValueAt(fila, 8).toString();
            Date fechas;
            fechas=sdf.parse(strFechaProduccion);
            this.dtFechaProduccion.setDate(fechas);
        } catch (ParseException e) {}            
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFechaVencimiento = this.tblDetalleIngreso.getValueAt(fila, 9).toString();
            Date fechas;
            fechas=sdf.parse(strFechaVencimiento);
            this.dtFechaVencimiento.setDate(fechas);
        } catch (ParseException e) {}
    }
    
    /**
     * Creates new form FrmIngreso
     */
    
    public FrmIngreso() {
        initComponents();
        mostrar();
        crearTabla();
        habilitar(false);
    }

    public FrmIngreso(String nombre, String acceso, int id) {
        initComponents();
        this.nombre = nombre;
        this.acceso = acceso;
        this.idEmpleado = id;
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

        jLabel8 = new javax.swing.JLabel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAlmacen = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        dtFechaInicio = new com.toedter.calendar.JDateChooser();
        dtFechaFinal = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        chkEliminar = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtIdIngreso = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtArticulo = new javax.swing.JTextField();
        btnBuscarArticulo = new javax.swing.JButton();
        txtStockInicial = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioCompra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        dtFechaProduccion = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dtFechaVencimiento = new com.toedter.calendar.JDateChooser();
        btnAgregar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        txtIdArticulo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDai = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtIva = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCesc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleIngreso = new javax.swing.JTable();
        cmbComprobante = new javax.swing.JComboBox<>();
        txtIdProveedor = new javax.swing.JTextField();
        txtProveedor = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCorrelativo = new javax.swing.JTextField();
        dtFecha = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        btnBuscarProveedor = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("Almacén");

        jLabel1.setText("Fecha inicio:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnAnular.setText("Anular");

        tblAlmacen.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAlmacen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAlmacenMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAlmacen);

        lblTotal.setText("temporal");

        jLabel3.setText("Fecha final:");

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
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(chkEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnular)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(lblTotal)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBuscar)
                            .addComponent(btnAnular))
                        .addGap(4, 4, 4)
                        .addComponent(lblTotal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(dtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Listado", jPanel1);

        jLabel2.setText("Código:");

        jLabel4.setText("Comprobante:");

        txtIdIngreso.setEditable(false);
        txtIdIngreso.setEnabled(false);

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

        txtArticulo.setEditable(false);
        txtArticulo.setEnabled(false);

        btnBuscarArticulo.setText("Buscar");
        btnBuscarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarArticuloActionPerformed(evt);
            }
        });

        jLabel7.setText("Stock inicial:");

        jLabel9.setText("Precio compra:");

        txtPrecioVenta.setEnabled(false);

        jLabel10.setText("Precio venta:");

        jLabel5.setText("Fecha prod.:");

        jLabel11.setText("Fecha venc.:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnQuitar.setText("Eliminar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        txtIdArticulo.setEnabled(false);

        jLabel19.setText("Id Artículo:");

        txtDai.setEnabled(false);

        jLabel16.setText("DAI:");

        txtIva.setEnabled(false);

        jLabel15.setText("Iva:");

        txtCesc.setEnabled(false);

        jLabel17.setText("CESC:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStockInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarArticulo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtFechaProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dtFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnQuitar)
                            .addComponent(btnAgregar))
                        .addContainerGap(40, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDai, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCesc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(201, 201, 201))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtIdArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap(10, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(txtDai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(txtCesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(txtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(btnBuscarArticulo)
                            .addComponent(jLabel9)
                            .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel5)
                        .addComponent(dtFechaProduccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnAgregar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnQuitar)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtStockInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addComponent(jLabel11)
                        .addComponent(dtFechaVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tblDetalleIngreso.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalleIngreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleIngresoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDetalleIngreso);

        cmbComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura", "Ticket" }));

        txtIdProveedor.setEditable(false);
        txtIdProveedor.setEnabled(false);

        txtProveedor.setEditable(false);
        txtProveedor.setEnabled(false);

        jLabel12.setText("Proveedor:");

        jLabel13.setText("Número:");

        jLabel14.setText("Fecha:");

        btnBuscarProveedor.setText("Buscar");
        btnBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProveedorActionPerformed(evt);
            }
        });

        jLabel18.setText("Correlativo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnBuscarProveedor))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(69, 69, 69)
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(671, 671, 671)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtIdIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(txtProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscarProveedor))
                    .addComponent(jLabel14)
                    .addComponent(dtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCorrelativo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmbComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar)
                    .addComponent(btnNuevo))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane4)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnBuscarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarArticuloActionPerformed
        try {
            if(vistaArticulo==null){
                vistaArticulo=new FrmVistaArticulo();
            }
            vistaArticulo.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarArticuloActionPerformed

    private void tblAlmacenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlmacenMouseClicked
        limpiarDetalle();
        int fila = this.tblAlmacen.getSelectedRow();
        this.txtIdIngreso.setText(String.valueOf(this.tblAlmacen.getValueAt(fila, 0)));
        this.txtIdProveedor.setText(String.valueOf(this.tblAlmacen.getValueAt(fila, 1)));
        this.txtProveedor.setText(String.valueOf(this.tblAlmacen.getValueAt(fila, 2)));
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFecha = this.tblAlmacen.getValueAt(fila, 3).toString();
            Date fechas;
            fechas=sdf.parse(strFecha);
            this.dtFecha.setDate(fechas);
        }catch(ParseException e){}
        this.cmbComprobante.setSelectedItem(String.valueOf(this.tblAlmacen.getValueAt(fila, 4)));
        this.txtSerie.setText(String.valueOf(this.tblAlmacen.getValueAt(fila, 5)));
        this.txtCorrelativo.setText(String.valueOf(this.tblAlmacen.getValueAt(fila, 6)));
        mostrarDetalleIngreso(Integer.parseInt(this.txtIdIngreso.getText()));
    }//GEN-LAST:event_tblAlmacenMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        esNuevo = true;
        botones();
        limpiar();
        limpiarDetalle();
        habilitar(true);
        txtSerie.isFocusable();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        esNuevo=false;
        botones();
        limpiar();
        limpiarDetalle();
        habilitar(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void tblDetalleIngresoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleIngresoMouseClicked
        llenarIngresoDetalle();
    }//GEN-LAST:event_tblDetalleIngresoMouseClicked

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        try {
            for (int i=0; i < tblDetalleIngreso.getRowCount(); i++) {
                String temp = tblDetalleIngreso.getValueAt(i, 0).toString();
                if(temp.equals(this.txtIdArticulo.getText())){
                    tablaTemp.removeRow(i);
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
            if (this.txtIdArticulo.getText().equals("") || this.txtArticulo.getText().equals("") || this.txtPrecioCompra.getText().equals("") ||
                 dtFechaProduccion.getDate().equals("") || dtFechaVencimiento.getDate().equals("")) {
                JOptionPane.showMessageDialog(null, "Error, datos de articulo incompleto");
            }else{
                boolean registrar = true;
                for (int i=0; i < tblDetalleIngreso.getRowCount(); i++) {
                    String temp = tblDetalleIngreso.getValueAt(i, 0).toString();
                    if(temp.equals(this.txtIdArticulo.getText())){
                        registrar=false;
                        JOptionPane.showMessageDialog(null, "Error, el articulo ya se encuentra en el listado");
                     }
                }
                if (registrar) {
                    Object datos[]=new Object[11];
                    try{
                        datos[0]=this.txtIdArticulo.getText();
                        datos[1]=this.txtArticulo.getText();
                        double precioCompra = Double.parseDouble(this.txtPrecioCompra.getText());
                        datos[2]= decimal.format(precioCompra);
                        if (this.txtIva.getText().equals("Si")) {
                            iva=precioCompra*0.13;
                            datos[3]=decimal.format (iva);
                        }else{
                            iva=0;
                            datos[3]=0;
                        }
                        if (this.txtCesc.getText().equals("Si")) {
                            cesc=precioCompra*0.05;
                            datos[4]=decimal.format (cesc);
                        }else{
                            datos[4]=0;
                            cesc=0;
                        }
                        if (!this.txtDai.getText().equals("")) {
                            dai=Double.parseDouble(txtDai.getText().toString());
                            dai=dai*0.01;
                            dai=precioCompra*dai;
                            datos[5]=decimal.format(dai);
                        }else{
                            datos[5]=0;
                        }
                        double precioVenta=(precioCompra + iva + cesc + dai)*1.20;
                        datos[6]=decimal.format(precioVenta);
                        double stockInicial = Double.parseDouble(this.txtStockInicial.getText());
                        datos[7]= entero.format(stockInicial);
                        Date date = dtFechaProduccion.getDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                        datos[8]=sdf.format(date);
                        Date dateVen = dtFechaVencimiento.getDate();
                        SimpleDateFormat sdfVen = new SimpleDateFormat("dd-MM-yyy");
                        datos[9]=sdfVen.format(dateVen);
                        datos[10]= decimal.format (precioVenta*stockInicial);
                        tablaTemp.addRow(datos);
                        limpiarDetalle();
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null,"Error al mostrar formulario: "+e);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en agregar articulo a listado");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProveedorActionPerformed
        try {
            if(vistaProveedor==null){
                vistaProveedor=new FrmVistaProveedor();
            }
            vistaProveedor.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarProveedorActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            String respuesta = "";
            DecimalFormat decimal = new DecimalFormat("###,###.00");
            if(this.txtIdProveedor.getText().equals("") || this.txtSerie.getText().equals("") || this.txtCorrelativo.getText().equals("")){
                mensajeError("Falta ingresar algunos datos");
                this.txtProveedor.setFocusable(true);
            }else{
                if(esNuevo){
                    Ingreso ingreso2 = new Ingreso();
                    empleado.setIdEmpleado(idEmpleado);
                    proveedor.setIdProveedor(Integer.parseInt(this.txtIdProveedor.getText().toString()));
                    ingreso2.setIdEmpleado(empleado);
                    ingreso2.setIdProveedor(proveedor);
                    Date date = dtFecha.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                    ingreso2.setFecha(sdf.format(date));
                    ingreso2.setTipoComprobante(this.cmbComprobante.getSelectedItem().toString());
                    ingreso2.setSerie(this.txtSerie.getText());
                    ingreso2.setCorrelativo(this.txtCorrelativo.getText());
                    jpaIngreso.create(ingreso2);
                    for (int fila = 0; fila < tblDetalleIngreso.getRowCount(); fila++) {
                        Detalleingreso detalleIngreso2 = new Detalleingreso();
                        detalleIngreso2.setIdIngreso(ingreso2); //Prueba de ultimo dato ingresado
                         articulo.setIdArticulo(Integer.parseInt(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 0)).toString()));
                        detalleIngreso2.setIdArticulo(articulo);
                        detalleIngreso2.setPrecioCompra(Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 2)).toString()));
                         double iva=0, cesc=0;
                        detalleIngreso2.setIva(Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 3))));
                        detalleIngreso2.setCesc(Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 4))));
                        detalleIngreso2.setDai(Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 5))));
                        detalleIngreso2.setPrecioVenta(Double.parseDouble(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 6))));
                        detalleIngreso2.setStockInicial(Integer.parseInt(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 7))));
                        detalleIngreso2.setStockActual(Integer.parseInt(String.valueOf(this.tblDetalleIngreso.getValueAt(fila, 7))));
                         date = dtFechaProduccion.getDate();
                        detalleIngreso2.setFechaProduccion(sdf.format(date));
                         date = dtFechaVencimiento.getDate();
                        detalleIngreso2.setFechaVencimiento(sdf.format(date));
                        jpaDetalleIngreso.create(detalleIngreso2);
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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarArticulo;
    private javax.swing.JButton btnBuscarProveedor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JCheckBox chkEliminar;
    private javax.swing.JComboBox<String> cmbComprobante;
    private com.toedter.calendar.JDateChooser dtFecha;
    private com.toedter.calendar.JDateChooser dtFechaFinal;
    private com.toedter.calendar.JDateChooser dtFechaInicio;
    private com.toedter.calendar.JDateChooser dtFechaProduccion;
    private com.toedter.calendar.JDateChooser dtFechaVencimiento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblAlmacen;
    private javax.swing.JTable tblDetalleIngreso;
    public static javax.swing.JTextField txtArticulo;
    public static javax.swing.JTextField txtCesc;
    private javax.swing.JTextField txtCorrelativo;
    public static javax.swing.JTextField txtDai;
    public static javax.swing.JTextField txtIdArticulo;
    private javax.swing.JTextField txtIdIngreso;
    public static javax.swing.JTextField txtIdProveedor;
    public static javax.swing.JTextField txtIva;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    public static javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtStockInicial;
    // End of variables declaration//GEN-END:variables
}
