/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.views;

import com.controller.DetalleingresoJpaController;
import com.entities.Detalleingreso;
import com.entities.Detalleingreso_;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.Utilidades;

/**
 *  Nombre de la clase: FrmVentaArticulos
 *  Fecha: 11-19-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class FrmVentaArticulos extends javax.swing.JFrame {

    Detalleingreso articulo = new Detalleingreso();
    DetalleingresoJpaController jpaDetalle = new DetalleingresoJpaController();
    
    /** Creates new form FrmVentaArticulos */
    public FrmVentaArticulos() {
        initComponents();
        mostrar();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtBuscar1 = new javax.swing.JTextPane();
        btnBuscar1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblArticuloBodega = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Nombre:");

        jScrollPane3.setViewportView(txtBuscar1);

        btnBuscar1.setText("Buscar");
        btnBuscar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscar1MouseClicked(evt);
            }
        });

        tblArticuloBodega.setModel(new javax.swing.table.DefaultTableModel(
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
        tblArticuloBodega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArticuloBodegaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblArticuloBodega);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane5.addTab("Listado", jPanel2);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 30)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("Artículos en venta");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscar1MouseClicked
        buscarNombre();
        
    }//GEN-LAST:event_btnBuscar1MouseClicked

    private void tblArticuloBodegaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArticuloBodegaMouseClicked
        int fila = this.tblArticuloBodega.getSelectedRow();
        DecimalFormat decimal2 = new DecimalFormat ("##0.00");
        FrmVenta.txtIdDetalleVentaArticulo.setText(String.valueOf(this.tblArticuloBodega.getValueAt(fila, 0)));
        FrmVenta.txtArticulo.setText(String.valueOf(this.tblArticuloBodega.getValueAt(fila, 3)));
        FrmVenta.txtStockInicialVenta.setText(String.valueOf(this.tblArticuloBodega.getValueAt(fila, 6)));
        double precioCompra=Double.parseDouble(String.valueOf(this.tblArticuloBodega.getValueAt(fila, 7)));
        double iva =Double.parseDouble(this.tblArticuloBodega.getValueAt(fila, 11).toString());
        double cesc =Double.parseDouble(this.tblArticuloBodega.getValueAt(fila, 12).toString());
        double dai =Double.parseDouble(this.tblArticuloBodega.getValueAt(fila, 13).toString());
        FrmVenta.txtPrecioCompra.setText(String.valueOf(precioCompra+iva+cesc+dai));
        double precioVenta=Double.parseDouble(this.tblArticuloBodega.getValueAt(fila, 8).toString());
        FrmVenta.txtPrecioVenta.setText(String.valueOf(precioVenta));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String strFechaVencimiento = this.tblArticuloBodega.getValueAt(fila, 9).toString();
            Date fecha;
            fecha=sdf.parse(strFechaVencimiento);
            FrmVenta.dtFechaVencimiento.setDate(fecha);
        } catch (ParseException e) {}
        double precioIva = precioVenta*0.13;
        FrmVenta.txtIva.setText(String.valueOf(decimal2.format(precioIva)));
        double precioCesc = Double.parseDouble(this.tblArticuloBodega.getValueAt(fila, 10).toString());
        if (precioCesc==1 || precioCesc == 1.0) {
            precioCesc=precioVenta*0.05;
        }else{
            precioCesc=0;
        }
        FrmVenta.txtCesc.setText(String.valueOf(decimal2.format(precioCesc)));
        precioVenta=precioVenta+precioIva+precioCesc;
        FrmVenta.txtPrecioVentaTotal.setText(String.valueOf(decimal2.format(precioVenta)));
        this.dispose();
    }//GEN-LAST:event_tblArticuloBodegaMouseClicked

    public void buscarNombre(){
        
    }
    
    public void llenarFormulario(){
        
    }
    
    public void mostrar(){
        DefaultTableModel tabla;
        String temp;
        Utilidades utilidades = new Utilidades();
        String encabezados[]={"Id det.","id Art.","Codigo","Nombre","Categoria","Presentacion","Stock actual","Precio compra","Precio venta","Fecha venc.","c","i","ce","d"};
        tabla=new DefaultTableModel(null,encabezados);
        Object datos[]=new Object[14];
        try{
            List lista;
            lista=jpaDetalle.listaArticulosAlmacen();
            double cesc=0;
            for(int i=0;i<lista.size();i++){
                articulo=(Detalleingreso)lista.get(i);
                datos[0]=articulo.getIdDetalleIngreso();
                datos[1]=articulo.getIdArticulo().getIdArticulo();
                datos[2]=articulo.getIdArticulo().getCodigo();
                datos[3]=articulo.getIdArticulo().getNombre();
                datos[4]=articulo.getIdArticulo().getIdCategoria().getNombre();
                datos[5]=articulo.getIdArticulo().getIdPresentacion().getNombre();
                datos[6]=articulo.getStockActual();
                datos[7]=articulo.getPrecioCompra();
                datos[8]=articulo.getPrecioVenta();
                datos[9]=articulo.getFechaVencimiento();
                datos[10]=articulo.getIdArticulo().getIdCategoria().getCesc(); //Valida si al producto aplica el cesc
                datos[11]=articulo.getIva();
                datos[12]=articulo.getCesc();
                datos[13]=articulo.getDai();
                tabla.addRow(datos);
            }
            this.tblArticuloBodega.setModel(tabla);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error al mostrar formulario FrmVentaArticulo : "+e);
        }
    }
    
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
            java.util.logging.Logger.getLogger(FrmVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVentaArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVentaArticulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTable tblArticuloBodega;
    private javax.swing.JTextPane txtBuscar1;
    // End of variables declaration//GEN-END:variables

}
