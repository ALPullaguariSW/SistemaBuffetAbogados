package buffeteabogados.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private final Conexion conexion;

    public EmpleadoDAO() {
        this.conexion = Conexion.getInstancia();
    }

    private Empleado mapResultSetToEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getInt("id"));
        empleado.setNombres(rs.getString("nombres"));
        empleado.setApellidos(rs.getString("apellidos"));
        empleado.setEmail(rs.getString("email"));
        empleado.setTelefono(rs.getString("telefono"));
        empleado.setCargo(rs.getString("cargo"));
        empleado.setFechaContratacion(rs.getDate("fecha_contratacion"));
        empleado.setSalario(rs.getDouble("salario"));
        empleado.setActivo(rs.getBoolean("estado"));
        return empleado;
    }// CREATE
    public boolean insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombres, apellidos, email, telefono, cargo, fecha_contratacion, salario, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, empleado.getNombres());
            pstmt.setString(2, empleado.getApellidos());
            pstmt.setString(3, empleado.getEmail());
            pstmt.setString(4, empleado.getTelefono());
            pstmt.setString(5, empleado.getCargo());
            pstmt.setDate(6, empleado.getFechaContratacion());
            pstmt.setDouble(7, empleado.getSalario());
            pstmt.setBoolean(8, empleado.isActivo());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ (Todos)
    public List<Empleado> obtenerTodos() {
        List<Empleado> empleados = new ArrayList<>();
        // Mostramos solo los empleados activos
        String sql = "SELECT * FROM empleados WHERE estado = 1 ORDER BY nombres, apellidos";
        try (Connection conn = conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                empleados.add(mapResultSetToEmpleado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }
    
    // READ (Por ID) - **NECESARIO PARA EDITAR**
    public Empleado obtenerPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE - **NECESARIO PARA EDITAR**
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombres = ?, apellidos = ?, email = ?, telefono = ?, cargo = ?, fecha_contratacion = ?, salario = ?, estado = ? WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getNombres());
            pstmt.setString(2, empleado.getApellidos());
            pstmt.setString(3, empleado.getEmail());
            pstmt.setString(4, empleado.getTelefono());
            pstmt.setString(5, empleado.getCargo());
            pstmt.setDate(6, empleado.getFechaContratacion());
            pstmt.setDouble(7, empleado.getSalario());
            pstmt.setBoolean(8, empleado.isActivo());
            pstmt.setInt(9, empleado.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE (Soft Delete) - **NECESARIO PARA ELIMINAR**
    public boolean eliminar(int id) {
        // En lugar de borrar, cambiamos el estado a 0 (inactivo)
        String sql = "UPDATE empleados SET estado = 0 WHERE id = ?";
        try (Connection conn = conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
