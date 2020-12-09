package com.requestnet.entidades;

import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(TipoDoc.class)
public class TipoDoc_ { 

    public static volatile SingularAttribute<TipoDoc, String> nombreTipdoc;
    public static volatile SingularAttribute<TipoDoc, Integer> idTipdoc;
    public static volatile CollectionAttribute<TipoDoc, Usuarios> usuariosCollection;

}