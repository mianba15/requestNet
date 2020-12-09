package com.requestnet.entidades;

import com.requestnet.entidades.Casos;
import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.Orden;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(Jefeinfraestructura.class)
public class Jefeinfraestructura_ { 

    public static volatile SingularAttribute<Jefeinfraestructura, Integer> idJefe;
    public static volatile SingularAttribute<Jefeinfraestructura, Usuarios> idUsuario;
    public static volatile CollectionAttribute<Jefeinfraestructura, InventarioEquipos> inventarioEquiposCollection;
    public static volatile CollectionAttribute<Jefeinfraestructura, Casos> casosCollection;
    public static volatile CollectionAttribute<Jefeinfraestructura, Orden> ordenCollection;

}