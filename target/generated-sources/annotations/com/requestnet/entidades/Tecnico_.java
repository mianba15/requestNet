package com.requestnet.entidades;

import com.requestnet.entidades.Casos;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Tecnico.class)
public class Tecnico_ { 

    public static volatile SingularAttribute<Tecnico, Usuarios> idUsuario;
    public static volatile SingularAttribute<Tecnico, Integer> idTecnico;
    public static volatile CollectionAttribute<Tecnico, Casos> casosCollection;

}