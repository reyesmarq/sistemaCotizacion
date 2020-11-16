package com.entities;

import com.entities.Categoria;
import com.entities.Detalleingreso;
import com.entities.Presentacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Articulo.class)
public class Articulo_ { 

    public static volatile SingularAttribute<Articulo, Integer> idArticulo;
    public static volatile SingularAttribute<Articulo, String> descripcion;
    public static volatile ListAttribute<Articulo, Detalleingreso> detalleingresoList;
    public static volatile SingularAttribute<Articulo, String> codigo;
    public static volatile SingularAttribute<Articulo, Presentacion> idPresentacion;
    public static volatile SingularAttribute<Articulo, Categoria> idCategoria;
    public static volatile SingularAttribute<Articulo, String> nombre;

}