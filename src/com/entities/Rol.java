/*
 * Nombre de Entidad: Rol
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
 */
package com.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dguevara
 */
@Entity
@Table(catalog = "cotizacionEmpresa", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombreRol", "descripcionRol"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")
    , @NamedQuery(name = "Rol.findByCodigoRol", query = "SELECT r FROM Rol r WHERE r.codigoRol = :codigoRol")
    , @NamedQuery(name = "Rol.findByNombreRol", query = "SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol")
    , @NamedQuery(name = "Rol.existRol", query = "SELECT count(u.codigoRol) FROM Rol u WHERE u.descripcionRol = :descripcionRol and u.nombreRol = :nombreRol")
    , @NamedQuery(name = "Rol.dependRolUsuario", query = "SELECT count(u.codigoRolUsuario) FROM Rolusuario u WHERE u.codigoRol.codigoRol=:codigoRol")
    , @NamedQuery(name = "Rol.dependRolPermiso", query = "SELECT count(u.codigoRolPermiso) FROM Rolpermiso u WHERE u.codigoRol.codigoRol=:codigoRol")
    , @NamedQuery(name = "Rol.findByDescripcionRol", query = "SELECT r FROM Rol r WHERE r.descripcionRol = :descripcionRol")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoRol;
    @Column(length = 50)
    private String nombreRol;
    @Column(length = 200)
    private String descripcionRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoRol")
    private List<Rolpermiso> rolpermisoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoRol")
    private List<Rolusuario> rolusuarioList;

    public Rol() {
    }

    public Rol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Integer getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Integer codigoRol) {
        this.codigoRol = codigoRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    @XmlTransient
    public List<Rolpermiso> getRolpermisoList() {
        return rolpermisoList;
    }

    public void setRolpermisoList(List<Rolpermiso> rolpermisoList) {
        this.rolpermisoList = rolpermisoList;
    }

    @XmlTransient
    public List<Rolusuario> getRolusuarioList() {
        return rolusuarioList;
    }

    public void setRolusuarioList(List<Rolusuario> rolusuarioList) {
        this.rolusuarioList = rolusuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRol != null ? codigoRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.codigoRol == null && other.codigoRol != null) || (this.codigoRol != null && !this.codigoRol.equals(other.codigoRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Rol[ codigoRol=" + codigoRol + " ]";
    }
    
}
