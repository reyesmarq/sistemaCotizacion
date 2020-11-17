/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *  Nombre de la clase: Empleado
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
@Entity
@Table(catalog = "cotizacionEmpresa", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")
    , @NamedQuery(name = "Empleado.findByCodigoEmpleado", query = "SELECT e FROM Empleado e WHERE e.codigoEmpleado = :codigoEmpleado")
    , @NamedQuery(name = "Empleado.findByNombreEmpleado", query = "SELECT e FROM Empleado e WHERE e.nombreEmpleado = :nombreEmpleado")
    , @NamedQuery(name = "Empleado.findByDuiEmpleado", query = "SELECT e FROM Empleado e WHERE e.duiEmpleado = :duiEmpleado")
    , @NamedQuery(name = "Empleado.findByNitEmpleado", query = "SELECT e FROM Empleado e WHERE e.nitEmpleado = :nitEmpleado")
    , @NamedQuery(name = "Empleado.findByFechaNacimientoEmpleado", query = "SELECT e FROM Empleado e WHERE e.fechaNacimientoEmpleado = :fechaNacimientoEmpleado")
    , @NamedQuery(name = "Empleado.findByCargoEmpleado", query = "SELECT e FROM Empleado e WHERE e.cargoEmpleado = :cargoEmpleado")
    , @NamedQuery(name = "Empleado.findByDireccionEmpleado", query = "SELECT e FROM Empleado e WHERE e.direccionEmpleado = :direccionEmpleado")
    , @NamedQuery(name = "Empleado.findByTelefonoEmpleado", query = "SELECT e FROM Empleado e WHERE e.telefonoEmpleado = :telefonoEmpleado")})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoEmpleado;
    @Column(length = 100)
    private String nombreEmpleado;
    @Column(length = 10)
    private String duiEmpleado;
    @Column(length = 20)
    private String nitEmpleado;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimientoEmpleado;
    @Column(length = 50)
    private String cargoEmpleado;
    @Column(length = 200)
    private String direccionEmpleado;
    @Column(length = 10)
    private String telefonoEmpleado;
    @OneToMany(mappedBy = "codigoEmpleado")
    private List<Venta> ventaList;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario")
    @ManyToOne
    private Usuario codigoUsuario;
    @OneToMany(mappedBy = "codigoEmpleado")
    private List<Ingreso> ingresoList;

    public Empleado() {
    }

    public Empleado(Integer codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Integer getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(Integer codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getDuiEmpleado() {
        return duiEmpleado;
    }

    public void setDuiEmpleado(String duiEmpleado) {
        this.duiEmpleado = duiEmpleado;
    }

    public String getNitEmpleado() {
        return nitEmpleado;
    }

    public void setNitEmpleado(String nitEmpleado) {
        this.nitEmpleado = nitEmpleado;
    }

    public Date getFechaNacimientoEmpleado() {
        return fechaNacimientoEmpleado;
    }

    public void setFechaNacimientoEmpleado(Date fechaNacimientoEmpleado) {
        this.fechaNacimientoEmpleado = fechaNacimientoEmpleado;
    }

    public String getCargoEmpleado() {
        return cargoEmpleado;
    }

    public void setCargoEmpleado(String cargoEmpleado) {
        this.cargoEmpleado = cargoEmpleado;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    @XmlTransient
    public List<Ingreso> getIngresoList() {
        return ingresoList;
    }

    public void setIngresoList(List<Ingreso> ingresoList) {
        this.ingresoList = ingresoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoEmpleado != null ? codigoEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.codigoEmpleado == null && other.codigoEmpleado != null) || (this.codigoEmpleado != null && !this.codigoEmpleado.equals(other.codigoEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Empleado[ codigoEmpleado=" + codigoEmpleado + " ]";
    }
    
}
