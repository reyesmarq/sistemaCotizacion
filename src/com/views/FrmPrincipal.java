/*
* Nombre de Formulario: FrmPrincipal
 * Fecha: 13/10/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.views;


import com.conexion.Conexion;
import com.entities.Empleado;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import utilidades.ComunicacionAcceso;
/**
 *
 * @author dguevara
 */
public class FrmPrincipal extends javax.swing.JFrame {
    
    private List<String> listaAcceso = new ArrayList<String>();
    private List<String> listaNombre = new ArrayList<String>();
    
    private FrmAcercaDe acercade=null;
    private FrmCategoria categoria = null;
    private FrmPresentacion presentacion = null;
    private FrmArticulos articulo = null;
    private FrmIngreso ingreso = null;
    private FrmProveedor proveedor = null;
    private FrmVenta venta = null;
    private FrmCliente cliente = null;
    private FrmTrabajador trabajador = null;
    private FrmIngreso ingresos = null;
    private ComunicacionAcceso datos = new ComunicacionAcceso();
    Empleado temp = new Empleado();
    int idTrabajador=0;
    String nombre="";
    String acceso="";
    
    
    
    private void gestionarUsuario(){
        if (acceso.equals("Administrador")) {
            this.mnuAlmacen.setVisible(true);
            this.mnuCompras.setVisible(true);
            this.mnuVentas.setVisible(true);
            this.mnuConsultas.setVisible(true);
            this.mnuMantenimiento.setVisible(true);
        }else if(acceso.equals("Vendedor")){
            this.mnuAlmacen.setVisible(false);
            this.mnuCompras.setVisible(false);
            this.mnuVentas.setVisible(true);
            this.mnuConsultas.setVisible(false);
            this.mnuMantenimiento.setVisible(false);
        }else if(acceso.equals("Bodeguero")){
            this.mnuAlmacen.setVisible(true);
            this.mnuCompras.setVisible(true);
            this.mnuVentas.setVisible(false);
            this.mnuConsultas.setVisible(false);
            this.mnuMantenimiento.setVisible(false);
        }
    }
    
