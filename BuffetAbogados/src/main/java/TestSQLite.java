import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class TestSQLite {
    public static void main(String[] args) {
        try {
            // Cargar el driver
            Class.forName("org.sqlite.JDBC");
            System.out.println("✅ Driver SQLite cargado correctamente");
            
            // Crear conexión de prueba
            Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:");
            System.out.println("✅ Conexión a base de datos SQLite exitosa");
            
            // Crear tabla de prueba
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE test (id INTEGER, name TEXT)");
            stmt.execute("INSERT INTO test VALUES (1, 'Prueba')");
            
            // Consultar datos
            ResultSet rs = stmt.executeQuery("SELECT * FROM test");
            while (rs.next()) {
                System.out.println("✅ Datos recuperados: ID=" + rs.getInt("id") + ", Name=" + rs.getString("name"));
            }
            
            conn.close();
            System.out.println("✅ Todas las pruebas pasaron. SQLite JDBC está funcionando correctamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}