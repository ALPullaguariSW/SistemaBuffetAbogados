package modelo;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Clase para manejar las conexiones a diferentes bases de datos
 * Soporta MySQL, PostgreSQL y SQLite con métodos para probar conexión y obtener conexión activa
 */
public class Conexion {
    private static Conexion instancia;
    private Connection conexion;
    private String tipoDB;
    private String host;
    private String puerto;
    private String nombreDB;
    private String usuario;
    private String password;
    
    // Drivers para cada tipo de base de datos
    private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    private static final String DRIVER_POSTGRESQL = "org.postgresql.Driver";
    private static final String DRIVER_SQLITE = "org.sqlite.JDBC";
    
    private Conexion() {
        cargarConfiguracion();
    }
    
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
    
    /**
     * Carga la configuración desde el archivo properties
     */
    private void cargarConfiguracion() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("configuracion.properties")) {
            props.load(fis);
            tipoDB = props.getProperty("tipoDB", "SQLite");
            host = props.getProperty("host", "localhost");
            puerto = props.getProperty("puerto", "3306");
            nombreDB = props.getProperty("nombreDB", "buffet_abogados");
            usuario = props.getProperty("usuario", "root");
            password = props.getProperty("password", "");
        } catch (IOException e) {
            // Si no existe el archivo, usar configuración por defecto
            tipoDB = "SQLite";
            host = "localhost";
            puerto = "3306";
            nombreDB = "buffet_abogados";
            usuario = "root";
            password = "";
        }
    }
    
    /**
     * Guarda la configuración en el archivo properties
     */
    public void guardarConfiguracion(String tipoDB, String host, String puerto, 
                                   String nombreDB, String usuario, String password) {
        Properties props = new Properties();
        props.setProperty("tipoDB", tipoDB);
        props.setProperty("host", host);
        props.setProperty("puerto", puerto);
        props.setProperty("nombreDB", nombreDB);
        props.setProperty("usuario", usuario);
        props.setProperty("password", password);
        
        try (FileOutputStream fos = new FileOutputStream("configuracion.properties")) {
            props.store(fos, "Configuración de Base de Datos");
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
    
    /**
     * Prueba la conexión a la base de datos
     */
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
    
    /**
     * Obtiene la conexión activa
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conectar();
        }
        return conexion;
    }
    
    /**
     * Establece la conexión a la base de datos
     */
    private void conectar() throws SQLException {
        try {
            String url = construirURL(tipoDB, host, puerto, nombreDB);
            Class.forName(obtenerDriver(tipoDB));
            
            if (tipoDB.equalsIgnoreCase("SQLite")) {
                conexion = DriverManager.getConnection(url);
            } else {
                conexion = DriverManager.getConnection(url, usuario, password);
            }
            
            // Crear tablas si no existen (solo para SQLite por defecto)
            if (tipoDB.equalsIgnoreCase("SQLite")) {
                crearTablas();
            }
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver no encontrado: " + e.getMessage());
        }
    }
    
    /**
     * Construye la URL de conexión según el tipo de base de datos
     */
    private String construirURL(String tipoDB, String host, String puerto, String nombreDB) {
        switch (tipoDB.toUpperCase()) {
            case "MYSQL":
                return "jdbc:mysql://" + host + ":" + puerto + "/" + nombreDB + 
                       "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            case "POSTGRESQL":
                return "jdbc:postgresql://" + host + ":" + puerto + "/" + nombreDB;
            case "SQLITE":
                return "jdbc:sqlite:" + nombreDB + ".db";
            default:
                return "jdbc:sqlite:" + nombreDB + ".db";
        }
    }
    
    /**
     * Obtiene el driver correspondiente al tipo de base de datos
     */
    private String obtenerDriver(String tipoDB) {
        switch (tipoDB.toUpperCase()) {
            case "MYSQL":
                return DRIVER_MYSQL;
            case "POSTGRESQL":
                return DRIVER_POSTGRESQL;
            case "SQLITE":
                return DRIVER_SQLITE;
            default:
                return DRIVER_SQLITE;
        }
    }
    
    /**
     * Crea las tablas necesarias para el sistema
     */
    private void crearTablas() {
        try {
            Statement stmt = conexion.createStatement();
            
            // Tabla de usuarios
            String crearUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombres VARCHAR(100) NOT NULL,
                    apellidos VARCHAR(100) NOT NULL,
                    usuario VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    rol VARCHAR(20) NOT NULL,
                    activo BOOLEAN DEFAULT 1,
                    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            
            // Tabla de clientes
            String crearClientes = """
                CREATE TABLE IF NOT EXISTS clientes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombres VARCHAR(100) NOT NULL,
                    apellidos VARCHAR(100) NOT NULL,
                    direccion TEXT,
                    telefono VARCHAR(20),
                    correo VARCHAR(100),
                    dui VARCHAR(20) UNIQUE,
                    fecha_nacimiento DATE,
                    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            
            // Tabla de empleados
            String crearEmpleados = """
                CREATE TABLE IF NOT EXISTS empleados (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombres VARCHAR(100) NOT NULL,
                    apellidos VARCHAR(100) NOT NULL,
                    telefono VARCHAR(20),
                    dui VARCHAR(20) UNIQUE,
                    fecha_nacimiento DATE,
                    genero VARCHAR(10),
                    provincia VARCHAR(50),
                    tipo_empleado VARCHAR(50),
                    estado_civil VARCHAR(20),
                    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            
            // Tabla de casos
            String crearCasos = """
                CREATE TABLE IF NOT EXISTS casos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(200) NOT NULL,
                    fecha DATE,
                    preambulo TEXT,
                    estado VARCHAR(50),
                    empleado_id INTEGER,
                    cliente_id INTEGER,
                    tipo_caso VARCHAR(100),
                    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (empleado_id) REFERENCES empleados(id),
                    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
                )
            """;
            
            // Tabla de audiencias
            String crearAudiencias = """
                CREATE TABLE IF NOT EXISTS audiencias (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fecha DATE,
                    tipo_audiencia VARCHAR(100),
                    juzgado VARCHAR(100),
                    caso_id INTEGER,
                    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (caso_id) REFERENCES casos(id)
                )
            """;
            
            stmt.execute(crearUsuarios);
            stmt.execute(crearClientes);
            stmt.execute(crearEmpleados);
            stmt.execute(crearCasos);
            stmt.execute(crearAudiencias);
            
            // Insertar usuario administrador por defecto
            String insertarAdmin = """
                INSERT OR IGNORE INTO usuarios (nombres, apellidos, usuario, password, rol, activo)
                VALUES ('Administrador', 'Sistema', 'admin', 'admin123', 'Abogado', 1)
            """;
            stmt.execute(insertarAdmin);
            
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Cierra la conexión
     */
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