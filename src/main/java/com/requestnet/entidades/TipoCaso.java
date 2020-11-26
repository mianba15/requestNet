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
@Table(name = "tipo_caso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCaso.findAll", query = "SELECT t FROM TipoCaso t")
    , @NamedQuery(name = "TipoCaso.findByIdTipoCaso", query = "SELECT t FROM TipoCaso t WHERE t.idTipoCaso = :idTipoCaso")
    , @NamedQuery(name = "TipoCaso.findByNomTipoCaso", query = "SELECT t FROM TipoCaso t WHERE t.nomTipoCaso = :nomTipoCaso")})
public class TipoCaso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_caso")
    private Integer idTipoCaso;
    @Size(max = 19)
    @Column(name = "nom_tipo_caso")
    private String nomTipoCaso;
    @OneToMany(mappedBy = "idTipoCaso")
    private Collection<Casos> casosCollection;

    public TipoCaso() {
    }

    public TipoCaso(Integer idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public Integer getIdTipoCaso() {
        return idTipoCaso;
    }

    public void setIdTipoCaso(Integer idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public String getNomTipoCaso() {
        return nomTipoCaso;
    }

    public void setNomTipoCaso(String nomTipoCaso) {
        this.nomTipoCaso = nomTipoCaso;
    }

    @XmlTransient
    public Collection<Casos> getCasosCollection() {
        return casosCollection;
    }

    public void setCasosCollection(Collection<Casos> casosCollection) {
        this.casosCollection = casosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCaso != null ? idTipoCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCaso)) {
            return false;
        }
        TipoCaso other = (TipoCaso) object;
        if ((this.idTipoCaso == null && other.idTipoCaso != null) || (this.idTipoCaso != null && !this.idTipoCaso.equals(other.idTipoCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.TipoCaso[ idTipoCaso=" + idTipoCaso + " ]";
    }
    
}
