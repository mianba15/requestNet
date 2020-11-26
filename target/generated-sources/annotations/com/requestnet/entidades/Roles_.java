package com.requestnet.entidades;

import com.requestnet.entidades.EstadosRu;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Roles.class)
public class Roles_ { 

    public static volatile SingularAttribute<Roles, Integer> idRol;
    public static volatile SingularAttribute<Roles, EstadosRu> idEstadoru;
    public static volatile SingularAttribute<Roles, String> nombreRol;
    public static volatile CollectionAttribute<Roles, Usuarios> usuariosCollection;

}