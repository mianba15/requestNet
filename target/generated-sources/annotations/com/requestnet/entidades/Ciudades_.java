package com.requestnet.entidades;

import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Ciudades.class)
public class Ciudades_ { 

    public static volatile SingularAttribute<Ciudades, String> nombreCiu;
    public static volatile CollectionAttribute<Ciudades, Usuarios> usuariosCollection;
    public static volatile SingularAttribute<Ciudades, Integer> idCiudad;

}