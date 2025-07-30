import buffetabogados.modelo.Conexion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN A BASE DE DATOS ===");
        
        try {
            // Obtener conexión
            Conexion conexionDB = Conexion.getInstancia();
            Connection conn = conexionDB.getConexion();
            
            System.out.println("✅ Conexión exitosa!");
            System.out.println("📊 Tipo de BD: " + conexionDB.getTipoDB());
            
            // Consultar usuarios
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
            
            System.out.println("\n👥 USUARIOS EN LA BASE DE DATOS:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("nombres") + " " + 
                                 rs.getString("apellidos") + " (" + 
                                 rs.getString("usuario") + ")");
            }
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            System.out.println("💡 Asegúrate de que el archivo configuracion.properties existe");
        }
    }
}