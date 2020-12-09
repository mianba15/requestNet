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
@Table(name = "tipo_orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoOrden.findAll", query = "SELECT t FROM TipoOrden t")
    , @NamedQuery(name = "TipoOrden.findByIdTipoOrden", query = "SELECT t FROM TipoOrden t WHERE t.idTipoOrden = :idTipoOrden")
    , @NamedQuery(name = "TipoOrden.findByNomTipoOrden", query = "SELECT t FROM TipoOrden t WHERE t.nomTipoOrden = :nomTipoOrden")})
public class TipoOrden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_orden")
    private Integer idTipoOrden;
    @Size(max = 20)
    @Column(name = "nom_tipo_orden")
    private String nomTipoOrden;
    @OneToMany(mappedBy = "idTipoOrden")
    private Collection<Orden> ordenCollection;

    public TipoOrden() {
    }

    public TipoOrden(Integer idTipoOrden) {
        this.idTipoOrden = idTipoOrden;
    }

    public Integer getIdTipoOrden() {
        return idTipoOrden;
    }

    public void setIdTipoOrden(Integer idTipoOrden) {
        this.idTipoOrden = idTipoOrden;
    }

    public String getNomTipoOrden() {
        return nomTipoOrden;
    }

    public void setNomTipoOrden(String nomTipoOrden) {
        this.nomTipoOrden = nomTipoOrden;
    }

    @XmlTransient
    public Collection<Orden> getOrdenCollection() {
        return ordenCollection;
    }

    public void setOrdenCollection(Collection<Orden> ordenCollection) {
        this.ordenCollection = ordenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoOrden != null ? idTipoOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoOrden)) {
            return false;
        }
        TipoOrden other = (TipoOrden) object;
        if ((this.idTipoOrden == null && other.idTipoOrden != null) || (this.idTipoOrden != null && !this.idTipoOrden.equals(other.idTipoOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNomTipoOrden();
    }
    
}
