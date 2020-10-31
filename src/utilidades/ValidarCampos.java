package utilidades;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;
/**
 *  Nombre de la clase: ValidarCampos
 *  Fecha: 10-22-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */
public class ValidarCampos {
    
    public ValidarCampos() {
    }

    /**
     * validar solo numeros
     *
     * @param evt
     */
    public void soloNumeros(KeyEvent evt) {
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }
    /**
     * validar solo palabras
     *
     * @param evt
     */
    public void soloLetras(KeyEvent evt) {
        if (!Character.isLetter(evt.getKeyChar()) && evt.getKeyChar() != KeyEvent.VK_SPACE) {
            evt.consume();
        }
    }
    /**
     * validar solo números y un punto
     *
     * @param evt
     */
    public void numerosYPunto(KeyEvent evt,JTextField textField) {
        if (!Character.isDigit(evt.getKeyChar()) && evt.getKeyChar() != '.') {
            evt.consume();
        }
        if(evt.getKeyChar()=='.'&& textField.getText().contains("."))
        {
            evt.consume();
        }
    }   
}
