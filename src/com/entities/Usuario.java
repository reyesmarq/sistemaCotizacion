/*
 * Nombre de Entidad: Usuario
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
    @UniqueConstraint(columnNames = {"nombreUsuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByCodigoUsuario", query = "SELECT u FROM Usuario u WHERE u.codigoUsuario = :codigoUsuario")
    , @NamedQuery(name = "Usuario.findByNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Usuario.findByContraUsuario", query = "SELECT u FROM Usuario u WHERE u.contraUsuario = :contraUsuario")
    , @NamedQuery(name = "Usuario.findUser", query = "SELECT distinct u.codigoUsuario FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario and u.contraUsuario = :contraUsuario")
    , @NamedQuery(name = "Usuario.existUser", query = "SELECT count(u.codigoUsuario) FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario and u.contraUsuario = :contraUsuario")
    , @NamedQuery(name = "Usuario.dependUser", query = "SELECT count(u.codigoUsuario) FROM Rolusuario u WHERE u.codigoUsuario.codigoUsuario=:codigoUsuario")
    , @NamedQuery(name = "Usuario.findPermisos", query = "select per.nombrePermiso from Permiso per where per.codigoPermiso in(select rolPer.codigoPermiso.codigoPermiso from Rolpermiso rolPer where rolPer.codigoRol.codigoRol in(select rolUs.codigoRol.codigoRol from Rolusuario rolUs inner join Usuario us on rolUs.codigoUsuario.codigoUsuario=us.codigoUsuario where us.codigoUsuario=:codigoUsuario))")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer codigoUsuario;
    @Column(length = 50)
    private String nombreUsuario;
    @Column(length = 50)
    private String contraUsuario;
    @OneToMany(mappedBy = "codigoUsuario")
    private List<Loguusuario> loguusuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoUsuario")
    private List<Rolusuario> rolusuarioList;
    @OneToMany(mappedBy = "codigoUsuario")
    private List<Empleado> empleadoList;

    public Usuario() {
    }

    public Usuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraUsuario() {
        return contraUsuario;
    }

    public void setContraUsuario(String contraUsuario) {
        this.contraUsuario = contraUsuario;
    }

    @XmlTransient
    public List<Loguusuario> getLoguusuarioList() {
        return loguusuarioList;
    }

    public void setLoguusuarioList(List<Loguusuario> loguusuarioList) {
        this.loguusuarioList = loguusuarioList;
    }

    @XmlTransient
    public List<Rolusuario> getRolusuarioList() {
        return rolusuarioList;
    }

    public void setRolusuarioList(List<Rolusuario> rolusuarioList) {
        this.rolusuarioList = rolusuarioList;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoUsuario != null ? codigoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.codigoUsuario == null && other.codigoUsuario != null) || (this.codigoUsuario != null && !this.codigoUsuario.equals(other.codigoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Usuario[ codigoUsuario=" + codigoUsuario + " ]";
    }
    
}
