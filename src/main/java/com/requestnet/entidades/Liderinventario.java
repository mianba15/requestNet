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
@Table(name = "liderinventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liderinventario.findAll", query = "SELECT l FROM Liderinventario l")
    , @NamedQuery(name = "Liderinventario.findByIdLider", query = "SELECT l FROM Liderinventario l WHERE l.idLider = :idLider")})
public class Liderinventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_lider")
    private Integer idLider;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios idUsuario;
    @OneToMany(mappedBy = "idLider")
    private Collection<Casos> casosCollection;
    @OneToMany(mappedBy = "idLider")
    private Collection<Orden> ordenCollection;
    @OneToMany(mappedBy = "idLider")
    private Collection<InventarioEquipos> inventarioEquiposCollection;

    public Liderinventario() {
    }

    public Liderinventario(Integer idLider) {
        this.idLider = idLider;
    }

    public Integer getIdLider() {
        return idLider;
    }

    public void setIdLider(Integer idLider) {
        this.idLider = idLider;
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
        hash += (idLider != null ? idLider.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liderinventario)) {
            return false;
        }
        Liderinventario other = (Liderinventario) object;
        if ((this.idLider == null && other.idLider != null) || (this.idLider != null && !this.idLider.equals(other.idLider))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.Liderinventario[ idLider=" + idLider + " ]";
    }
    
}
