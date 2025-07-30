import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestMySQL {
    public static void main(String[] args) {
        try {
            // Cargar el driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
            
            // URL de conexión (ajustar según tu configuración)
            String url = "jdbc:mysql://localhost:3306/buffet_abogados?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String usuario = "root";
            String password = ""; // Ajustar según tu configuración
            
            // Crear conexión
            Connection conn = DriverManager.getConnection(url, usuario, password);
            System.out.println("✅ Conexión a MySQL exitosa");
            
            // Probar consulta
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM usuarios");
            if (rs.next()) {
                System.out.println("✅ Usuarios en la base de datos: " + rs.getInt("total"));
            }
            
            conn.close();
            System.out.println("✅ Todas las pruebas MySQL pasaron correctamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error de conexión MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}