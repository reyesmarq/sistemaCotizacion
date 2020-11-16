package com.entities;

import com.entities.Detalleingreso;
import com.entities.Empleado;
import com.entities.Proveedor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Ingreso.class)
public class Ingreso_ { 

    public static volatile ListAttribute<Ingreso, Detalleingreso> detalleingresoList;
    public static volatile SingularAttribute<Ingreso, String> fecha;
    public static volatile SingularAttribute<Ingreso, Integer> estado;
    public static volatile SingularAttribute<Ingreso, Empleado> codigoEmpleado;
    public static volatile SingularAttribute<Ingreso, Integer> idIngreso;
    public static volatile SingularAttribute<Ingreso, Proveedor> idProveedor;
    public static volatile SingularAttribute<Ingreso, String> serie;
    public static volatile SingularAttribute<Ingreso, String> tipoComprobante;
    public static volatile SingularAttribute<Ingreso, String> correlativo;

}