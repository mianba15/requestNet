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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mianba
 */
@Entity
@Table(name = "tipo_procedencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProcedencia.findAll", query = "SELECT t FROM TipoProcedencia t")
    , @NamedQuery(name = "TipoProcedencia.findByIdTipoProcedencia", query = "SELECT t FROM TipoProcedencia t WHERE t.idTipoProcedencia = :idTipoProcedencia")})
public class TipoProcedencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_procedencia")
    private Integer idTipoProcedencia;
    @JoinColumn(name = "id_orden", referencedColumnName = "id_orden")
    @ManyToOne
    private Orden idOrden;
    @OneToMany(mappedBy = "idTipoProcedencia")
    private Collection<InventarioEquipos> inventarioEquiposCollection;

    public TipoProcedencia() {
    }

    public TipoProcedencia(Integer idTipoProcedencia) {
        this.idTipoProcedencia = idTipoProcedencia;
    }

    public Integer getIdTipoProcedencia() {
        return idTipoProcedencia;
    }

    public void setIdTipoProcedencia(Integer idTipoProcedencia) {
        this.idTipoProcedencia = idTipoProcedencia;
    }

    public Orden getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Orden idOrden) {
        this.idOrden = idOrden;
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
        hash += (idTipoProcedencia != null ? idTipoProcedencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProcedencia)) {
            return false;
        }
        TipoProcedencia other = (TipoProcedencia) object;
        if ((this.idTipoProcedencia == null && other.idTipoProcedencia != null) || (this.idTipoProcedencia != null && !this.idTipoProcedencia.equals(other.idTipoProcedencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.TipoProcedencia[ idTipoProcedencia=" + idTipoProcedencia + " ]";
    }
    
}
