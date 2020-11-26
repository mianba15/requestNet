package com.requestnet.entidades;

import com.requestnet.entidades.Casos;
import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.Orden;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Liderinventario.class)
public class Liderinventario_ { 

    public static volatile SingularAttribute<Liderinventario, Usuarios> idUsuario;
    public static volatile SingularAttribute<Liderinventario, Integer> idLider;
    public static volatile CollectionAttribute<Liderinventario, InventarioEquipos> inventarioEquiposCollection;
    public static volatile CollectionAttribute<Liderinventario, Casos> casosCollection;
    public static volatile CollectionAttribute<Liderinventario, Orden> ordenCollection;

}