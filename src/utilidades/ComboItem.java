package utilidades;

/**
 *  Nombre de la clase: ComboItem
 *  Fecha: 10-22-2020 (m/d/a)
 *  Versión: 1.0
 *  CopyRight: Ulises Guzmán
 *  @author Ulises Guzmán
 */

public class ComboItem {
    private int value;
    private String label;

    public ComboItem() {
    }

    public ComboItem(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

  @Override
    public String toString() {
        return label;
    }

   
    
}
