import buffetabogados.modelo.Conexion;
import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIONES ===");
        
        try {
            // Obtener instancia de conexión
            Conexion conexionManager = Conexion.getInstancia();
            
            System.out.println("Tipo de BD configurado: " + conexionManager.getTipoDB());
            System.out.println("Host: " + conexionManager.getHost());
            System.out.println("Puerto: " + conexionManager.getPuerto());
            System.out.println("Base de datos: " + conexionManager.getNombreDB());
            System.out.println("Usuario: " + conexionManager.getUsuario());
            
            // Probar conexión
            Connection conn = conexionManager.getConexion();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión exitosa!");
                
                // Probar una consulta simple
                var stmt = conn.createStatement();
                var rs = stmt.executeQuery("SELECT COUNT(*) as total FROM usuarios");
                if (rs.next()) {
                    System.out.println("📊 Total usuarios: " + rs.getInt("total"));
                }
                rs.close();
                stmt.close();
                
            } else {
                System.out.println("❌ No se pudo conectar");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}