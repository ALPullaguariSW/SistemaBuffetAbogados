package buffeteabogados;

import buffeteabogados.vista.Login;

import javax.swing.*;

/**
 * Clase principal del sistema de gestión para buffet de abogados
 * @author axel_
 */
public class BuffetAbogados {

    /**
     * Método principal de la aplicación
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Configurar Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // Si falla, usar el Look and Feel por defecto
            System.err.println("No se pudo establecer el Look and Feel del sistema: " + ex.getMessage());
        }
        
        // Ejecutar la interfaz gráfica en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear y mostrar la ventana de login
                Login loginWindow = new Login();
                loginWindow.setVisible(true);
                loginWindow.enfocarUsuario();
            }
        });
    }
}