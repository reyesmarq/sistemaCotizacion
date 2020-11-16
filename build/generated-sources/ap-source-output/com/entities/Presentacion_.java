package com.entities;

import com.entities.Articulo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Presentacion.class)
public class Presentacion_ { 

    public static volatile SingularAttribute<Presentacion, String> descripcion;
    public static volatile ListAttribute<Presentacion, Articulo> articuloList;
    public static volatile SingularAttribute<Presentacion, Integer> idPresentacion;
    public static volatile SingularAttribute<Presentacion, Integer> estado;
    public static volatile SingularAttribute<Presentacion, String> nombre;

}