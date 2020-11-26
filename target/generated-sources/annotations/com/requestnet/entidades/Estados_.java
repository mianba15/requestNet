package com.requestnet.entidades;

import com.requestnet.entidades.Casos;
import com.requestnet.entidades.Orden;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Estados.class)
public class Estados_ { 

    public static volatile SingularAttribute<Estados, Integer> idEstado;
    public static volatile SingularAttribute<Estados, String> nomEstado;
    public static volatile CollectionAttribute<Estados, Casos> casosCollection;
    public static volatile CollectionAttribute<Estados, Orden> ordenCollection;

}