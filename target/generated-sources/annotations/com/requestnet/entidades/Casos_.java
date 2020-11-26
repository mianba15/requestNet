package com.requestnet.entidades;

import com.requestnet.entidades.Estados;
import com.requestnet.entidades.IntegranteColaborador;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.Tecnico;
import com.requestnet.entidades.TipoCaso;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-24T16:14:44")
@StaticMetamodel(Casos.class)
public class Casos_ { 

    public static volatile SingularAttribute<Casos, String> descripcion;
    public static volatile SingularAttribute<Casos, Date> fechaSol;
    public static volatile SingularAttribute<Casos, Estados> idEstado;
    public static volatile SingularAttribute<Casos, Jefeinfraestructura> idJefe;
    public static volatile SingularAttribute<Casos, IntegranteColaborador> idCliente;
    public static volatile SingularAttribute<Casos, String> notas;
    public static volatile SingularAttribute<Casos, Integer> idCaso;
    public static volatile SingularAttribute<Casos, Date> fechaCreacion;
    public static volatile SingularAttribute<Casos, Liderinventario> idLider;
    public static volatile SingularAttribute<Casos, Tecnico> idTecnico;
    public static volatile SingularAttribute<Casos, String> solucion;
    public static volatile SingularAttribute<Casos, TipoCaso> idTipoCaso;

}