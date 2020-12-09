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
@Table(name = "estados_ru")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadosRu.findAll", query = "SELECT e FROM EstadosRu e")
    , @NamedQuery(name = "EstadosRu.findByIdEstadoru", query = "SELECT e FROM EstadosRu e WHERE e.idEstadoru = :idEstadoru")
    , @NamedQuery(name = "EstadosRu.findByNomEstadoru", query = "SELECT e FROM EstadosRu e WHERE e.nomEstadoru = :nomEstadoru")})
public class EstadosRu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estadoru")
    private Integer idEstadoru;
    @Size(max = 15)
    @Column(name = "nom_estadoru")
    private String nomEstadoru;
    @OneToMany(mappedBy = "idEstadoru")
    private Collection<Roles> rolesCollection;
    @OneToMany(mappedBy = "idEstadoru")
    private Collection<Usuarios> usuariosCollection;

    public EstadosRu() {
    }

    public EstadosRu(Integer idEstadoru) {
        this.idEstadoru = idEstadoru;
    }

    public Integer getIdEstadoru() {
        return idEstadoru;
    }

    public void setIdEstadoru(Integer idEstadoru) {
        this.idEstadoru = idEstadoru;
    }

    public String getNomEstadoru() {
        return nomEstadoru;
    }

    public void setNomEstadoru(String nomEstadoru) {
        this.nomEstadoru = nomEstadoru;
    }

    @XmlTransient
    public Collection<Roles> getRolesCollection() {
        return rolesCollection;
    }

    public void setRolesCollection(Collection<Roles> rolesCollection) {
        this.rolesCollection = rolesCollection;
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
        hash += (idEstadoru != null ? idEstadoru.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadosRu)) {
            return false;
        }
        EstadosRu other = (EstadosRu) object;
        if ((this.idEstadoru == null && other.idEstadoru != null) || (this.idEstadoru != null && !this.idEstadoru.equals(other.idEstadoru))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNomEstadoru();
    }
    
}
