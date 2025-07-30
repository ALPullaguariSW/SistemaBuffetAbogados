package buffeteabogados.controlador;

import buffeteabogados.modelo.Usuario;
import buffeteabogados.vista.Login;
import buffeteabogados.util.Validaciones;

import javax.swing.JOptionPane;
import java.util.Random;

/**
 * Controlador para el manejo de autenticación
 * @author axel_
 */
public class LoginController {
    
    private Login vista;
    
    public LoginController(Login vista) {
        this.vista = vista;
    }
    
    /**
     * Autentica un usuario con credenciales
     * @param usuario nombre de usuario
     * @param password contraseña
     * @return Usuario autenticado o null si falla
     */
    public Usuario autenticarUsuario(String usuario, String password) {
        try {
            // Validaciones básicas
            if (Validaciones.campoVacio(usuario) || Validaciones.campoVacio(password)) {
                JOptionPane.showMessageDialog(vista,
                    "Usuario y contraseña son obligatorios",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
            // Usuario administrador por defecto
            if ("admin".equals(usuario) && "admin123".equals(password)) {
                return new Usuario(1, "Administrador", "Sistema", "admin", "admin123", "Abogado");
            }
            
            // TODO: Aquí se implementaría la consulta a base de datos
            // Por ahora solo valida el usuario admin
            
            JOptionPane.showMessageDialog(vista,
                "Usuario o contraseña incorrectos",
                "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            return null;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista,
                "Error al validar credenciales: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Registra un nuevo usuario
     * @param usuario objeto Usuario con los datos
     * @return true si se registró exitosamente
     */
    public boolean registrarUsuario(Usuario usuario) {
        try {
            // Validar datos del usuario
            if (!validarDatosUsuario(usuario)) {
                return false;
            }
            
            // Verificar si el usuario ya existe
            if (usuarioExiste(usuario.getUsuario())) {
                JOptionPane.showMessageDialog(vista,
                    "El nombre de usuario ya existe",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // TODO: Aquí se implementaría la inserción en base de datos
            // Por ahora simula el registro exitoso
            
            JOptionPane.showMessageDialog(vista,
                "Usuario registrado exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista,
                "Error al registrar usuario: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Valida los datos del usuario antes del registro
     * @param usuario objeto Usuario a validar
     * @return true si todos los datos son válidos
     */
    private boolean validarDatosUsuario(Usuario usuario) {
        // Validar nombres
        if (Validaciones.campoVacio(usuario.getNombres())) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Nombres"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.nombreValido(usuario.getNombres())) {
            JOptionPane.showMessageDialog(vista,
                "Los nombres solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar apellidos
        if (Validaciones.campoVacio(usuario.getApellidos())) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Apellidos"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.nombreValido(usuario.getApellidos())) {
            JOptionPane.showMessageDialog(vista,
                "Los apellidos solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar usuario
        if (Validaciones.campoVacio(usuario.getUsuario())) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Usuario"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.usuarioValido(usuario.getUsuario())) {
            JOptionPane.showMessageDialog(vista,
                "El usuario debe contener al menos 3 caracteres (letras, números y guiones bajos)",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar contraseña
        if (Validaciones.campoVacio(usuario.getPassword())) {
            JOptionPane.showMessageDialog(vista,
                Validaciones.mensajeCampoVacio("Contraseña"),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.passwordValida(usuario.getPassword())) {
            JOptionPane.showMessageDialog(vista,
                "La contraseña debe tener al menos 6 caracteres",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    /**
     * Verifica si un usuario ya existe
     * @param nombreUsuario nombre de usuario a verificar
     * @return true si el usuario existe
     */
    public boolean usuarioExiste(String nombreUsuario) {
        // Usuario admin siempre existe
        if ("admin".equals(nombreUsuario)) {
            return true;
        }
        
        // TODO: Implementar consulta a base de datos
        return false;
    }
    
    /**
     * Genera un PIN aleatorio para recuperación de contraseña
     * @return PIN de 6 dígitos
     */
    public String generarPIN() {
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000); // PIN de 6 dígitos
        return String.valueOf(pin);
    }
    
    /**
     * Verifica si un usuario existe para recuperación de contraseña
     * @param usuarioOEmail usuario o email
     * @return true si existe
     */
    public boolean usuarioExisteParaRecuperacion(String usuarioOEmail) {
        // TODO: Implementar consulta a base de datos
        // Por ahora solo valida el admin
        return "admin".equals(usuarioOEmail) || "admin@buffet.com".equals(usuarioOEmail);
    }
    
    /**
     * Actualiza la contraseña de un usuario
     * @param usuario nombre de usuario
     * @param nuevaPassword nueva contraseña
     * @return true si se actualizó exitosamente
     */
    public boolean actualizarPassword(String usuario, String nuevaPassword) {
        try {
            // Validar nueva contraseña
            if (!Validaciones.passwordValida(nuevaPassword)) {
                JOptionPane.showMessageDialog(vista,
                    "La contraseña debe tener al menos 6 caracteres",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // TODO: Implementar actualización en base de datos
            
            JOptionPane.showMessageDialog(vista,
                "Contraseña actualizada exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista,
                "Error al actualizar contraseña: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}