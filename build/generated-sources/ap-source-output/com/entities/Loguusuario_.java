package com.entities;

import com.entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-11-15T16:51:01")
@StaticMetamodel(Loguusuario.class)
public class Loguusuario_ { 

    public static volatile SingularAttribute<Loguusuario, String> nombreEvento;
    public static volatile SingularAttribute<Loguusuario, Date> fechaIngreso;
    public static volatile SingularAttribute<Loguusuario, Usuario> codigoUsuario;
    public static volatile SingularAttribute<Loguusuario, Integer> codigoLog;

}