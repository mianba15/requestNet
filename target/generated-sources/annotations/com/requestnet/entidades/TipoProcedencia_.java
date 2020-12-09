package com.requestnet.entidades;

import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.Orden;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(TipoProcedencia.class)
public class TipoProcedencia_ { 

    public static volatile CollectionAttribute<TipoProcedencia, InventarioEquipos> inventarioEquiposCollection;
    public static volatile SingularAttribute<TipoProcedencia, Integer> idTipoProcedencia;
    public static volatile SingularAttribute<TipoProcedencia, Orden> idOrden;

}