package buffeteabogados.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasoDAO {

    private final Conexion conexion;

    public CasoDAO() {
        this.conexion = Conexion.getInstancia();
    }

    public boolean insertar(Caso caso) {
        String sql = "INSERT INTO casos (titulo, descripcion, tipo_caso, estado, fecha_inicio, cliente_id, abogado_id, prioridad, estimacion_costo, fecha_cierre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, caso.getTitulo());
            pstmt.setString(2, caso.getDescripcion());
            pstmt.setString(3, caso.getTipoCaso());
            pstmt.setString(4, caso.getEstado());
            pstmt.setDate(5, caso.getFechaInicio());
            pstmt.setInt(6, caso.getClienteId());
            pstmt.setInt(7, caso.getAbogadoId());
            pstmt.setString(8, caso.getPrioridad());
            pstmt.setDouble(9, caso.getEstimacionCosto());
            pstmt.setDate(10, caso.getFechaCierre());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Caso> obtenerTodos() {
        List<Caso> casos = new ArrayList<>();
        String sql = "SELECT c.*, cl.nombres as cliente_nombres, cl.apellidos as cliente_apellidos, "
                + "e.nombres as abogado_nombres, e.apellidos as abogado_apellidos "
                + "FROM casos c "
                + "LEFT JOIN clientes cl ON c.cliente_id = cl.id "
                + "LEFT JOIN empleados e ON c.abogado_id = e.id "
                + "ORDER BY c.fecha_inicio DESC";
        try (Connection conn = conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                casos.add(mapResultSetToCaso(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return casos;
    }

    /**
     * Obtiene un caso específico por su ID.
     *
     * @param id El ID del caso a buscar.
     * @return Un objeto Caso si se encuentra, de lo contrario null.
     */
    public Caso obtenerPorId(int id) {
        String sql = "SELECT * FROM casos WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Usamos el mapResultSetToCaso pero sin los JOINs, por eso
                    // el clienteNombre y abogadoNombre vendrán nulos.
                    // Se cargarán por separado en el controlador si es necesario.
                    return mapResultSetToCaso(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza un caso existente en la base de datos.
     *
     * @param caso El objeto Caso con los datos actualizados.
     * @return true si la operación fue exitosa.
     */
    public boolean actualizar(Caso caso) {
        String sql = "UPDATE casos SET titulo = ?, descripcion = ?, tipo_caso = ?, estado = ?, fecha_inicio = ?, cliente_id = ?, abogado_id = ?, prioridad = ?, estimacion_costo = ?, fecha_cierre = ? WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, caso.getTitulo());
            pstmt.setString(2, caso.getDescripcion());
            pstmt.setString(3, caso.getTipoCaso());
            pstmt.setString(4, caso.getEstado());
            pstmt.setDate(5, caso.getFechaInicio());
            pstmt.setInt(6, caso.getClienteId());
            pstmt.setInt(7, caso.getAbogadoId());
            pstmt.setString(8, caso.getPrioridad());
            pstmt.setDouble(9, caso.getEstimacionCosto());
            pstmt.setDate(10, caso.getFechaCierre());
            pstmt.setInt(11, caso.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un caso de la base de datos. CUIDADO: Esta es una eliminación
     * física. Puede fallar si hay audiencias asociadas.
     *
     * @param id El ID del caso a eliminar.
     * @return true si la operación fue exitosa.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM casos WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el caso. Puede tener audiencias asociadas: " + e.getMessage());
            return false;
        }
    }

    // --- Utilidad para que mapResultSetToCaso no falle ---
    // Añade este método de ayuda al final de la clase para evitar errores
    // cuando una consulta no incluye todas las columnas de los JOINs.
    private static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equalsIgnoreCase(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    // --- Modifica tu método mapResultSetToCaso existente ---
    // Reemplaza tu método mapResultSetToCaso con este para que sea más robusto.
    private Caso mapResultSetToCaso(ResultSet rs) throws SQLException {
        Caso caso = new Caso();
        caso.setId(rs.getInt("id"));
        caso.setTitulo(rs.getString("titulo"));
        caso.setDescripcion(rs.getString("descripcion"));
        caso.setTipoCaso(rs.getString("tipo_caso"));
        caso.setEstado(rs.getString("estado"));
        caso.setFechaInicio(rs.getDate("fecha_inicio"));
        caso.setFechaCierre(rs.getDate("fecha_cierre"));
        caso.setClienteId(rs.getInt("cliente_id"));
        caso.setAbogadoId(rs.getInt("abogado_id"));
        caso.setPrioridad(rs.getString("prioridad"));
        caso.setEstimacionCosto(rs.getDouble("estimacion_costo"));

        // Datos de las uniones (JOIN), solo si existen en el resultado
        if (hasColumn(rs, "cliente_nombres")) {
            caso.setClienteNombre(rs.getString("cliente_nombres") + " " + rs.getString("cliente_apellidos"));
        }
        if (hasColumn(rs, "abogado_nombres")) {
            caso.setAbogadoNombre(rs.getString("abogado_nombres") + " " + rs.getString("abogado_apellidos"));
        }

        return caso;
    }
}
