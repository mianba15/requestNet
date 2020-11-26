package com.requestnet.entidades;

import com.requestnet.entidades.Ciudades;
import com.requestnet.entidades.EstadosRu;
import com.requestnet.entidades.IntegranteColaborador;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.Proveedores;
import com.requestnet.entidades.Roles;
import com.requestnet.entidades.Tecnico;
import com.requestnet.entidades.TipoDoc;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, String> apellidos;
    public static volatile SingularAttribute<Usuarios, Integer> numDocumento;
    public static volatile SingularAttribute<Usuarios, Roles> idRol;
    public static volatile CollectionAttribute<Usuarios, Liderinventario> liderinventarioCollection;
    public static volatile SingularAttribute<Usuarios, Date> fechaNacimiento;
    public static volatile SingularAttribute<Usuarios, TipoDoc> idTipdoc;
    public static volatile SingularAttribute<Usuarios, Integer> idUsuario;
    public static volatile SingularAttribute<Usuarios, String> direccion;
    public static volatile SingularAttribute<Usuarios, Date> fechaIngresoCom;
    public static volatile SingularAttribute<Usuarios, Ciudades> idCiudad;
    public static volatile SingularAttribute<Usuarios, String> nombres;
    public static volatile CollectionAttribute<Usuarios, IntegranteColaborador> integranteColaboradorCollection;
    public static volatile SingularAttribute<Usuarios, EstadosRu> idEstadoru;
    public static volatile CollectionAttribute<Usuarios, Jefeinfraestructura> jefeinfraestructuraCollection;
    public static volatile CollectionAttribute<Usuarios, Proveedores> proveedoresCollection;
    public static volatile SingularAttribute<Usuarios, String> correo;
    public static volatile CollectionAttribute<Usuarios, Tecnico> tecnicoCollection;
    public static volatile SingularAttribute<Usuarios, Long> telefono;
    public static volatile SingularAttribute<Usuarios, String> cargo;
    public static volatile SingularAttribute<Usuarios, String> contraseña;

}