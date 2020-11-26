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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mianba
 */
@Entity
@Table(name = "estado_eq")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoEq.findAll", query = "SELECT e FROM EstadoEq e")
    , @NamedQuery(name = "EstadoEq.findByIdEstadoeq", query = "SELECT e FROM EstadoEq e WHERE e.idEstadoeq = :idEstadoeq")
    , @NamedQuery(name = "EstadoEq.findByNomEstadoeq", query = "SELECT e FROM EstadoEq e WHERE e.nomEstadoeq = :nomEstadoeq")})
public class EstadoEq implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estadoeq")
    private Integer idEstadoeq;
    @Size(max = 29)
    @Column(name = "nom_estadoeq")
    private String nomEstadoeq;
    @OneToMany(mappedBy = "idEstadoeq")
    private Collection<InventarioEquipos> inventarioEquiposCollection;

    public EstadoEq() {
    }

    public EstadoEq(Integer idEstadoeq) {
        this.idEstadoeq = idEstadoeq;
    }

    public Integer getIdEstadoeq() {
        return idEstadoeq;
    }

    public void setIdEstadoeq(Integer idEstadoeq) {
        this.idEstadoeq = idEstadoeq;
    }

    public String getNomEstadoeq() {
        return nomEstadoeq;
    }

    public void setNomEstadoeq(String nomEstadoeq) {
        this.nomEstadoeq = nomEstadoeq;
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
        hash += (idEstadoeq != null ? idEstadoeq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoEq)) {
            return false;
        }
        EstadoEq other = (EstadoEq) object;
        if ((this.idEstadoeq == null && other.idEstadoeq != null) || (this.idEstadoeq != null && !this.idEstadoeq.equals(other.idEstadoeq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.EstadoEq[ idEstadoeq=" + idEstadoeq + " ]";
    }
    
}
