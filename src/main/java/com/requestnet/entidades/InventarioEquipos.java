/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.requestnet.entidades;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mianba
 */
@Entity
@Table(name = "inventario_equipos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InventarioEquipos.findAll", query = "SELECT i FROM InventarioEquipos i")
    , @NamedQuery(name = "InventarioEquipos.findByIdEquipo", query = "SELECT i FROM InventarioEquipos i WHERE i.idEquipo = :idEquipo")
    , @NamedQuery(name = "InventarioEquipos.findBySerial", query = "SELECT i FROM InventarioEquipos i WHERE i.serial = :serial")})
public class InventarioEquipos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_equipo")
    private Integer idEquipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "serial")
    private String serial;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private IntegranteColaborador idCliente;
    @JoinColumn(name = "id_tipo_componente", referencedColumnName = "id_tipo_componente")
    @ManyToOne
    private TipoComponente idTipoComponente;
    @JoinColumn(name = "id_marca", referencedColumnName = "id_marca")
    @ManyToOne
    private Marca idMarca;
    @JoinColumn(name = "id_tipo_procedencia", referencedColumnName = "id_tipo_procedencia")
    @ManyToOne
    private TipoProcedencia idTipoProcedencia;
    @JoinColumn(name = "id_jefe", referencedColumnName = "id_jefe")
    @ManyToOne
    private Jefeinfraestructura idJefe;
    @JoinColumn(name = "id_lider", referencedColumnName = "id_lider")
    @ManyToOne
    private Liderinventario idLider;
    @JoinColumn(name = "id_estadoeq", referencedColumnName = "id_estadoeq")
    @ManyToOne
    private EstadoEq idEstadoeq;

    public InventarioEquipos() {
    }

    public InventarioEquipos(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public InventarioEquipos(Integer idEquipo, String serial) {
        this.idEquipo = idEquipo;
        this.serial = serial;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public IntegranteColaborador getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(IntegranteColaborador idCliente) {
        this.idCliente = idCliente;
    }

    public TipoComponente getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(TipoComponente idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public Marca getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Marca idMarca) {
        this.idMarca = idMarca;
    }

    public TipoProcedencia getIdTipoProcedencia() {
        return idTipoProcedencia;
    }

    public void setIdTipoProcedencia(TipoProcedencia idTipoProcedencia) {
        this.idTipoProcedencia = idTipoProcedencia;
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

    public EstadoEq getIdEstadoeq() {
        return idEstadoeq;
    }

    public void setIdEstadoeq(EstadoEq idEstadoeq) {
        this.idEstadoeq = idEstadoeq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipo != null ? idEquipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventarioEquipos)) {
            return false;
        }
        InventarioEquipos other = (InventarioEquipos) object;
        if ((this.idEquipo == null && other.idEquipo != null) || (this.idEquipo != null && !this.idEquipo.equals(other.idEquipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.InventarioEquipos[ idEquipo=" + idEquipo + " ]";
    }
    
}
