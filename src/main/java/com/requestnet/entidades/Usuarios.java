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
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByIdUsuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuarios.findByNumDocumento", query = "SELECT u FROM Usuarios u WHERE u.numDocumento = :numDocumento")
    , @NamedQuery(name = "Usuarios.findByNombres", query = "SELECT u FROM Usuarios u WHERE u.nombres = :nombres")
    , @NamedQuery(name = "Usuarios.findByApellidos", query = "SELECT u FROM Usuarios u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "Usuarios.findByTelefono", query = "SELECT u FROM Usuarios u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuarios.findByDireccion", query = "SELECT u FROM Usuarios u WHERE u.direccion = :direccion")
    , @NamedQuery(name = "Usuarios.findByFechaNacimiento", query = "SELECT u FROM Usuarios u WHERE u.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Usuarios.findByFechaIngresoCom", query = "SELECT u FROM Usuarios u WHERE u.fechaIngresoCom = :fechaIngresoCom")
    , @NamedQuery(name = "Usuarios.findByCargo", query = "SELECT u FROM Usuarios u WHERE u.cargo = :cargo")
    , @NamedQuery(name = "Usuarios.findByContrase\u00f1a", query = "SELECT u FROM Usuarios u WHERE u.contrase\u00f1a = :contrase\u00f1a")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_documento")
    private int numDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private long telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso_com")
    @Temporal(TemporalType.DATE)
    private Date fechaIngresoCom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 15)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Tecnico> tecnicoCollection;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne
    private Roles idRol;
    @JoinColumn(name = "id_tipdoc", referencedColumnName = "id_tipdoc")
    @ManyToOne
    private TipoDoc idTipdoc;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne
    private Ciudades idCiudad;
    @JoinColumn(name = "id_estadoru", referencedColumnName = "id_estadoru")
    @ManyToOne
    private EstadosRu idEstadoru;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Jefeinfraestructura> jefeinfraestructuraCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Proveedores> proveedoresCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Liderinventario> liderinventarioCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<IntegranteColaborador> integranteColaboradorCollection;

    public Usuarios() {
    }

    public Usuarios(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(Integer idUsuario, int numDocumento, String nombres, String apellidos, long telefono, String correo, String direccion, Date fechaIngresoCom, String cargo) {
        this.idUsuario = idUsuario;
        this.numDocumento = numDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaIngresoCom = fechaIngresoCom;
        this.cargo = cargo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(int numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaIngresoCom() {
        return fechaIngresoCom;
    }

    public void setFechaIngresoCom(Date fechaIngresoCom) {
        this.fechaIngresoCom = fechaIngresoCom;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @XmlTransient
    public Collection<Tecnico> getTecnicoCollection() {
        return tecnicoCollection;
    }

    public void setTecnicoCollection(Collection<Tecnico> tecnicoCollection) {
        this.tecnicoCollection = tecnicoCollection;
    }

    public Roles getIdRol() {
        return idRol;
    }

    public void setIdRol(Roles idRol) {
        this.idRol = idRol;
    }

    public TipoDoc getIdTipdoc() {
        return idTipdoc;
    }

    public void setIdTipdoc(TipoDoc idTipdoc) {
        this.idTipdoc = idTipdoc;
    }

    public Ciudades getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudades idCiudad) {
        this.idCiudad = idCiudad;
    }

    public EstadosRu getIdEstadoru() {
        return idEstadoru;
    }

    public void setIdEstadoru(EstadosRu idEstadoru) {
        this.idEstadoru = idEstadoru;
    }

    @XmlTransient
    public Collection<Jefeinfraestructura> getJefeinfraestructuraCollection() {
        return jefeinfraestructuraCollection;
    }

    public void setJefeinfraestructuraCollection(Collection<Jefeinfraestructura> jefeinfraestructuraCollection) {
        this.jefeinfraestructuraCollection = jefeinfraestructuraCollection;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
    }

    @XmlTransient
    public Collection<Liderinventario> getLiderinventarioCollection() {
        return liderinventarioCollection;
    }

    public void setLiderinventarioCollection(Collection<Liderinventario> liderinventarioCollection) {
        this.liderinventarioCollection = liderinventarioCollection;
    }

    @XmlTransient
    public Collection<IntegranteColaborador> getIntegranteColaboradorCollection() {
        return integranteColaboradorCollection;
    }

    public void setIntegranteColaboradorCollection(Collection<IntegranteColaborador> integranteColaboradorCollection) {
        this.integranteColaboradorCollection = integranteColaboradorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.requestnet.entidades.Usuarios[ idUsuario=" + idUsuario + " ]";
    }
    
}
