package buffeteabogados.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final Conexion conexion;

    public ClienteDAO() {
        this.conexion = Conexion.getInstancia();
    }

    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombres, apellidos, direccion, telefono, correo, dui, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getDui());
            pstmt.setDate(7, cliente.getFechaNacimiento());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombres, apellidos";
        try (Connection conn = conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(mapResultSetToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombres = ?, apellidos = ?, direccion = ?, telefono = ?, correo = ?, dui = ?, fecha_nacimiento = ? WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getDui());
            pstmt.setDate(7, cliente.getFechaNacimiento());
            pstmt.setInt(8, cliente.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Podría fallar si hay casos asociados (llave foránea)
            System.err.println("Error al eliminar cliente, puede tener dependencias: " + e.getMessage());
            return false;
        }
    }
    
    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setNombres(rs.getString("nombres"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setDui(rs.getString("dui"));
        cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
        cliente.setFechaRegistro(rs.getDate("fecha_registro"));
        return cliente;
    }
    // Otros métodos como buscarPorNombre, existeDui, etc., pueden ser añadidos aquí.
}