package com.entities;

import com.entities.Ingreso;
import com.entities.Usuario;
import com.entities.Venta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Empleado.class)
public class Empleado_ { 

    public static volatile ListAttribute<Empleado, Ingreso> ingresoList;
    public static volatile SingularAttribute<Empleado, Integer> codigoEmpleado;
    public static volatile SingularAttribute<Empleado, String> duiEmpleado;
    public static volatile SingularAttribute<Empleado, String> direccionEmpleado;
    public static volatile SingularAttribute<Empleado, Date> fechaNacimientoEmpleado;
    public static volatile SingularAttribute<Empleado, String> cargoEmpleado;
    public static volatile SingularAttribute<Empleado, Usuario> codigoUsuario;
    public static volatile ListAttribute<Empleado, Venta> ventaList;
    public static volatile SingularAttribute<Empleado, String> nitEmpleado;
    public static volatile SingularAttribute<Empleado, String> nombreEmpleado;
    public static volatile SingularAttribute<Empleado, String> telefonoEmpleado;

}