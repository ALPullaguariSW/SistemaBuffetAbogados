package buffetabogados.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones CRUD de clientes
 */
public class ClienteDAO {
    
    private Conexion conexion;
    
    public ClienteDAO() {
        this.conexion = Conexion.getInstancia();
    }
    
    /**
     * Inserta un nuevo cliente en la base de datos
     */
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombres, apellidos, direccion, telefono, correo, dui, fecha_nacimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getDui());
            pstmt.setDate(7, cliente.getFechaNacimiento());
            
            int resultado = pstmt.executeUpdate();
            pstmt.close();
            
            return resultado > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Obtiene todos los clientes de la base de datos
     */
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombres, apellidos";
        
        try {
            Connection conn = conexion.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("dui"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getDate("fecha_registro")
                );
                clientes.add(cliente);
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    /**
     * Busca un cliente por ID
     */
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("dui"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getDate("fecha_registro")
                );
                
                rs.close();
                pstmt.close();
                return cliente;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Busca clientes por nombre
     */
    public List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE LOWER(nombres) LIKE ? OR LOWER(apellidos) LIKE ? ORDER BY nombres, apellidos";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String patron = "%" + nombre.toLowerCase() + "%";
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("dui"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getDate("fecha_registro")
                );
                clientes.add(cliente);
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    /**
     * Busca un cliente por DUI
     */
    public Cliente buscarPorDui(String dui) {
        String sql = "SELECT * FROM clientes WHERE dui = ?";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dui);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("dui"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getDate("fecha_registro")
                );
                
                rs.close();
                pstmt.close();
                return cliente;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Actualiza un cliente existente
     */
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombres = ?, apellidos = ?, direccion = ?, telefono = ?, correo = ?, dui = ?, fecha_nacimiento = ? WHERE id = ?";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getCorreo());
            pstmt.setString(6, cliente.getDui());
            pstmt.setDate(7, cliente.getFechaNacimiento());
            pstmt.setInt(8, cliente.getId());
            
            int resultado = pstmt.executeUpdate();
            pstmt.close();
            
            return resultado > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Elimina un cliente por ID
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int resultado = pstmt.executeUpdate();
            pstmt.close();
            
            return resultado > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verifica si existe un cliente con el DUI especificado
     */
    public boolean existeDui(String dui) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE dui = ?";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dui);
            
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
     * Verifica si existe un cliente con el DUI especificado (excluyendo un ID)
     */
    public boolean existeDui(String dui, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE dui = ? AND id != ?";
        
        try {
            Connection conn = conexion.getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dui);
            pstmt.setInt(2, idExcluir);
            
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
     * Obtiene el total de clientes registrados
     */
    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM clientes";
        
        try {
            Connection conn = conexion.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            rs.next();
            int total = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            return total;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}