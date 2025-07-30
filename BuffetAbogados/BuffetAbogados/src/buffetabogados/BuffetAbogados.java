package buffetabogados;

import vista.Login;
import javax.swing.SwingUtilities;

public class BuffetAbogados {
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            System.err.println("Error al configurar look and feel: " + e.getMessage());
        }
        
        // Iniciar la aplicación
        SwingUtilities.invokeLater(() -> {
            try {
                new Login();
                System.out.println("Sistema de Buffet de Abogados iniciado correctamente");
                System.out.println("Usuario por defecto: admin");
                System.out.println("Contraseña por defecto: admin123");
            } catch (Exception e) {
                System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}