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
@Table(name = "tipo_componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoComponente.findAll", query = "SELECT t FROM TipoComponente t")
    , @NamedQuery(name = "TipoComponente.findByIdTipoComponente", query = "SELECT t FROM TipoComponente t WHERE t.idTipoComponente = :idTipoComponente")
    , @NamedQuery(name = "TipoComponente.findByNomTipoComponente", query = "SELECT t FROM TipoComponente t WHERE t.nomTipoComponente = :nomTipoComponente")
    , @NamedQuery(name = "TipoComponente.findByCantidad", query = "SELECT t FROM TipoComponente t WHERE t.cantidad = :cantidad")})
public class TipoComponente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_componente")
    private Integer idTipoComponente;
    @Size(max = 212)
    @Column(name = "nom_tipo_componente")
    private String nomTipoComponente;
    @Column(name = "cantidad")
    private Integer cantidad;
    @OneToMany(mappedBy = "idTipoComponente")
    private Collection<InventarioEquipos> inventarioEquiposCollection;

    public TipoComponente() {
    }

    public TipoComponente(Integer idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public Integer getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(Integer idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public String getNomTipoComponente() {
        return nomTipoComponente;
    }

    public void setNomTipoComponente(String nomTipoComponente) {
        this.nomTipoComponente = nomTipoComponente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
        hash += (idTipoComponente != null ? idTipoComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoComponente)) {
            return false;
        }
        TipoComponente other = (TipoComponente) object;
        if ((this.idTipoComponente == null && other.idTipoComponente != null) || (this.idTipoComponente != null && !this.idTipoComponente.equals(other.idTipoComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.TipoComponente[ idTipoComponente=" + idTipoComponente + " ]";
    }
    
}
