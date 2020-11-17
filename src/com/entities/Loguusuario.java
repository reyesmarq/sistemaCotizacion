/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Nombre de la clase: Loguusuario
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
@Entity
@Table(catalog = "cotizacionEmpresa", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loguusuario.findAll", query = "SELECT l FROM Loguusuario l")
    , @NamedQuery(name = "Loguusuario.findByCodigoLog", query = "SELECT l FROM Loguusuario l WHERE l.codigoLog = :codigoLog")
    , @NamedQuery(name = "Loguusuario.findByNombreEvento", query = "SELECT l FROM Loguusuario l WHERE l.nombreEvento = :nombreEvento")
    , @NamedQuery(name = "Loguusuario.findByFechaIngreso", query = "SELECT l FROM Loguusuario l WHERE l.fechaIngreso = :fechaIngreso")})
public class Loguusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoLog;
    @Column(length = 100)
    private String nombreEvento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @JoinColumn(name = "codigoUsuario", referencedColumnName = "codigoUsuario")
    @ManyToOne
    private Usuario codigoUsuario;

    public Loguusuario() {
    }

    public Loguusuario(Integer codigoLog) {
        this.codigoLog = codigoLog;
    }

    public Integer getCodigoLog() {
        return codigoLog;
    }

    public void setCodigoLog(Integer codigoLog) {
        this.codigoLog = codigoLog;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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
        hash += (codigoLog != null ? codigoLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Loguusuario)) {
            return false;
        }
        Loguusuario other = (Loguusuario) object;
        if ((this.codigoLog == null && other.codigoLog != null) || (this.codigoLog != null && !this.codigoLog.equals(other.codigoLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Loguusuario[ codigoLog=" + codigoLog + " ]";
    }
    
}
