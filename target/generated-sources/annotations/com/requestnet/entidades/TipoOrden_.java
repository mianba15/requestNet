package com.requestnet.entidades;

import com.requestnet.entidades.Orden;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(TipoOrden.class)
public class TipoOrden_ { 

    public static volatile SingularAttribute<TipoOrden, Integer> idTipoOrden;
    public static volatile SingularAttribute<TipoOrden, String> nomTipoOrden;
    public static volatile CollectionAttribute<TipoOrden, Orden> ordenCollection;

}