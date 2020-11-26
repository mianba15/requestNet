package com.requestnet.entidades;

import com.requestnet.entidades.InventarioEquipos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(EstadoEq.class)
public class EstadoEq_ { 

    public static volatile SingularAttribute<EstadoEq, String> nomEstadoeq;
    public static volatile SingularAttribute<EstadoEq, Integer> idEstadoeq;
    public static volatile CollectionAttribute<EstadoEq, InventarioEquipos> inventarioEquiposCollection;

}