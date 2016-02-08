
package Controlador;

import javax.swing.JOptionPane;

/**
 * @author Andres
 */
public class MyTools {
    public void mostrarError(String msg) {
        System.out.println(msg);
        JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
