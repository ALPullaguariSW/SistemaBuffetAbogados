package buffetabogados.modelo;

import java.sql.Date;

/**
 * Clase que representa un usuario del sistema
 */
public class Usuario {
    private int id;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String password;
    private String rol;
    private Date fechaCreacion;
    private boolean activo = true; // Campo para estado activo/inactivo
    
    // Constructor por defecto
    public Usuario() {}
    
    // Constructor con parámetros
    public Usuario(String nombres, String apellidos, String usuario, String password, String rol) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }
    
    // Constructor completo
    public Usuario(int id, String nombres, String apellidos, String usuario, String password, String rol, Date fechaCreacion) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /**
     * Obtiene el nombre completo del usuario
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    /**
     * Verifica si el usuario es administrador
     */
    public boolean esAdmin() {
        return "Abogado".equals(rol);
    }
    
    /**
     * Verifica si el usuario es cliente
     */
    public boolean esCliente() {
        return "Cliente".equals(rol);
    }
    
    /**
     * Verifica si el usuario es empleado
     */
    public boolean esEmpleado() {
        return "Empleado".equals(rol);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", usuario='" + usuario + '\'' +
                ", rol='" + rol + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
} 