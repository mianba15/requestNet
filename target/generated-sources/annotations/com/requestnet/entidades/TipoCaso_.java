package com.requestnet.entidades;

import com.requestnet.entidades.Casos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(TipoCaso.class)
public class TipoCaso_ { 

    public static volatile SingularAttribute<TipoCaso, String> nomTipoCaso;
    public static volatile CollectionAttribute<TipoCaso, Casos> casosCollection;
    public static volatile SingularAttribute<TipoCaso, Integer> idTipoCaso;

}