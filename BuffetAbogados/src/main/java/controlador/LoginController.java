package buffetabogados.controlador;

import buffetabogados.modelo.Conexion;
import buffetabogados.modelo.Usuario;
import buffetabogados.util.Validaciones;

import java.sql.*;
import javax.swing.JOptionPane;
import java.security.SecureRandom;

/**
 * Controlador para la autenticación de usuarios
 */
public class LoginController {
    
    private Conexion conexion;
    
    public LoginController() {
        this.conexion = Conexion.getInstancia();
    }
    
    /**
     * Autentica un usuario con las credenciales proporcionadas
     */
    public Usuario autenticarUsuario(String usuario, String password) {
        // Validaciones básicas
        if (Validaciones.campoVacio(usuario) || Validaciones.campoVacio(password)) {
            JOptionPane.showMessageDialog(null,
                "Por favor complete todos los campos",
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        // Validación especial para usuario admin por defecto
        if ("admin".equals(usuario) && "admin123".equals(password)) {
            return new Usuario("Administrador", "Sistema", "admin", "admin123", "Abogado");
        }
        
        // Autenticación contra base de datos
        try {
            Connection conn = conexion.getConexion();
            String sql = "SELECT id, nombres, apellidos, usuario, password, rol, fecha_creacion " +
                        "FROM usuarios WHERE usuario = ? AND password = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Usuario user = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("usuario"),
                    rs.getString("password"),
                    rs.getString("rol"),
                    rs.getDate("fecha_creacion")
                );
                
                rs.close();
                pstmt.close();
                return user;
                
            } else {
                rs.close();
                pstmt.close();
                JOptionPane.showMessageDialog(null,
                    "Usuario o contraseña incorrectos",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error de conexión a la base de datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    /**
     * Registra un nuevo usuario en el sistema
     */
    public boolean registrarUsuario(Usuario usuario) {
        // Validaciones
        if (!Validaciones.nombreValido(usuario.getNombres())) {
            JOptionPane.showMessageDialog(null,
                "Los nombres solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.nombreValido(usuario.getApellidos())) {
            JOptionPane.showMessageDialog(null,
                "Los apellidos solo pueden contener letras y espacios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.usuarioValido(usuario.getUsuario())) {
            JOptionPane.showMessageDialog(null,
                "El usuario debe contener al menos 3 caracteres (letras, números y guiones bajos)",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Validaciones.passwordValida(usuario.getPassword())) {
            JOptionPane.showMessageDialog(null,
                "La contraseña debe tener al menos 6 caracteres",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Verificar si el usuario ya existe
        if (usuarioExiste(usuario.getUsuario())) {
            JOptionPane.showMessageDialog(null,
                "El usuario ya existe en el sistema",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Insertar usuario en la base de datos
        try {
            Connection conn = conexion.getConexion();
            String sql = "INSERT INTO usuarios (nombres, apellidos, usuario, password, rol) " +
                        "VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario.getNombres());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getUsuario());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setString(5, usuario.getRol());
            
            int resultado = pstmt.executeUpdate();
            pstmt.close();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null,
                    "Usuario registrado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Error al registrar el usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error de conexión a la base de datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Verifica si un usuario existe en la base de datos
     */
    private boolean usuarioExiste(String usuario) {
        try {
            Connection conn = conexion.getConexion();
            String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario);
            
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            
            rs.close();
            pstmt.close();
            
            return count > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Genera un PIN aleatorio para recuperación de contraseña
     */
    public String generarPIN() {
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            pin.append((int) (Math.random() * 10));
        }
        return pin.toString();
    }
    
    /**
     * Verifica si un usuario existe para recuperación de contraseña
     */
    public boolean usuarioExisteParaRecuperacion(String usuario) {
        try {
            Connection conn = conexion.getConexion();
            String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario);
            
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            
            rs.close();
            pstmt.close();
            
            return count > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza la contraseña de un usuario
     */
    public boolean actualizarPassword(String usuario, String nuevaPassword) {
        if (!Validaciones.passwordValida(nuevaPassword)) {
            JOptionPane.showMessageDialog(null,
                "La contraseña debe tener al menos 6 caracteres",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            Connection conn = conexion.getConexion();
            String sql = "UPDATE usuarios SET password = ? WHERE usuario = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nuevaPassword);
            pstmt.setString(2, usuario);
            
            int resultado = pstmt.executeUpdate();
            pstmt.close();
            
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null,
                    "Contraseña actualizada exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null,
                    "Error al actualizar la contraseña",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error de conexión a la base de datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
} 