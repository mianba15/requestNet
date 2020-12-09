package com.requestnet.entidades;

import com.requestnet.entidades.Estados;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.Proveedores;
import com.requestnet.entidades.TipoOrden;
import com.requestnet.entidades.TipoProcedencia;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(Orden.class)
public class Orden_ { 

    public static volatile SingularAttribute<Orden, String> descripcion;
    public static volatile SingularAttribute<Orden, Jefeinfraestructura> idJefe;
    public static volatile SingularAttribute<Orden, TipoOrden> idTipoOrden;
    public static volatile SingularAttribute<Orden, String> notas;
    public static volatile SingularAttribute<Orden, Liderinventario> idLider;
    public static volatile SingularAttribute<Orden, Integer> idOrden;
    public static volatile SingularAttribute<Orden, String> solucion;
    public static volatile SingularAttribute<Orden, Estados> idEstado;
    public static volatile CollectionAttribute<Orden, TipoProcedencia> tipoProcedenciaCollection;
    public static volatile SingularAttribute<Orden, Date> fechaAprobacion;
    public static volatile SingularAttribute<Orden, Proveedores> idProveedor;
    public static volatile SingularAttribute<Orden, Date> fechaSolucion;
    public static volatile SingularAttribute<Orden, byte[]> adjuntarArchivo;

}