package com.entities;

import com.entities.Detalleingreso;
import com.entities.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Detalleventa.class)
public class Detalleventa_ { 

    public static volatile SingularAttribute<Detalleventa, Integer> estado;
    public static volatile SingularAttribute<Detalleventa, Double> descuento;
    public static volatile SingularAttribute<Detalleventa, Integer> idDetalleVenta;
    public static volatile SingularAttribute<Detalleventa, Integer> cantidad;
    public static volatile SingularAttribute<Detalleventa, Double> precioVenta;
    public static volatile SingularAttribute<Detalleventa, Detalleingreso> idDetalleIngreso;
    public static volatile SingularAttribute<Detalleventa, Venta> idVenta;

}