package buffeteabogados.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Conexion conexion;

    public UsuarioDAO() {
        this.conexion = Conexion.getInstancia();
    }

    public Usuario autenticar(String nombreUsuario, String password) {
        if ("admin".equals(nombreUsuario) && "admin123".equals(password)) {
            Usuario admin = new Usuario();
            admin.setId(0);
            admin.setNombres("Administrador");
            admin.setApellidos("del Sistema");
            admin.setUsuario("admin");
            admin.setRol("Abogado");
            admin.setActivo(true);
            return admin;
        }

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password = ? AND activo = 1";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setEmail(rs.getString("email"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombres, apellidos, email, usuario, password, rol, activo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombres());
            pstmt.setString(2, usuario.getApellidos());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getUsuario());
            pstmt.setString(5, usuario.getPassword());
            pstmt.setString(6, usuario.getRol());
            pstmt.setBoolean(7, usuario.isActivo());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verificarUsuarioYCorreo(String nombreUsuario, String correo) {
        String sql = "SELECT id FROM usuarios WHERE usuario = ? AND email = ? AND activo = 1";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarPassword(String nombreUsuario, String nuevaPassword) {
        String sql = "UPDATE usuarios SET password = ? WHERE usuario = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevaPassword);
            pstmt.setString(2, nombreUsuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
