package com.entities;

import com.entities.Articulo;
import com.entities.Detalleventa;
import com.entities.Ingreso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Detalleingreso.class)
public class Detalleingreso_ { 

    public static volatile ListAttribute<Detalleingreso, Detalleventa> detalleventaList;
    public static volatile SingularAttribute<Detalleingreso, Articulo> idArticulo;
    public static volatile SingularAttribute<Detalleingreso, Integer> estado;
    public static volatile SingularAttribute<Detalleingreso, String> fechaVencimiento;
    public static volatile SingularAttribute<Detalleingreso, Integer> idDetalleIngreso;
    public static volatile SingularAttribute<Detalleingreso, Double> dai;
    public static volatile SingularAttribute<Detalleingreso, Integer> stockInicial;
    public static volatile SingularAttribute<Detalleingreso, String> fechaProduccion;
    public static volatile SingularAttribute<Detalleingreso, Double> iva;
    public static volatile SingularAttribute<Detalleingreso, Ingreso> idIngreso;
    public static volatile SingularAttribute<Detalleingreso, Double> precioCompra;
    public static volatile SingularAttribute<Detalleingreso, Integer> stockActual;
    public static volatile SingularAttribute<Detalleingreso, Double> precioVenta;
    public static volatile SingularAttribute<Detalleingreso, Double> cesc;

}