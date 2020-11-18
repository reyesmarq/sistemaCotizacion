/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilidades;

/**
 *  Nombre de la clase: comunicacionAcceso
 *  Fecha: 11-17-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class ComunicacionAcceso {
    private static String id="";
    private static String usuario="";
    private static String acceso="";

    /**
     * @return the id
     */
    public static String getId() {
        return id;
    }

    /**
     * @param aId the id to set
     */
    public static void setId(String aId) {
        id = aId;
    }

    /**
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * @param aUsuario the usuario to set
     */
    public static void setUsuario(String aUsuario) {
        usuario = aUsuario;
    }

    /**
     * @return the acceso
     */
    public static String getAcceso() {
        return acceso;
    }

    /**
     * @param aAcceso the acceso to set
     */
    public static void setAcceso(String aAcceso) {
        acceso = aAcceso;
    }
    
}
