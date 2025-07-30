import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class TestSQLiteRead {
    public static void main(String[] args) {
        try {
            // Cargar driver SQLite
            Class.forName("org.sqlite.JDBC");
            
            // Conectar a la base de datos
            Connection conn = DriverManager.getConnection("jdbc:sqlite:buffet_abogados.db");
            System.out.println("✅ Conectado a SQLite");
            
            // Insertar datos de prueba
            String insertSQL = "INSERT OR IGNORE INTO usuarios (nombres, apellidos, usuario, password, rol, activo) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, "Juan");
            pstmt.setString(2, "Pérez");
            pstmt.setString(3, "juan.perez");
            pstmt.setString(4, "123456");
            pstmt.setString(5, "Cliente");
            pstmt.setBoolean(6, true);
            
            int inserted = pstmt.executeUpdate();
            System.out.println("📝 Registros insertados: " + inserted);
            
            // Leer todos los usuarios
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombres, apellidos, usuario, rol, activo FROM usuarios");
            
            System.out.println("\n📋 USUARIOS EN LA BASE DE DATOS:");
            System.out.println("ID | Nombres | Apellidos | Usuario | Rol | Activo");
            System.out.println("---|---------|-----------|---------|-----|-------");
            
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %s | %s%n",
                    rs.getInt("id"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"), 
                    rs.getString("usuario"),
                    rs.getString("rol"),
                    rs.getBoolean("activo") ? "Sí" : "No"
                );
            }
            
            // Verificar estructura de la tabla
            ResultSet tableInfo = stmt.executeQuery("PRAGMA table_info(usuarios)");
            System.out.println("\n🗂️ ESTRUCTURA DE LA TABLA USUARIOS:");
            while (tableInfo.next()) {
                System.out.printf("Campo: %s | Tipo: %s | No Null: %s%n",
                    tableInfo.getString("name"),
                    tableInfo.getString("type"),
                    tableInfo.getInt("notnull") == 1 ? "Sí" : "No"
                );
            }
            
            conn.close();
            System.out.println("\n✅ Prueba de lectura completada");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}