package buffeteabogados.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AudienciaDAO {

    private final Conexion conexion;

    public AudienciaDAO() {
        this.conexion = Conexion.getInstancia();
    }

    public boolean insertar(Audiencia audiencia) {
        String sql = "INSERT INTO audiencias (tipo, fecha_hora, lugar, caso_id, abogado_id, estado, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, audiencia.getTipo());
            pstmt.setTimestamp(2, audiencia.getFechaHora());
            pstmt.setString(3, audiencia.getLugar());
            pstmt.setInt(4, audiencia.getCasoId());
            pstmt.setInt(5, audiencia.getAbogadoId());
            pstmt.setString(6, audiencia.getEstado());
            pstmt.setString(7, audiencia.getObservaciones());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Audiencia> obtenerTodas() {
        List<Audiencia> audiencias = new ArrayList<>();
        String sql = "SELECT a.*, c.titulo as caso_titulo, e.nombres as abogado_nombres, e.apellidos as abogado_apellidos "
                + "FROM audiencias a "
                + "LEFT JOIN casos c ON a.caso_id = c.id "
                + "LEFT JOIN empleados e ON a.abogado_id = e.id "
                + "ORDER BY a.fecha_hora DESC";
        try (Connection conn = conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                audiencias.add(mapResultSetToAudiencia(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audiencias;
    }

    /**
     * Obtiene una audiencia específica por su ID.
     *
     * @param id El ID de la audiencia a buscar.
     * @return Un objeto Audiencia si se encuentra, de lo contrario null.
     */
    public Audiencia obtenerPorId(int id) {
        String sql = "SELECT * FROM audiencias WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Usamos el mapResultSetToAudiencia sin los JOINs
                    return mapResultSetToAudiencia(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualiza una audiencia existente en la base de datos.
     *
     * @param audiencia El objeto Audiencia con los datos actualizados.
     * @return true si la operación fue exitosa.
     */
    public boolean actualizar(Audiencia audiencia) {
        String sql = "UPDATE audiencias SET tipo = ?, fecha_hora = ?, lugar = ?, caso_id = ?, abogado_id = ?, estado = ?, observaciones = ? WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, audiencia.getTipo());
            pstmt.setTimestamp(2, audiencia.getFechaHora());
            pstmt.setString(3, audiencia.getLugar());
            pstmt.setInt(4, audiencia.getCasoId());
            pstmt.setInt(5, audiencia.getAbogadoId());
            pstmt.setString(6, audiencia.getEstado());
            pstmt.setString(7, audiencia.getObservaciones());
            pstmt.setInt(8, audiencia.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una audiencia de la base de datos.
     *
     * @param id El ID de la audiencia a eliminar.
     * @return true si la operación fue exitosa.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM audiencias WHERE id = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Utilidad para que mapResultSetToAudiencia no falle ---
    // Añade este método de ayuda al final de la clase
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

    // --- Modifica tu método mapResultSetToAudiencia existente ---
    // Reemplaza tu método mapResultSetToAudiencia con este
    private Audiencia mapResultSetToAudiencia(ResultSet rs) throws SQLException {
        Audiencia audiencia = new Audiencia();
        audiencia.setId(rs.getInt("id"));
        audiencia.setTipo(rs.getString("tipo"));
        audiencia.setFechaHora(rs.getTimestamp("fecha_hora"));
        audiencia.setLugar(rs.getString("lugar"));
        audiencia.setCasoId(rs.getInt("caso_id"));
        audiencia.setAbogadoId(rs.getInt("abogado_id"));
        audiencia.setEstado(rs.getString("estado"));
        audiencia.setObservaciones(rs.getString("observaciones"));

        // Datos de las uniones (JOIN), solo si existen
        if (hasColumn(rs, "caso_titulo")) {
            audiencia.setCasoTitulo(rs.getString("caso_titulo"));
        }
        if (hasColumn(rs, "abogado_nombres")) {
            audiencia.setAbogadoNombre(rs.getString("abogado_nombres") + " " + rs.getString("abogado_apellidos"));
        }

        return audiencia;
    }
}
