package com.requestnet.entidades;

import com.requestnet.entidades.Roles;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(EstadosRu.class)
public class EstadosRu_ { 

    public static volatile SingularAttribute<EstadosRu, String> nomEstadoru;
    public static volatile SingularAttribute<EstadosRu, Integer> idEstadoru;
    public static volatile CollectionAttribute<EstadosRu, Usuarios> usuariosCollection;
    public static volatile CollectionAttribute<EstadosRu, Roles> rolesCollection;

}