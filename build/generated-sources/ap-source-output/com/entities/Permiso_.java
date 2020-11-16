package com.entities;

import com.entities.Rolpermiso;
import com.entities.Usuariopermiso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Permiso.class)
public class Permiso_ { 

    public static volatile SingularAttribute<Permiso, Integer> codigoPermiso;
    public static volatile ListAttribute<Permiso, Rolpermiso> rolpermisoList;
    public static volatile SingularAttribute<Permiso, String> descripcionPermiso;
    public static volatile SingularAttribute<Permiso, String> nombrePermiso;
    public static volatile ListAttribute<Permiso, Usuariopermiso> usuariopermisoList;

}