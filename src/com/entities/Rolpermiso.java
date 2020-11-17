/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Nombre de la clase: Rolpermiso
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
@Entity
@Table(catalog = "cotizacionEmpresa", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigoRol", "codigoPermiso"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolpermiso.findAll", query = "SELECT r FROM Rolpermiso r")
    , @NamedQuery(name = "Rolpermiso.findByCodigoRolPermiso", query = "SELECT r FROM Rolpermiso r WHERE r.codigoRolPermiso = :codigoRolPermiso")})
public class Rolpermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoRolPermiso;
    @JoinColumn(name = "codigoRol", referencedColumnName = "codigoRol", nullable = false)
    @ManyToOne(optional = false)
    private Rol codigoRol;
    @JoinColumn(name = "codigoPermiso", referencedColumnName = "codigoPermiso", nullable = false)
    @ManyToOne(optional = false)
    private Permiso codigoPermiso;

    public Rolpermiso() {
    }

    public Rolpermiso(Integer codigoRolPermiso) {
        this.codigoRolPermiso = codigoRolPermiso;
    }

    public Integer getCodigoRolPermiso() {
        return codigoRolPermiso;
    }

    public void setCodigoRolPermiso(Integer codigoRolPermiso) {
        this.codigoRolPermiso = codigoRolPermiso;
    }

    public Rol getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Rol codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Permiso getCodigoPermiso() {
        return codigoPermiso;
    }

    public void setCodigoPermiso(Permiso codigoPermiso) {
        this.codigoPermiso = codigoPermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRolPermiso != null ? codigoRolPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolpermiso)) {
            return false;
        }
        Rolpermiso other = (Rolpermiso) object;
        if ((this.codigoRolPermiso == null && other.codigoRolPermiso != null) || (this.codigoRolPermiso != null && !this.codigoRolPermiso.equals(other.codigoRolPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Rolpermiso[ codigoRolPermiso=" + codigoRolPermiso + " ]";
    }
    
}
