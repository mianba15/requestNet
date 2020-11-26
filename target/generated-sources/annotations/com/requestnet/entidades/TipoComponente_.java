package com.requestnet.entidades;

import com.requestnet.entidades.InventarioEquipos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(TipoComponente.class)
public class TipoComponente_ { 

    public static volatile SingularAttribute<TipoComponente, Integer> idTipoComponente;
    public static volatile SingularAttribute<TipoComponente, Integer> cantidad;
    public static volatile SingularAttribute<TipoComponente, String> nomTipoComponente;
    public static volatile CollectionAttribute<TipoComponente, InventarioEquipos> inventarioEquiposCollection;

}