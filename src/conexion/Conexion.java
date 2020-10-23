/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *  Nombre de la clase: Conexion
 *  Fecha: 10-22-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class Conexion {
    Connection con;

    public Connection getCon() {
        return con;
    }
    public boolean conectar()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/proyectoCotizaciones",
                    "root","");
            return true;
            
        } catch (SQLException |ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar: "+e.getMessage(),"Error",0);
              return false;
        }
    }
    public boolean desconectar()
    {
        try {
            if(con!=null)
            {
                if(con.isClosed()==false)
                {
                    con.close();
                }
            }
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al desconectar"+e.getMessage(),"Error",0);
              return false;
        }
    }
}
