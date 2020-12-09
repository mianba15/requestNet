/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mianba
 */
@Entity
@Table(name = "orden")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o")
    , @NamedQuery(name = "Orden.findByIdOrden", query = "SELECT o FROM Orden o WHERE o.idOrden = :idOrden")
    , @NamedQuery(name = "Orden.findByFechaAprobacion", query = "SELECT o FROM Orden o WHERE o.fechaAprobacion = :fechaAprobacion")
    , @NamedQuery(name = "Orden.findByFechaSolucion", query = "SELECT o FROM Orden o WHERE o.fechaSolucion = :fechaSolucion")})
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden")
    private Integer idOrden;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Column(name = "adjuntar_archivo")
    private byte[] adjuntarArchivo;
    @Column(name = "fecha_aprobacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAprobacion;
    @Lob
    @Size(max = 65535)
    @Column(name = "solucion")
    private String solucion;
    @Column(name = "fecha_solucion")
    @Temporal(TemporalType.DATE)
    private Date fechaSolucion;
    @Lob
    @Size(max = 65535)
    @Column(name = "notas")
    private String notas;
    @OneToMany(mappedBy = "idOrden")
    private Collection<TipoProcedencia> tipoProcedenciaCollection;
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne
    private Proveedores idProveedor;
    @JoinColumn(name = "id_lider", referencedColumnName = "id_lider")
    @ManyToOne
    private Liderinventario idLider;
    @JoinColumn(name = "id_jefe", referencedColumnName = "id_jefe")
    @ManyToOne
    private Jefeinfraestructura idJefe;
    @JoinColumn(name = "id_tipo_orden", referencedColumnName = "id_tipo_orden")
    @ManyToOne
    private TipoOrden idTipoOrden;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private Estados idEstado;

    public Orden() {
    }

    public Orden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Orden(Integer idOrden, String descripcion) {
        this.idOrden = idOrden;
        this.descripcion = descripcion;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getAdjuntarArchivo() {
        return adjuntarArchivo;
    }

    public void setAdjuntarArchivo(byte[] adjuntarArchivo) {
        this.adjuntarArchivo = adjuntarArchivo;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    @XmlTransient
    public Collection<TipoProcedencia> getTipoProcedenciaCollection() {
        return tipoProcedenciaCollection;
    }

    public void setTipoProcedenciaCollection(Collection<TipoProcedencia> tipoProcedenciaCollection) {
        this.tipoProcedenciaCollection = tipoProcedenciaCollection;
    }

    public Proveedores getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedores idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Liderinventario getIdLider() {
        return idLider;
    }

    public void setIdLider(Liderinventario idLider) {
        this.idLider = idLider;
    }

    public Jefeinfraestructura getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(Jefeinfraestructura idJefe) {
        this.idJefe = idJefe;
    }

    public TipoOrden getIdTipoOrden() {
        return idTipoOrden;
    }

    public void setIdTipoOrden(TipoOrden idTipoOrden) {
        this.idTipoOrden = idTipoOrden;
    }

    public Estados getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estados idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.idTipoOrden.getNomTipoOrden();
    }
    
}
