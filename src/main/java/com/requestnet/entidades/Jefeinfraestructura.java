/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mianba
 */
@Entity
@Table(name = "jefeinfraestructura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jefeinfraestructura.findAll", query = "SELECT j FROM Jefeinfraestructura j")
    , @NamedQuery(name = "Jefeinfraestructura.findByIdJefe", query = "SELECT j FROM Jefeinfraestructura j WHERE j.idJefe = :idJefe")})
public class Jefeinfraestructura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_jefe")
    private Integer idJefe;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios idUsuario;
    @OneToMany(mappedBy = "idJefe")
    private Collection<Casos> casosCollection;
    @OneToMany(mappedBy = "idJefe")
    private Collection<Orden> ordenCollection;
    @OneToMany(mappedBy = "idJefe")
    private Collection<InventarioEquipos> inventarioEquiposCollection;

    public Jefeinfraestructura() {
    }

    public Jefeinfraestructura(Integer idJefe) {
        this.idJefe = idJefe;
    }

    public Integer getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(Integer idJefe) {
        this.idJefe = idJefe;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public Collection<Casos> getCasosCollection() {
        return casosCollection;
    }

    public void setCasosCollection(Collection<Casos> casosCollection) {
        this.casosCollection = casosCollection;
    }

    @XmlTransient
    public Collection<Orden> getOrdenCollection() {
        return ordenCollection;
    }

    public void setOrdenCollection(Collection<Orden> ordenCollection) {
        this.ordenCollection = ordenCollection;
    }

    @XmlTransient
    public Collection<InventarioEquipos> getInventarioEquiposCollection() {
        return inventarioEquiposCollection;
    }

    public void setInventarioEquiposCollection(Collection<InventarioEquipos> inventarioEquiposCollection) {
        this.inventarioEquiposCollection = inventarioEquiposCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJefe != null ? idJefe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jefeinfraestructura)) {
            return false;
        }
        Jefeinfraestructura other = (Jefeinfraestructura) object;
        if ((this.idJefe == null && other.idJefe != null) || (this.idJefe != null && !this.idJefe.equals(other.idJefe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.idUsuario.getNombres() + this.idUsuario.getApellidos();
    }
    
}
