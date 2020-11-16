package com.entities;

import com.entities.Permiso;
import com.entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Usuariopermiso.class)
public class Usuariopermiso_ { 

    public static volatile SingularAttribute<Usuariopermiso, Permiso> codigoPermiso;
    public static volatile SingularAttribute<Usuariopermiso, Usuario> codigoUsuario;
    public static volatile SingularAttribute<Usuariopermiso, Integer> codigoUsuarioPermiso;

}