/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "integrante_colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntegranteColaborador.findAll", query = "SELECT i FROM IntegranteColaborador i")
    , @NamedQuery(name = "IntegranteColaborador.findByIdCliente", query = "SELECT i FROM IntegranteColaborador i WHERE i.idCliente = :idCliente")})
public class IntegranteColaborador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cliente")
    private Integer idCliente;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private Collection<Casos> casosCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<InventarioEquipos> inventarioEquiposCollection;

    public IntegranteColaborador() {
    }

    public IntegranteColaborador(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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
    public Collection<InventarioEquipos> getInventarioEquiposCollection() {
        return inventarioEquiposCollection;
    }

    public void setInventarioEquiposCollection(Collection<InventarioEquipos> inventarioEquiposCollection) {
        this.inventarioEquiposCollection = inventarioEquiposCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntegranteColaborador)) {
            return false;
        }
        IntegranteColaborador other = (IntegranteColaborador) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.IntegranteColaborador[ idCliente=" + idCliente + " ]";
    }
    
}
