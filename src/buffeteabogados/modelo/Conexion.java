package buffeteabogados.modelo;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;
    private String tipoDB;
    private String host;
    private String puerto;
    private String nombreDB;
    private String usuario;
    private String password;

    private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
    private static final String CONFIG_FILE = "configuracion.properties";

    private Conexion() {
        cargarConfiguracion();
    }

    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    private void cargarConfiguracion() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            props.load(input);
            tipoDB = props.getProperty("tipoDB", "SQLite");
            host = props.getProperty("host", "localhost");
            puerto = props.getProperty("puerto", "3306");
            nombreDB = props.getProperty("nombreDB", "buffet_abogados");
            usuario = props.getProperty("usuario", "root");
            password = props.getProperty("password", "");
        } catch (IOException ex) {
            System.out.println("Archivo de configuración no encontrado, creando uno por defecto con SQLite.");
            tipoDB = "SQLite";
            host = "localhost";
            puerto = "3306";
            nombreDB = "buffet_abogados";
            usuario = "root";
            password = "";
            guardarConfiguracion(tipoDB, host, puerto, nombreDB, usuario, password);
        }
    }

    public void guardarConfiguracion(String tipoDB, String host, String puerto,
            String nombreDB, String usuario, String password) {
        Properties props = new Properties();
        props.setProperty("tipoDB", tipoDB);
        props.setProperty("host", host);
        props.setProperty("puerto", puerto);
        props.setProperty("nombreDB", nombreDB);
        props.setProperty("usuario", usuario);
        props.setProperty("password", password);

        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.store(fos, "Configuracion de Base de Datos");
            this.tipoDB = tipoDB;
            this.host = host;
            this.puerto = puerto;
            this.nombreDB = nombreDB;
            this.usuario = usuario;
            this.password = password;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean probarConexion(String tipoDB, String host, String puerto,
            String nombreDB, String usuario, String password) {
        try {
            String url = construirURL(tipoDB, host, puerto, nombreDB);
            Class.forName(obtenerDriver(tipoDB));
            Connection testConexion = DriverManager.getConnection(url, usuario, password);
            testConexion.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conectar();
        }
        return conexion;
    }

    private void conectar() throws SQLException {
        try {
            String url = construirURL(tipoDB, host, puerto, nombreDB);
            Class.forName(obtenerDriver(tipoDB));

            if ("SQLite".equalsIgnoreCase(tipoDB)) {
                conexion = DriverManager.getConnection(url);
            } else {
                conexion = DriverManager.getConnection(url, usuario, password);
            }

            if ("SQLite".equalsIgnoreCase(tipoDB)) {
                crearTablasSiNoExisten();
            }

        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC no encontrado: " + e.getMessage());
        }
    }

    private String construirURL(String tipo, String h, String p, String db) {
        if ("MySQL".equalsIgnoreCase(tipo)) {
            return "jdbc:mysql://" + h + ":" + p + "/" + db + "?useSSL=false&serverTimezone=UTC";
        }
        return "jdbc:sqlite:" + db + ".db";
    }

    private String obtenerDriver(String tipo) {
        return "MySQL".equalsIgnoreCase(tipo) ? DRIVER_MYSQL : DRIVER_SQLITE;
    }

    private void crearTablasSiNoExisten() {
        String[] sqls = {
            // --- Clientes ---
            "CREATE TABLE IF NOT EXISTS clientes ("
            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "    nombres TEXT NOT NULL,"
            + "    apellidos TEXT NOT NULL,"
            + "    direccion TEXT,"
            + "    telefono TEXT,"
            + "    correo TEXT UNIQUE,"
            + "    dui TEXT UNIQUE,"
            + "    fecha_nacimiento DATE,"
            + "    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ");",

            // --- Empleados ---
            "CREATE TABLE IF NOT EXISTS empleados ("
            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "    nombres TEXT NOT NULL,"
            + "    apellidos TEXT NOT NULL,"
            + "    email TEXT UNIQUE,"
            + "    telefono TEXT,"
            + "    cargo TEXT,"
            + "    fecha_contratacion DATE,"
            + "    salario REAL,"
            + "    estado BOOLEAN NOT NULL DEFAULT 1"
            + ");",

            // --- Usuarios (CON LA ESTRUCTURA CORRECTA) ---
            "CREATE TABLE IF NOT EXISTS usuarios ("
            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "    nombres TEXT,"
            + "    apellidos TEXT,"
            + "    email TEXT UNIQUE,"  // <-- Columna añadida para que coincida con el DAO
            + "    usuario TEXT NOT NULL UNIQUE,"
            + "    password TEXT NOT NULL,"
            + "    rol TEXT,"
            + "    activo BOOLEAN NOT NULL DEFAULT 1,"
            + "    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ");",

            // --- Casos ---
            "CREATE TABLE IF NOT EXISTS casos ("
            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "    titulo TEXT NOT NULL,"
            + "    descripcion TEXT,"
            + "    tipo_caso TEXT,"
            + "    estado TEXT,"
            + "    fecha_inicio DATE,"
            + "    fecha_cierre DATE,"
            + "    cliente_id INTEGER NOT NULL,"
            + "    abogado_id INTEGER NOT NULL,"
            + "    prioridad TEXT,"
            + "    estimacion_costo REAL,"
            + "    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE RESTRICT,"
            + "    FOREIGN KEY (abogado_id) REFERENCES empleados(id) ON DELETE RESTRICT"
            + ");",

            // --- Audiencias ---
            "CREATE TABLE IF NOT EXISTS audiencias ("
            + "    id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "    tipo TEXT NOT NULL,"
            + "    fecha_hora DATETIME NOT NULL,"
            + "    lugar TEXT,"
            + "    caso_id INTEGER NOT NULL,"
            + "    abogado_id INTEGER NOT NULL,"
            + "    estado TEXT,"
            + "    observaciones TEXT,"
            + "    FOREIGN KEY (caso_id) REFERENCES casos(id) ON DELETE CASCADE,"
            + "    FOREIGN KEY (abogado_id) REFERENCES empleados(id) ON DELETE RESTRICT"
            + ");"
        };

        try (Statement stmt = conexion.createStatement()) {
            for (String sql : sqls) {
                stmt.execute(sql);
            }
            System.out.println("Verificación de tablas SQLite completada.");
        } catch (SQLException e) {
            System.err.println("Error crítico al crear/verificar las tablas de la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters para la configuración actual
    public String getTipoDB() { return tipoDB; }
    public String getHost() { return host; }
    public String getPuerto() { return puerto; }
    public String getNombreDB() { return nombreDB; }
    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }
}