package buffetabogados;

import vista.Login;
import javax.swing.SwingUtilities;

public class BuffetAbogados {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Login();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}