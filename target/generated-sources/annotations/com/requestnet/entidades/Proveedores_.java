package com.requestnet.entidades;

import com.requestnet.entidades.Orden;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Proveedores.class)
public class Proveedores_ { 

    public static volatile SingularAttribute<Proveedores, Integer> idProveedor;
    public static volatile SingularAttribute<Proveedores, Usuarios> idUsuario;
    public static volatile CollectionAttribute<Proveedores, Orden> ordenCollection;

}