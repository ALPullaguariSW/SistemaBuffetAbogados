import buffetabogados.vista.Login;

/**
 * Clase principal del sistema de Buffet de Abogados
 * Punto de entrada de la aplicación
 */
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
        
        // Iniciar la aplicación en el EDT (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Mostrar ventana de login
                    new Login();
                    
                    System.out.println("Sistema de Buffet de Abogados iniciado correctamente");
                    System.out.println("Usuario por defecto: admin");
                    System.out.println("Contraseña por defecto: admin123");
                    
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                    
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación:\n" + e.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
} 