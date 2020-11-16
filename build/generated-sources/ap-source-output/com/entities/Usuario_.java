package com.entities;

import com.entities.Empleado;
import com.entities.Loguusuario;
import com.entities.Rolusuario;
import com.entities.Usuariopermiso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Integer> codigoUsuario;
    public static volatile ListAttribute<Usuario, Empleado> empleadoList;
    public static volatile ListAttribute<Usuario, Loguusuario> loguusuarioList;
    public static volatile ListAttribute<Usuario, Rolusuario> rolusuarioList;
    public static volatile SingularAttribute<Usuario, String> contraUsuario;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;
    public static volatile ListAttribute<Usuario, Usuariopermiso> usuariopermisoList;

}