    /**
     * Creates new form FrmPrincipal
     */
    public FrmPrincipal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        gestionarUsuario();
    }
    
    public FrmPrincipal(List<String> accesoL, List<String> nombreL, int id){
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        listaAcceso=accesoL;
        listaNombre=nombreL;
        nombre=listaNombre.get(0);
        acceso=listaAcceso.get(0);
        idTrabajador=id;
        gestionarUsuario();
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        sistemaMenu = new javax.swing.JMenu();
        acercaDeMenuItem = new javax.swing.JMenuItem();
        salirMenuItem = new javax.swing.JMenuItem();
        mnuAlmacen = new javax.swing.JMenu();
        articulosMenuItem = new javax.swing.JMenuItem();
        categoriaMenuItem = new javax.swing.JMenuItem();
        presentacionMenuItem = new javax.swing.JMenuItem();
        mnuCompras = new javax.swing.JMenu();
        ingresosMenuItem = new javax.swing.JMenuItem();
        preevodoresMenuItem = new javax.swing.JMenuItem();
        mnuVentas = new javax.swing.JMenu();
        ventasMenuItem = new javax.swing.JMenuItem();
        clientesMenuItem = new javax.swing.JMenuItem();
        mnuMantenimiento = new javax.swing.JMenu();
        empleado = new javax.swing.JMenuItem();
        mnuConsultas = new javax.swing.JMenu();
        reportesClientes = new javax.swing.JMenuItem();
        reportesProveedores = new javax.swing.JMenuItem();
        reportesArticulos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));
        setState(getExtendedState());

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, Short.MAX_VALUE, Short.MAX_VALUE)
        );

        sistemaMenu.setMnemonic('f');
        sistemaMenu.setText("Sistema");

        acercaDeMenuItem.setText("Acerca de");
        acercaDeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaDeMenuItemActionPerformed(evt);
            }
        });
        sistemaMenu.add(acercaDeMenuItem);

        salirMenuItem.setMnemonic('x');
        salirMenuItem.setText("Salir");
        salirMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salirMenuItemMouseClicked(evt);
            }
        });
        salirMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirMenuItemActionPerformed(evt);
            }
        });
        sistemaMenu.add(salirMenuItem);

        menuBar.add(sistemaMenu);

        mnuAlmacen.setMnemonic('e');
        mnuAlmacen.setText("Almacen");

        articulosMenuItem.setMnemonic('t');
        articulosMenuItem.setText("Articulos");
        articulosMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                articulosMenuItemActionPerformed(evt);
            }
        });
        mnuAlmacen.add(articulosMenuItem);

        categoriaMenuItem.setMnemonic('y');
        categoriaMenuItem.setText("Categoria");
        categoriaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriaMenuItemActionPerformed(evt);
            }
        });
        mnuAlmacen.add(categoriaMenuItem);

        presentacionMenuItem.setMnemonic('p');
        presentacionMenuItem.setText("Presentacion");
        presentacionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                presentacionMenuItemActionPerformed(evt);
            }
        });
        mnuAlmacen.add(presentacionMenuItem);

        menuBar.add(mnuAlmacen);

        mnuCompras.setMnemonic('h');
        mnuCompras.setText("Compras");

        ingresosMenuItem.setMnemonic('c');
        ingresosMenuItem.setText("Ingresos");
        ingresosMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresosMenuItemActionPerformed(evt);
            }
        });
        mnuCompras.add(ingresosMenuItem);

        preevodoresMenuItem.setMnemonic('a');
        preevodoresMenuItem.setText("Proveedores");
        preevodoresMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                preevodoresMenuItemActionPerformed(evt);
            }
        });
        mnuCompras.add(preevodoresMenuItem);

        menuBar.add(mnuCompras);

        mnuVentas.setText("Ventas");

        ventasMenuItem.setText("Ventas");
        ventasMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventasMenuItemActionPerformed(evt);
            }
        });
        mnuVentas.add(ventasMenuItem);

        clientesMenuItem.setText("Clientes");
        clientesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientesMenuItemActionPerformed(evt);
            }
        });
        mnuVentas.add(clientesMenuItem);

        menuBar.add(mnuVentas);

        mnuMantenimiento.setText("Mantenimiento");

        empleado.setText("Empleados");
        empleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empleadoActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(empleado);

        menuBar.add(mnuMantenimiento);

        mnuConsultas.setText("Consultas");

        reportesClientes.setText("Clientes");
        reportesClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportesClientesActionPerformed(evt);
            }
        });
        mnuConsultas.add(reportesClientes);

        reportesProveedores.setText("Proveedores");
        mnuConsultas.add(reportesProveedores);

        reportesArticulos.setText("Articulos");
        mnuConsultas.add(reportesArticulos);

        menuBar.add(mnuConsultas);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acercaDeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaDeMenuItemActionPerformed
        try {
            if(acercade==null || acercade.isClosed()){
                acercade=new FrmAcercaDe();
                this.jDesktopPane1.add(acercade);
            }
            acercade.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_acercaDeMenuItemActionPerformed

    private void articulosMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_articulosMenuItemActionPerformed
        try {
            if(articulo==null || articulo.isClosed()){
                articulo=new FrmArticulos(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(articulo);
            }
            articulo.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_articulosMenuItemActionPerformed

    private void categoriaMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriaMenuItemActionPerformed
        try {
            if(categoria==null || categoria.isClosed()){
                categoria=new FrmCategoria(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(categoria);
            }
            categoria.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_categoriaMenuItemActionPerformed

    private void presentacionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_presentacionMenuItemActionPerformed
        try {
            if(presentacion==null || presentacion.isClosed()){
                presentacion=new FrmPresentacion(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(presentacion);
            }
            presentacion.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_presentacionMenuItemActionPerformed

    private void ingresosMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresosMenuItemActionPerformed
        try{
            if(ingresos==null || ingresos.isClosed()){
                ingresos=new FrmIngreso(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(ingresos);
            }
            ingresos.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ingresosMenuItemActionPerformed

    private void preevodoresMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_preevodoresMenuItemActionPerformed
        try {
            if(proveedor==null || proveedor.isClosed()){
                proveedor=new FrmProveedor(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(proveedor);
            }
            proveedor.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_preevodoresMenuItemActionPerformed

    private void ventasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ventasMenuItemActionPerformed
        try {
            if(venta==null || venta.isClosed()){
                venta=new FrmVenta(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(venta);
            }
            venta.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ventasMenuItemActionPerformed

    private void clientesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientesMenuItemActionPerformed
        try {
            if(cliente==null || cliente.isClosed()){
                cliente=new FrmCliente(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(cliente);
            }
            cliente.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_clientesMenuItemActionPerformed

    private void salirMenuItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salirMenuItemMouseClicked
        System.exit(0);
    }//GEN-LAST:event_salirMenuItemMouseClicked

    private void salirMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_salirMenuItemActionPerformed

    private void empleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empleadoActionPerformed
        try {
            if(trabajador==null || trabajador.isClosed()){
                trabajador=new FrmTrabajador(nombre, acceso, idTrabajador);
                this.jDesktopPane1.add(trabajador);
            }
            trabajador.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(FrmPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_empleadoActionPerformed

    private void reportesClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportesClientesActionPerformed
        Conexion con = new Conexion();
        JasperReport reporte;
        
        try {
            con.conectar();
            reporte = JasperCompileManager.compileReport("src/reportes/clientes.jrxml");
            JasperPrint jp = JasperFillManager.fillReport(reporte, null, con.getCon());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_reportesClientesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem acercaDeMenuItem;
    private javax.swing.JMenuItem articulosMenuItem;
    private javax.swing.JMenuItem categoriaMenuItem;
    private javax.swing.JMenuItem clientesMenuItem;
    private javax.swing.JMenuItem empleado;
    private javax.swing.JMenuItem ingresosMenuItem;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu mnuAlmacen;
    private javax.swing.JMenu mnuCompras;
    private javax.swing.JMenu mnuConsultas;
    private javax.swing.JMenu mnuMantenimiento;
    private javax.swing.JMenu mnuVentas;
    private javax.swing.JMenuItem preevodoresMenuItem;
    private javax.swing.JMenuItem presentacionMenuItem;
    private javax.swing.JMenuItem reportesArticulos;
    private javax.swing.JMenuItem reportesClientes;
    private javax.swing.JMenuItem reportesProveedores;
    private javax.swing.JMenuItem salirMenuItem;
    private javax.swing.JMenu sistemaMenu;
    private javax.swing.JMenuItem ventasMenuItem;
    // End of variables declaration//GEN-END:variables
}
