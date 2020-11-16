package com.entities;

import com.entities.Ingreso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ { 

    public static volatile SingularAttribute<Proveedor, String> tipoDocumento;
    public static volatile SingularAttribute<Proveedor, String> razonSocial;
    public static volatile SingularAttribute<Proveedor, Integer> estado;
    public static volatile ListAttribute<Proveedor, Ingreso> ingresoList;
    public static volatile SingularAttribute<Proveedor, String> sectorComercial;
    public static volatile SingularAttribute<Proveedor, Integer> idProveedor;
    public static volatile SingularAttribute<Proveedor, String> direccion;
    public static volatile SingularAttribute<Proveedor, String> numeroDocumento;
    public static volatile SingularAttribute<Proveedor, String> telefono;
    public static volatile SingularAttribute<Proveedor, String> email;

}