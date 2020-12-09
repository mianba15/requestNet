/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.entidades;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mianba
 */
@Entity
@Table(name = "casos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casos.findAll", query = "SELECT c FROM Casos c")
    , @NamedQuery(name = "Casos.findByIdCaso", query = "SELECT c FROM Casos c WHERE c.idCaso = :idCaso")
    , @NamedQuery(name = "Casos.findByFechaCreacion", query = "SELECT c FROM Casos c WHERE c.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Casos.findByFechaSol", query = "SELECT c FROM Casos c WHERE c.fechaSol = :fechaSol")})
public class Casos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_caso")
    private Integer idCaso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Size(max = 65535)
    @Column(name = "solucion")
    private String solucion;
    @Column(name = "fecha_sol")
    @Temporal(TemporalType.DATE)
    private Date fechaSol;
    @Lob
    @Size(max = 65535)
    @Column(name = "notas")
    private String notas;
    @JoinColumn(name = "id_tipo_caso", referencedColumnName = "id_tipo_caso")
    @ManyToOne
    private TipoCaso idTipoCaso;
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico")
    @ManyToOne
    private Tecnico idTecnico;
    @JoinColumn(name = "id_jefe", referencedColumnName = "id_jefe")
    @ManyToOne
    private Jefeinfraestructura idJefe;
    @JoinColumn(name = "id_lider", referencedColumnName = "id_lider")
    @ManyToOne
    private Liderinventario idLider;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private Estados idEstado;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
    private IntegranteColaborador idCliente;

    public Casos() {
    }

    public Casos(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public Casos(Integer idCaso, Date fechaCreacion, String descripcion) {
        this.idCaso = idCaso;
        this.fechaCreacion = fechaCreacion;
        this.descripcion = descripcion;
    }

    public Integer getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Integer idCaso) {
        this.idCaso = idCaso;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public Date getFechaSol() {
        return fechaSol;
    }

    public void setFechaSol(Date fechaSol) {
        this.fechaSol = fechaSol;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public TipoCaso getIdTipoCaso() {
        return idTipoCaso;
    }

    public void setIdTipoCaso(TipoCaso idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public Tecnico getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Tecnico idTecnico) {
        this.idTecnico = idTecnico;
    }

    public Jefeinfraestructura getIdJefe() {
        return idJefe;
    }

    public void setIdJefe(Jefeinfraestructura idJefe) {
        this.idJefe = idJefe;
    }

    public Liderinventario getIdLider() {
        return idLider;
    }

    public void setIdLider(Liderinventario idLider) {
        this.idLider = idLider;
    }

    public Estados getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estados idEstado) {
        this.idEstado = idEstado;
    }

    public IntegranteColaborador getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(IntegranteColaborador idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaso != null ? idCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Casos)) {
            return false;
        }
        Casos other = (Casos) object;
        if ((this.idCaso == null && other.idCaso != null) || (this.idCaso != null && !this.idCaso.equals(other.idCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.idTipoCaso.getNomTipoCaso();
    }
    
}
