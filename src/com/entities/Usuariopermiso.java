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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Nombre de la clase: Usuariopermiso
 *  Fecha: 11-04-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
@Entity
@Table(catalog = "cotizacionEmpresa", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuariopermiso.findAll", query = "SELECT u FROM Usuariopermiso u")
    , @NamedQuery(name = "Usuariopermiso.findByCodigoUsuarioPermiso", query = "SELECT u FROM Usuariopermiso u WHERE u.codigoUsuarioPermiso = :codigoUsuarioPermiso")})
public class Usuariopermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoUsuarioPermiso;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario codigoUsuario;
    @JoinColumn(name = "codigoPermiso", referencedColumnName = "codigoPermiso", nullable = false)
    @ManyToOne(optional = false)
    private Permiso codigoPermiso;

    public Usuariopermiso() {
    }

    public Usuariopermiso(Integer codigoUsuarioPermiso) {
        this.codigoUsuarioPermiso = codigoUsuarioPermiso;
    }

    public Integer getCodigoUsuarioPermiso() {
        return codigoUsuarioPermiso;
    }

    public void setCodigoUsuarioPermiso(Integer codigoUsuarioPermiso) {
        this.codigoUsuarioPermiso = codigoUsuarioPermiso;
    }

    public Usuario getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Usuario codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
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
        hash += (codigoUsuarioPermiso != null ? codigoUsuarioPermiso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariopermiso)) {
            return false;
        }
        Usuariopermiso other = (Usuariopermiso) object;
        if ((this.codigoUsuarioPermiso == null && other.codigoUsuarioPermiso != null) || (this.codigoUsuarioPermiso != null && !this.codigoUsuarioPermiso.equals(other.codigoUsuarioPermiso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Usuariopermiso[ codigoUsuarioPermiso=" + codigoUsuarioPermiso + " ]";
    }
    
}
