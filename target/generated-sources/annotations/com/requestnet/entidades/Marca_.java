package com.requestnet.entidades;

import com.requestnet.entidades.InventarioEquipos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Marca.class)
public class Marca_ { 

    public static volatile SingularAttribute<Marca, String> nomMarca;
    public static volatile SingularAttribute<Marca, Integer> idMarca;
    public static volatile CollectionAttribute<Marca, InventarioEquipos> inventarioEquiposCollection;

}