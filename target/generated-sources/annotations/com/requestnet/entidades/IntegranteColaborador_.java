package com.requestnet.entidades;

import com.requestnet.entidades.Casos;
import com.requestnet.entidades.InventarioEquipos;
import com.requestnet.entidades.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(IntegranteColaborador.class)
public class IntegranteColaborador_ { 

    public static volatile SingularAttribute<IntegranteColaborador, Integer> idCliente;
    public static volatile SingularAttribute<IntegranteColaborador, Usuarios> idUsuario;
    public static volatile CollectionAttribute<IntegranteColaborador, InventarioEquipos> inventarioEquiposCollection;
    public static volatile CollectionAttribute<IntegranteColaborador, Casos> casosCollection;

}