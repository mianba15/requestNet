package com.requestnet.entidades;

import com.requestnet.entidades.EstadoEq;
import com.requestnet.entidades.IntegranteColaborador;
import com.requestnet.entidades.Jefeinfraestructura;
import com.requestnet.entidades.Liderinventario;
import com.requestnet.entidades.Marca;
import com.requestnet.entidades.TipoComponente;
import com.requestnet.entidades.TipoProcedencia;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-12-08T20:29:01")
@StaticMetamodel(InventarioEquipos.class)
public class InventarioEquipos_ { 

    public static volatile SingularAttribute<InventarioEquipos, Integer> idEquipo;
    public static volatile SingularAttribute<InventarioEquipos, TipoComponente> idTipoComponente;
    public static volatile SingularAttribute<InventarioEquipos, IntegranteColaborador> idCliente;
    public static volatile SingularAttribute<InventarioEquipos, Jefeinfraestructura> idJefe;
    public static volatile SingularAttribute<InventarioEquipos, String> serial;
    public static volatile SingularAttribute<InventarioEquipos, Liderinventario> idLider;
    public static volatile SingularAttribute<InventarioEquipos, Marca> idMarca;
    public static volatile SingularAttribute<InventarioEquipos, EstadoEq> idEstadoeq;
    public static volatile SingularAttribute<InventarioEquipos, TipoProcedencia> idTipoProcedencia;

}