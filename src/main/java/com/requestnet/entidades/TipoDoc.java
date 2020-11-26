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
@Table(name = "tipo_doc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoDoc.findAll", query = "SELECT t FROM TipoDoc t")
    , @NamedQuery(name = "TipoDoc.findByIdTipdoc", query = "SELECT t FROM TipoDoc t WHERE t.idTipdoc = :idTipdoc")
    , @NamedQuery(name = "TipoDoc.findByNombreTipdoc", query = "SELECT t FROM TipoDoc t WHERE t.nombreTipdoc = :nombreTipdoc")})
public class TipoDoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipdoc")
    private Integer idTipdoc;
    @Size(max = 12)
    @Column(name = "nombre_tipdoc")
    private String nombreTipdoc;
    @OneToMany(mappedBy = "idTipdoc")
    private Collection<Usuarios> usuariosCollection;

    public TipoDoc() {
    }

    public TipoDoc(Integer idTipdoc) {
        this.idTipdoc = idTipdoc;
    }

    public Integer getIdTipdoc() {
        return idTipdoc;
    }

    public void setIdTipdoc(Integer idTipdoc) {
        this.idTipdoc = idTipdoc;
    }

    public String getNombreTipdoc() {
        return nombreTipdoc;
    }

    public void setNombreTipdoc(String nombreTipdoc) {
        this.nombreTipdoc = nombreTipdoc;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipdoc != null ? idTipdoc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoDoc)) {
            return false;
        }
        TipoDoc other = (TipoDoc) object;
        if ((this.idTipdoc == null && other.idTipdoc != null) || (this.idTipdoc != null && !this.idTipdoc.equals(other.idTipdoc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.TipoDoc[ idTipdoc=" + idTipdoc + " ]";
    }
    
}
