/*
 * Nombre de Entidad: RolUsuario
 * Fecha: 16/11/2020
 * @author Diego Guevara
 * Version: 1.0
 * CopyRight: Diego Guevara
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
 *
 * @author dguevara
 */
@Entity
@Table(catalog = "cotizacionEmpresa", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"codigoRol", "codigoUsuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolusuario.findAll", query = "SELECT r FROM Rolusuario r")
    , @NamedQuery(name = "Rolusuario.findByCodigoRolUsuario", query = "SELECT r FROM Rolusuario r WHERE r.codigoRolUsuario = :codigoRolUsuario")})
public class Rolusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoRolUsuario;
    @JoinColumn(name = "codigoRol", referencedColumnName = "codigoRol", nullable = false)
    @ManyToOne(optional = false)
    private Rol codigoRol;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;

    public Rolusuario() {
    }

    public Rolusuario(Integer codigoRolUsuario) {
        this.codigoRolUsuario = codigoRolUsuario;
    }

    public Integer getCodigoRolUsuario() {
        return codigoRolUsuario;
    }

    public void setCodigoRolUsuario(Integer codigoRolUsuario) {
        this.codigoRolUsuario = codigoRolUsuario;
    }

    public Rol getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(Rol codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoRolUsuario != null ? codigoRolUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolusuario)) {
            return false;
        }
        Rolusuario other = (Rolusuario) object;
        if ((this.codigoRolUsuario == null && other.codigoRolUsuario != null) || (this.codigoRolUsuario != null && !this.codigoRolUsuario.equals(other.codigoRolUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Rolusuario[ codigoRolUsuario=" + codigoRolUsuario + " ]";
    }
    
}
