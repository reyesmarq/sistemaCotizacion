package com.entities;

import com.entities.Cliente;
import com.entities.Detalleventa;
import com.entities.Empleado;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile ListAttribute<Venta, Detalleventa> detalleventaList;
    public static volatile SingularAttribute<Venta, String> fecha;
    public static volatile SingularAttribute<Venta, Integer> estado;
    public static volatile SingularAttribute<Venta, Integer> cotizacion;
    public static volatile SingularAttribute<Venta, Empleado> codigoEmpleado;
    public static volatile SingularAttribute<Venta, Cliente> idCliente;
    public static volatile SingularAttribute<Venta, Double> iva;
    public static volatile SingularAttribute<Venta, String> serie;
    public static volatile SingularAttribute<Venta, String> correlativo;
    public static volatile SingularAttribute<Venta, String> tipoComprobante;
    public static volatile SingularAttribute<Venta, Double> cesc;
    public static volatile SingularAttribute<Venta, Integer> idVenta;

}