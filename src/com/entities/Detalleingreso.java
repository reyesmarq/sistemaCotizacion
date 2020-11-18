/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *  Nombre de la clase: Detalleingreso
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
@Entity
@Table(catalog = "empresacotizacionmark1", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Detalleingreso.findAll", query = "SELECT d FROM Detalleingreso d")
    , @NamedQuery(name = "Detalleingreso.findByIdDetalleIngreso", query = "SELECT d FROM Detalleingreso d WHERE d.idDetalleIngreso = :idDetalleIngreso")
    , @NamedQuery(name = "Detalleingreso.findByPrecioCompra", query = "SELECT d FROM Detalleingreso d WHERE d.precioCompra = :precioCompra")
    , @NamedQuery(name = "Detalleingreso.findByPrecioVenta", query = "SELECT d FROM Detalleingreso d WHERE d.precioVenta = :precioVenta")
    , @NamedQuery(name = "Detalleingreso.findByFechaProduccion", query = "SELECT d FROM Detalleingreso d WHERE d.fechaProduccion = :fechaProduccion")
    , @NamedQuery(name = "Detalleingreso.findByFechaVencimiento", query = "SELECT d FROM Detalleingreso d WHERE d.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "Detalleingreso.findByStockInicial", query = "SELECT d FROM Detalleingreso d WHERE d.stockInicial = :stockInicial")
    , @NamedQuery(name = "Detalleingreso.findByStockActual", query = "SELECT d FROM Detalleingreso d WHERE d.stockActual = :stockActual")
    , @NamedQuery(name = "Detalleingreso.findByEstado", query = "SELECT d FROM Detalleingreso d WHERE d.estado = :estado")
    , @NamedQuery(name = "Detalleingreso.findByDai", query = "SELECT d FROM Detalleingreso d WHERE d.dai = :dai")
    , @NamedQuery(name = "Detalleingreso.findByCesc", query = "SELECT d FROM Detalleingreso d WHERE d.cesc = :cesc")
    , @NamedQuery(name = "Detalleingreso.findByIva", query = "SELECT d FROM Detalleingreso d WHERE d.iva = :iva")})
public class Detalleingreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idDetalleIngreso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double precioCompra;
    @Column(precision = 22)
    private Double precioVenta;
    @Column(length = 50)
    private String fechaProduccion;
    @Column(length = 50)
    private String fechaVencimiento;
    private Integer stockInicial;
    private Integer stockActual;
    private Integer estado;
    @Column(precision = 22)
    private Double dai;
    @Column(precision = 22)
    private Double cesc;
    @Column(precision = 22)
    private Double iva;
    @OneToMany(mappedBy = "idDetalleIngreso")
    private List<Detalleventa> detalleventaList;
    @JoinColumn(name = "idIngreso", referencedColumnName = "idIngreso")
    @ManyToOne
    private Ingreso idIngreso;
    @JoinColumn(name = "idArticulo", referencedColumnName = "idArticulo")
    @ManyToOne
    private Articulo idArticulo;

    public Detalleingreso() {
    }

    public Detalleingreso(Integer idDetalleIngreso) {
        this.idDetalleIngreso = idDetalleIngreso;
    }

    public Integer getIdDetalleIngreso() {
        return idDetalleIngreso;
    }

    public void setIdDetalleIngreso(Integer idDetalleIngreso) {
        this.idDetalleIngreso = idDetalleIngreso;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(String fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Double getDai() {
        return dai;
    }

    public void setDai(Double dai) {
        this.dai = dai;
    }

    public Double getCesc() {
        return cesc;
    }

    public void setCesc(Double cesc) {
        this.cesc = cesc;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    @XmlTransient
    public List<Detalleventa> getDetalleventaList() {
        return detalleventaList;
    }

    public void setDetalleventaList(List<Detalleventa> detalleventaList) {
        this.detalleventaList = detalleventaList;
    }

    public Ingreso getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(Ingreso idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleIngreso != null ? idDetalleIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Detalleingreso)) {
            return false;
        }
        Detalleingreso other = (Detalleingreso) object;
        if ((this.idDetalleIngreso == null && other.idDetalleIngreso != null) || (this.idDetalleIngreso != null && !this.idDetalleIngreso.equals(other.idDetalleIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Detalleingreso[ idDetalleIngreso=" + idDetalleIngreso + " ]";
    }
    
}
